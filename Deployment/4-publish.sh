#!/usr/bin/env bash
docker rmi tradeixdev.azurecr.io/corda:latest
docker build --no-cache -t tradeix:Corda .
echo ${AZ_CONTAINER_REG_LOGIN_PSW} | docker login tradeixdev.azurecr.io --username ${AZ_CONTAINER_REG_LOGIN_USR} --password-stdin
docker tag tradeix:Corda tradeixdev.azurecr.io/corda:latest
docker push tradeixdev.azurecr.io/corda:latest