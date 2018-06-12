#!/usr/bin/env bash

source commonutil.sh
source config.sh


genPVCNames
genAzurePVCs ${PVC_NAMES}
kubectl delete -f services/
kubectl delete -f deployments/
kubectl delete po --all
#azDeletePVCAll
azDeleteSecrets
#kubectl delete -f storage/
kubectl delete secret acr-secret
azDeleteTixIntegration
