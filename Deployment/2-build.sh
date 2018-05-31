#!/usr/bin/env bash
./gradlew clean copyTixIntegrationConfiguration build
./gradlew deployNodes
#cp ./tradeix-concord/build/libs/tradeix-concord-0.1.jar /datadrive/share/corda
#cp ./tradeix-concord-domain/build/libs/tradeix-concord-domain-0.1.jar /datadrive/share/corda