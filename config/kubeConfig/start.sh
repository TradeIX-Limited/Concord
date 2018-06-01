#!/usr/bin/env bash
#set -eu
source commonutil.sh

#kubectl create secret generic omsagent-secret --from-literal=WSID=c7af6b09-fa65-48c8-b1e5-246705a82c7d --from-literal=KEY=XU38nI2JUano8s/3DbRK+7EVw+sgWzk56vwSeQ1QlSjbLJkPQJUYtddpYHL5x5pMGDtLhGygqYoLHbuOzo7/WQ==
#kubectl create -f daemonset/oms-daemonset.yaml
kubectl apply -f storage/
genPVCNames
genAzurePVCs ${PVC_NAMES}
azUploadPVCAll
azUploadTixIntegration
azUploadSecrets
kubectl apply -f services/
kubectl apply -f deployments/
kubectl apply -f networkpolicy/
kubectl get all
# REMEMBER TO CHANGE THE -g OPTION TO THE RESOURCE GROUP and THE -n TO THE NAME OF THE AKS NAME
az aks browse -g rg-azureks -n cordacluster1
#kubectl get pvc -l storage-tier=corda
