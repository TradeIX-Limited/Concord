#!/usr/bin/env bash

set -eux

#./oneTimeCreateStorage.sh
# Run this only once in the lifetime of the cluster. If you are adding subsequent nodes, don't run this script again.
kubectl apply -f storage/AzureFileStorage.yaml
