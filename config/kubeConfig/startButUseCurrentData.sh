#!/usr/bin/env bash
#set -eu
source commonutil.sh

#kubectl apply -f storage/
genPVCNames
genAzurePVCs ${PVC_NAMES}
#azUploadPVCAll
azUploadTixIntegration
azUploadSecrets
kubectl apply -f services/
kubectl apply -f deployments/
kubectl get all
# REMEMBER TO CHANGE THE -g OPTION TO THE RESOURCE GROUP and THE -n TO THE NAME OF THE AKS NAME
az aks browse -g rg-azureks -n cordacluster1
#kubectl get pvc -l storage-tier=corda
