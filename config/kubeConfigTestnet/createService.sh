#!/usr/bin/env bash

source commonutil.sh
source config.sh

# Usage is ./createService.sh
uploadService() {
    sleep ${SHORT_DELAY}
    kubectl apply -f services/${MODULES[0]}.yml
    kubectl get service
}

echo "Creating service for ${MODULES[0]} in Azure Kubernetes Cluster"
onlyOneModuleAtATime
uploadService
