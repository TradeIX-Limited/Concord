#!/usr/bin/env bash

source commonutil.sh
source config.sh

INSTALLATION_FOLDER="installations"

# Usage is ./upload.sh
uploadStorage() {
    echo "Creating storage for ${MODULES[0]}"
    kubectl apply -f storage/${MODULES[0]}.yaml
    sleep ${SHORT_DELAY}
    echo "Storage created for ${MODULES[i]}"
    # echo ${PVC_NAMES}
}

azUploadPVCAllForTestNet() {
  local COUNT=0
  for i in "${AZURE_PVCS[@]}"
  do
    echo "PVC is ${i}"
  local FILES=( "${INSTALLATION_FOLDER}/${MODULES[${COUNT}]}/node.conf" "${INSTALLATION_FOLDER}/${MODULES[${COUNT}]}/id/*.id" "${INSTALLATION_FOLDER}/${MODULES[${COUNT}]}/persistence.mv.db")
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

azUploadSecretsForTestnet() {
  kubectl create secret docker-registry acr-secret --docker-server https://tradeixdev.azurecr.io/ --docker-username tradeixdev --docker-password j70/WPS6Di2apeuckZs83rFfz6pVBtYf --docker-email rajesh@tradeix.com

  local cnt=${#MODULES[@]}
  for ((i=0;i<cnt;i++)); do
    echo "Uploading certificates for ${MODULES[i]}"
    kubectl create secret generic ${MODULES[i]}-certificates --from-file=${INSTALLATION_FOLDER}/${MODULES[i]}/certificates
    sleep ${SHORT_DELAY}
    echo "Uploading certificates for ${MODULES[i]} complete"
  done
}

azUploadTixIntegrationForTestnet() {
  local TIX_FILE=${INSTALLATION_FOLDER}/${CONDUCTOR}/${TIX_INTEGRATION}
  if [ -a ${TIX_FILE} ]
  then
    echo "Upload for ${TIX_FILE}"
    kubectl create configmap tix --from-file=${TIX_FILE}
  else
    echo "Unable to upload tix.integration.conf file"
  fi
}

echo "Starting Upload of artefacts for ${MODULES[0]} in Azure Storage"
onlyOneModuleAtATime
uploadStorage
genPVCNames
genAzurePVCs ${PVC_NAMES}
azUploadPVCAllForTestNet
if [ "${MODULES[0]}" = ${CONDUCTOR} ]
then
  azUploadTixIntegrationForTestnet
fi
azUploadSecretsForTestnet
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
echo "Completed Upload of artefacts for ${MODULES[0]} in Azure Storage"
