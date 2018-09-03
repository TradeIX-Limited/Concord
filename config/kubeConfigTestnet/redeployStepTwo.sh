#!/usr/bin/env bash

source commonutil.sh
source config.sh

# Usage is ./redeployStepTwo.sh
# Note : It is recommended that you run kubectl get pods and ensure that you have cleaned the pod before you run this script
INSTALLATION_FOLDER="installations"
uploadApp() {
  sleep ${SHORT_DELAY}
  kubectl apply -f deployments/${MODULES[0]}.yml
  sleep ${SHORT_DELAY}
}

echo "Starting the Step 2 redeployment of artefacts for ${MODULES[0]} in Kubernetes"
onlyOneModuleAtATime
# FILE1="persistence.mv.db"
# genPVCNames
# genAzurePVCs ${PVC_NAMES}
# azDeletePVCFile ${AZURE_PVCS[0]} ${FILE1}
# sleep ${MEDIUM_DELAY}
# sleep ${MEDIUM_DELAY}
# sleep ${MEDIUM_DELAY}
# sleep ${MEDIUM_DELAY}
# azUploadPVC ${AZURE_PVCS[0]} "${INSTALLATION_FOLDER}/${MODULES[0]}/${FILE1}"
# sleep ${MEDIUM_DELAY}
# sleep ${MEDIUM_DELAY}
# sleep ${MEDIUM_DELAY}
uploadApp
kubectl get pods
echo "ReDeployment of artefacts for the Step 2 of ${MODULES[0]} in Kubernetes Completed"
