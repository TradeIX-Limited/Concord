#!/usr/bin/env bash

cp tix.integration.template.conf tix.integration.conf
    sed -i -- "s|{{Username}}|${RABBIT_LOGIN_USR}|g" tix.integration.conf
    sed -i -- "s|{{Password}}|${RABBIT_LOGIN_PSW}|g" tix.integration.conf
    sed -i -- "s|{{RabbitPort}}|5672|g" tix.integration.conf
    sed -i -- "s|{{RabbitHost}}|${rabbit_machineip}|g" tix.integration.conf