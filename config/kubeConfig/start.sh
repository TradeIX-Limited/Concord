#!/usr/bin/env bash

# edit the password and security credentials
kubectl create secret docker-registry acr-secret --docker-server https://tradeixdev.azurecr.io/ --docker-username tradeixdev --docker-password <enter password> --docker-email rajesh@tradeix.com
#kubectl create tixintegration tixintegration --from-file=tixintegration
kubectl create configmap corda --from-file=config
kubectl apply -f storage/
kubectl apply -f services/
kubectl apply -f deployments/
