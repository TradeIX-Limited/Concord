#!/usr/bin/env bash

# edit the password and security credentials
kubectl create secret docker-registry acr-secret --docker-server https://tradeixdev.azurecr.io/ --docker-username tradeixdev --docker-password <enter password> --docker-email rajesh@tradeix.com
#kubectl create tixintegration tixintegration --from-file=tixintegration
kubectl create configmap corda --from-file=config
kubectl apply -f storage/
kubectl apply -f services/
kubectl apply -f deployments/
#kubectl create secret generic omsagent-secret --from-literal=WSID=c7af6b09-fa65-48c8-b1e5-246705a82c7d --from-literal=KEY=XU38nI2JUano8s/3DbRK+7EVw+sgWzk56vwSeQ1QlSjbLJkPQJUYtddpYHL5x5pMGDtLhGygqYoLHbuOzo7/WQ==
#kubectl create -f daemonset/oms-daemonset.yaml
kubectl get all