#!/usr/bin/env bash

#REMEMBER TO CHANGE THE ACCOUNT NAME AND ACCOUNT KEY
ACCOUNT_NAME="cordastorage2"
ACCOUNT_KEY="123456789"
PREFIX='kubernetes-dynamic-'
declare -a MODULES=("notary" "conductor" "buyer" "funder" "supplier1")
PVC_NAMES=()
AZURE_PVCS=()
CONFIG_HOME=$1
CONDUCTOR="conductor"
TIX_INTEGRATION="tix.integration.conf"

genPVCNames() {
  cnt=${#MODULES[@]}
  #echo ${cnt}
  for ((i=0;i<cnt;i++)); do
    PVC_NAMES+=("pvclaim-${MODULES[i]}")
    # echo ${PVC_NAMES}
  done
}

genAzurePVCs() {
  local PVC_NAMES_LOCAL=${PVC_NAMES[@]}
  local PREFIX_LOCAL=${PREFIX}
  local KUBE_NAME=""
  local KUBE_NAME_NO_WHITESPACE=""
  local AZURE_NAME=""
  for i in "${PVC_NAMES[@]}"
  do
    #echo ${i}
    KUBE_NAME=$(kubectl get pvc --field-selector metadata.name==${i} -o=custom-columns=:.spec.volumeName --no-headers=false)
    KUBE_NAME_NO_WHITESPACE="$(echo -e "${KUBE_NAME}" | tr -d '[:space:]')"
    AZURE_NAME=${PREFIX_LOCAL}${KUBE_NAME_NO_WHITESPACE}
    #echo ${AZURE_NAME}
    AZURE_PVCS+=(${AZURE_NAME})
  done
}

azDeletePVCAll() {
  read -p "Are you sure to delete all the content from the PVC  ? (Y/N) " PROMPT
  echo "Answer is ${PROMPT} "
  case $PROMPT in
      [Nn]* ) exit;;
      * ) echo "Excellent";
      local COUNT=0
      for i in "${AZURE_PVCS[@]}"
      do
        echo "Deleting content from PVC ${i}"
        az storage file delete-batch -s ${i} --account-key ${ACCOUNT_KEY} --account-name ${ACCOUNT_NAME}
      done
  esac
}

azDeleteSecrets() {
  read -p "Are you sure to delete all the content from the config  ? (Y/N) " PROMPT
  echo "Answer is ${PROMPT} "
  case $PROMPT in
      [Nn]* ) exit;;
      * ) echo "Excellent";
      local COUNT=0
      for i in "${MODULES[@]}"
      do
        echo "Deleting content from config ${i}"
        kubectl delete secret ${i}-nodeinfo
        kubectl delete secret ${i}-certificates
      done
      kubectl delete secret additional-node-infos
  esac
}

azUploadPVC() {
  local SHARE_NAME=$1
  local SOURCE=$2
  az storage file upload --share-name ${SHARE_NAME} --source ${SOURCE} --account-name ${ACCOUNT_NAME} --account-key ${ACCOUNT_KEY}
}

azUploadPVCAll() {
  local COUNT=0
  for i in "${AZURE_PVCS[@]}"
  do
    echo "PVC is ${i}"
  local FILES=("${CONFIG_HOME}/${MODULES[${COUNT}]}/persistence.mv.db" "${CONFIG_HOME}/${MODULES[${COUNT}]}/network-parameters" "${CONFIG_HOME}/${MODULES[${COUNT}]}/node.conf" "${CONFIG_HOME}/${MODULES[${COUNT}]}/id/*")
  for j in "${FILES[@]}"
  do
    echo "Upload to ${AZURE_PVCS[${COUNT}]} for ${j}"
    azUploadPVC ${AZURE_PVCS[${COUNT}]} ${j}
  done
  echo "Upload to ${AZURE_PVCS[${COUNT}]} Complete"
  ((COUNT++))
  done
}


#TODO refactor the function
azUploadSecrets() {
  kubectl create secret docker-registry acr-secret --docker-server https://tradeixdev.azurecr.io/ --docker-username tradeixdev --docker-password j70/WPS6Di2apeuckZs83rFfz6pVBtYf --docker-email rajesh@tradeix.com
  kubectl create secret generic additional-node-infos --from-file=${CONFIG_HOME}/buyer/additional-node-infos
  kubectl create secret generic buyer-certificates --from-file=${CONFIG_HOME}/buyer/certificates
  kubectl create secret generic conductor-certificates --from-file=${CONFIG_HOME}/conductor/certificates
  kubectl create secret generic funder-certificates --from-file=${CONFIG_HOME}/funder/certificates
  kubectl create secret generic notary-certificates --from-file=${CONFIG_HOME}/notary/certificates
  kubectl create secret generic supplier1-certificates --from-file=${CONFIG_HOME}/supplier1/certificates

  kubectl create secret generic buyer-nodeinfo --from-file=${CONFIG_HOME}/buyer/node
  kubectl create secret generic conductor-nodeinfo --from-file=${CONFIG_HOME}/conductor/node
  kubectl create secret generic funder-nodeinfo --from-file=${CONFIG_HOME}/funder/node
  kubectl create secret generic notary-nodeinfo --from-file=${CONFIG_HOME}/notary/node
  kubectl create secret generic supplier1-nodeinfo --from-file=${CONFIG_HOME}/supplier1/node
}

azUploadTixIntegration() {
  local TIX_FILE=${CONFIG_HOME}/${CONDUCTOR}/${TIX_INTEGRATION}
  if [ -a ${TIX_FILE} ]
  then
    echo "Upload for ${TIX_FILE}"
    kubectl create configmap tix --from-file=tix.integration.conf
  else
    echo "Unable to upload tix.integration.conf file"
  fi
}

azDeleteTixIntegration() {
  kubectl delete configmap tix
}
