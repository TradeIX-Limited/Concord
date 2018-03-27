#!/usr/bin/env bash
docker rmi tradeixdev.azurecr.io/corda:$docker_tag
docker build --no-cache -t tradeix:Corda .
echo ${AZ_CONTAINER_REG_LOGIN_PSW} | docker login tradeixdev.azurecr.io --username ${AZ_CONTAINER_REG_LOGIN_USR} --password-stdin
docker tag tradeix:Corda tradeixdev.azurecr.io/corda:$docker_tag
docker push tradeixdev.azurecr.io/corda:$docker_tag