#!/usr/bin/env bash

set -eu

CONFIG_SRC=$1
CONFIG_DEST=$2
declare -a MODULES=("notary" "conductor" "buyer" "funder" "supplier1")
CONDUCTOR="conductor"
TIX_INTEGRATION="tix.integration.conf"

downloadBootstrapperjar() {
  if [ ! -e network-bootstrapper-corda-3.0.jar ]
  then
    curl http://downloads.corda.net/network-bootstrapper-corda-3.0.jar -o network-bootstrapper-corda-3.0.jar
  fi
}

validateConfigSourceDirectory() {
  if [ \( \( -a ${CONFIG_SRC} \) -a \( -d ${CONFIG_SRC} \) \) -o \( -z "$(ls -A ${CONFIG_SRC} )" \) ]
  then
    echo "${CONFIG_SRC} is valid"
  else
    echo "${CONFIG_SRC} is invalid. Bootstrapping requires a source config directory"
    exit 1
  fi
}

validateConfigDestinationDirectory() {
  if [ \( -a ${CONFIG_DEST} \)  ]
  then
    echo "${CONFIG_DEST} is already present. Delete this directory"
    exit 1
  else
    echo "${CONFIG_DEST} is valid"
  fi
}

copyToConfigDestination() {
  cp -r ${CONFIG_SRC} ${CONFIG_DEST}
}

generateBootstrapFiles() {
 java -jar network-bootstrapper-corda-3.0.jar ${CONFIG_DEST}
}
generateCustomFiles() {
  echo "Generating custom files"
  local NODE_DIR="node"
  local ID_DIR="id"
  for i in ${MODULES[@]}
  do
    mkdir ${CONFIG_DEST}/$i/${ID_DIR}
    touch ${CONFIG_DEST}/$i/${ID_DIR}/${i}.id
    mkdir ${CONFIG_DEST}/$i/${NODE_DIR}
    cp ${CONFIG_DEST}/$i/nodeInfo* ${CONFIG_DEST}/$i/${NODE_DIR}/nodeInfo
  done
  echo "Generated custom files - complete"
}

positionTixIntegrationConfig() {
  if [ -d ${CONFIG_DEST}/${CONDUCTOR} ]
  then
    if [ -e ${TIX_INTEGRATION} ]
    then
      cp ${TIX_INTEGRATION} ${CONFIG_DEST}/${CONDUCTOR}/
      echo "${TIX_INTEGRATION} is positioned for azure upload"
    else
      echo "${TIX_INTEGRATION} does not exist. So not positioning the file for upload"
    fi
  else
    echo "You have decided not to deploy conductor. So not positioning ${TIX_INTEGRATION}"
  fi
}

downloadBootstrapperjar
validateConfigSourceDirectory
validateConfigDestinationDirectory
copyToConfigDestination
generateBootstrapFiles
generateCustomFiles
positionTixIntegrationConfig
