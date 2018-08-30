#!/usr/bin/env bash

# ./uninstall.sh
source commonutil.sh
source config.sh

INSTALLATION_FOLDER="installations"
onlyOneModuleAtATime
genPVCNames
genAzurePVCs ${PVC_NAMES}
# kubectl delete -f services/${MODULES[0]}.yml
kubectl delete -f deployments/${MODULES[0]}.yml
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
azDeletePVC ${AZURE_PVCS[0]}
sleep ${SHORT_DELAY}
kubectl delete secret ${MODULES[0]}-certificates
sleep ${SHORT_DELAY}
kubectl delete -f storage/${MODULES[0]}.yaml
sleep ${SHORT_DELAY}
if [ "${MODULES[0]}" = ${CONDUCTOR} ]
then
  azDeleteTixIntegration
fi
# rm -rf ${INSTALLATION_FOLDER}/${MODULES[0]}
