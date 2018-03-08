#!/usr/bin/env bash
./gradlew clean copyTixIntegrationConfiguration build
./gradlew deployDevNodes
cp ./tradeix-concord/build/libs/tradeix-concord-0.1.jar /datadrive/share/corda