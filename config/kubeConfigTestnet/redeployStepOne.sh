#!/usr/bin/env bash

source commonutil.sh
source config.sh

# Usage is ./redeployStepOne.sh
INSTALLATION_FOLDER="installations"

echo "Starting redeployment Step 1 of artefacts for ${MODULES[0]} in Kubernetes"
onlyOneModuleAtATime
kubectl delete -f deployments/${MODULES[0]}.yml
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
sleep ${MEDIUM_DELAY}
kubectl get pods
echo "ReDeployment of artefacts for the step 1 ${MODULES[0]} in Kubernetes Completed"
