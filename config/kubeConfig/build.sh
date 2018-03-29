#!/bin/sh

set -eux
# edit the tag, repository, username and password before executing the task
docker build -t corda:1.0.0 build
docker tag corda:1.0.0.0 tradeixdev.azurecr.io/corda:1.0.0.0
docker push tradeixdev.azurecr.io/corda:1.0.0.0
az acr repository show-tags -o table --repository corda -g rg-azurecr -u tradeixdev -p <enter password> -n tradeixdev