#!/usr/bin/env bash
AZ_CONTAINER_REG_LOGIN_USR=$1
AZ_CONTAINER_REG_LOGIN_PSW=$2

echo ${AZ_CONTAINER_REG_LOGIN_PSW} | docker login tradeixdev.azurecr.io --username ${AZ_CONTAINER_REG_LOGIN_USR} --password-stdin
docker-compose --file /etc/tradeix/containervolumes/corda/docker-compose.run.yml down
docker-compose --file /etc/tradeix/containervolumes/corda/docker-compose.run.yml pull --parallel
docker-compose --file /etc/tradeix/containervolumes/corda/docker-compose.run.yml up -d