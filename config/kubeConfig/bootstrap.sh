#!/usr/bin/env bash

set -eu

source config.sh

CONFIG_SRC=$1
CONFIG_DEST=$2
CONFIG_CLIENTS=$3

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

validateClientsDirectory() {
  if [ \( \( -a ${CONFIG_CLIENTS} \) -a \( -d ${CONFIG_CLIENTS} \) \) -o \( -z "$(ls -A ${CONFIG_CLIENTS} )" \) ]
  then
    echo "${CONFIG_CLIENTS} is valid"
  else
    echo "${CONFIG_CLIENTS} is invalid. Bootstrapping requires a CONFIG_CLIENTS config directory"
    exit 1
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
  local NOTARY="notary"
  for i in ${MODULES[@]}
  do
    mkdir ${CONFIG_DEST}/$i/${ID_DIR}
    touch ${CONFIG_DEST}/$i/${ID_DIR}/${i}.id
    mkdir ${CONFIG_DEST}/$i/${NODE_DIR}
    cp ${CONFIG_DEST}/$i/nodeInfo* ${CONFIG_DEST}/$i/${NODE_DIR}/nodeInfo
    if [ "$i" != "${NOTARY}" ]
    then
     cp -a ${CONFIG_CLIENTS}/$i/* ${CONFIG_DEST}/$i/
    fi
  done
  echo "Generated custom files - complete"
}


downloadBootstrapperjar
validateConfigSourceDirectory
validateConfigDestinationDirectory
copyToConfigDestination
generateBootstrapFiles
generateCustomFiles
