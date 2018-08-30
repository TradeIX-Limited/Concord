#!/usr/bin/env bash

source commonutil.sh
source config.sh

# Usage is ./deploy.sh
uploadApp() {
  sleep ${SHORT_DELAY}
  kubectl apply -f deployments/${MODULES[0]}.yml
  sleep ${SHORT_DELAY}
}

echo "Starting deployment of artefacts for ${MODULES[0]} in Kubernetes"
onlyOneModuleAtATime
uploadApp
kubectl get pods
echo "Deployment of artefacts for ${MODULES[0]} in Kubernetes Completed"
