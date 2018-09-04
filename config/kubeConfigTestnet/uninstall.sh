#!/usr/bin/env bash

# ./uninstall.sh
# If you are uninstalling Conductor, take a backup of the pre-configured deployment, service and storage files before you run this program.
# After running the script, copy the backup to its original location. This would help you run the next round
source commonutil.sh
source config.sh

INSTALLATION_FOLDER="installations"
ARCHIVE_FOLDER="archives"
createArchiveIfNotFound() {
  local directory1=${INSTALLATION_FOLDER}
  if [ -d ${directory1} ]
  then
   echo "${directory1} is Found. The generated files will be placed here."
  else
   echo "${directory1} is Not Found. So creating one"
   mkdir ${directory1}
  fi
}

timestamp=$(date +%s)

onlyOneModuleAtATime
genPVCNames
genAzurePVCs ${PVC_NAMES}
kubectl delete -f services/${MODULES[0]}.yml
kubectl delete -f deployments/${MODULES[0]}.yml
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
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
mv ${INSTALLATION_FOLDER}/${MODULES[0]} ${ARCHIVE_FOLDER}/${MODULES[0]}-${timestamp}
