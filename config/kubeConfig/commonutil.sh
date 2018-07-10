#!/usr/bin/env bash

source config.sh

PVC_NAMES=()
AZURE_PVCS=()
CONFIG_HOME=$1
APPLICATION_PROPERTIES="application.properties"

genPVCNames() {
  local cnt=${#MODULES[@]}
  #echo ${cnt}
  for ((i=0;i<cnt;i++)); do
    #echo ${MODULES[i]}
    PVC_NAMES+=("pvclaim-${MODULES[i]}")
    #echo ${PVC_NAMES}
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
  #echo $AZURE_PVCS
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
    sleep ${MEDIUM_DELAY}
    echo "Upload to ${AZURE_PVCS[${COUNT}]} for ${j} Complete"
  done
  echo "Upload to ${AZURE_PVCS[${COUNT}]} Complete"
  ((COUNT++))
  done
}

azUploadSecrets() {
  kubectl create secret docker-registry acr-secret --docker-server https://tradeixdev.azurecr.io/ --docker-username tradeixdev --docker-password j70/WPS6Di2apeuckZs83rFfz6pVBtYf --docker-email rajesh@tradeix.com
  kubectl create secret generic additional-node-infos --from-file=${CONFIG_HOME}/${MODULES[0]}/additional-node-infos

  local cnt=${#MODULES[@]}
  #echo ${cnt}
  for ((i=0;i<cnt;i++)); do
    #PVC_NAMES+=("pvclaim-${MODULES[i]}")
    kubectl create secret generic ${MODULES[i]}-certificates --from-file=${CONFIG_HOME}/${MODULES[i]}/certificates
    sleep ${SHORT_DELAY}
    kubectl create secret generic ${MODULES[i]}-nodeinfo --from-file=${CONFIG_HOME}/${MODULES[i]}/node
    sleep ${SHORT_DELAY}
    # echo ${MODULES[i]}
  done
}

azUploadReceiverResponderProperties() {
  local cnt=${#MODULES[@]}
  local RECEIVER_REF="receiver"
  local RESPONDER_REF="responder"
  local NOTARY="notary"
  #echo ${cnt}
  for ((i=0;i<cnt;i++)); do
    local RECEIVER_FILE=${CONFIG_HOME}/${MODULES[i]}/${RECEIVER_REF}/${APPLICATION_PROPERTIES}
    local RESPONDER_FILE=${CONFIG_HOME}/${MODULES[i]}/${RESPONDER_REF}/${APPLICATION_PROPERTIES}
    local CONFIGMAP_FILE_RECEIVER=${MODULES[i]}"-"${RECEIVER_REF}
    local CONFIGMAP_FILE_RESPONDER=${MODULES[i]}"-"${RESPONDER_REF}
#    if [ "$i" != "${NOTARY}" ]
#    then
#      continue
#    fi
    if [ -a ${RECEIVER_FILE} ]
    then
      kubectl create configmap ${CONFIGMAP_FILE_RECEIVER} --from-file=${RECEIVER_FILE}
      sleep ${SHORT_DELAY}
    else
      echo "Unable to upload ${APPLICATION_PROPERTIES} file for ${RECEIVER_REF}"
    fi
    if [ -a ${RESPONDER_FILE} ]
    then
      kubectl create configmap ${CONFIGMAP_FILE_RESPONDER} --from-file=${RESPONDER_FILE}
      sleep ${SHORT_DELAY}
    else
      echo "Unable to upload ${APPLICATION_PROPERTIES} file for ${RESPONDER_REF}"
    fi
    echo ${MODULES[i]}
  done
}
azDeleteReceiverResponderProperties() {
  kubectl delete configmap --all
}
