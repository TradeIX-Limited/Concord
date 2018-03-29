#!/usr/bin/env bash
kubectl scale --replicas=1 -f deployments/conductor.yml --namespace=dev
kubectl scale --replicas=1 -f deployments/buyer.yml --namespace=dev
kubectl scale --replicas=1 -f deployments/funder.yml --namespace=dev
kubectl scale --replicas=1 -f deployments/supplier1.yml --namespace=dev

#kubectl scale [--resource-version=version] [--current-replicas=count] --replicas=COUNT (-f FILENAME | TYPE NAME)
