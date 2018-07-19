#!/usr/bin/env bash

source commonutil.sh
source config.sh


uploadStorage() {
  local cnt=${#MODULES[@]}
  for ((i=0;i<cnt;i++)); do
    kubectl apply -f storage/${MODULES[i]}.yaml
    sleep ${SHORT_DELAY}
    # echo ${PVC_NAMES}
  done
}
uploadApps() {
  local cnt=${#MODULES[@]}
  for ((i=0;i<cnt;i++)); do
    kubectl apply -f services/${MODULES[i]}.yml
    sleep ${SHORT_DELAY}
    kubectl apply -f deployments/${MODULES[i]}.yml
    sleep ${SHORT_DELAY}
    # echo ${PVC_NAMES}
  done
}

#kubectl apply -f storage/AzureFileStorage.yaml
#uploadStorage
genPVCNames
genAzurePVCs ${PVC_NAMES}
#azUploadPVCAll
azUploadSecrets
azUploadReceiverResponderProperties
uploadApps
kubectl get all
az aks browse -g rg-azureks -n cordacluster1
