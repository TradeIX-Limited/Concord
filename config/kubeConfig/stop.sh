#!/usr/bin/env bash
kubectl delete configmap corda
kubectl delete -f services/
kubectl delete -f storage/
kubectl delete -f deployments/
kubectl delete secret acr-secret
kubectl delete secret omsagent-secret
kubectl delete -f daemonset/oms-daemonset.yaml
