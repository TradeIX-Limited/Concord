#!/usr/bin/env bash
set -eu
# TODO Remove the unnecessary echos

# Usage is ./kubeFiles.sh funder0
ID=$1
DEPLOYMENT_SRC="deployments"
SERVICES_SRC="services"
STORAGE_SRC="storage"
TEMPLATE_FILE="template.yml"
TEMPLATE_FILE_YAML="template.yaml"

validateKubeTemplates() {
  local directory=${1}/${TEMPLATE_FILE}
  echo ${directory}
  if [ -f ${directory} ]
  then
   echo "Template File ${directory} is Found"
  else
   echo "Template File ${directory} is Not Found"
   exit
  fi
}

validateKubeTemplatesYAML() {
  local directory=${1}/${TEMPLATE_FILE_YAML}
  echo ${directory}
  if [ -f ${directory} ]
  then
   echo "Template File ${TEMPLATE_FILE_YAML} is Found"
  else
   echo "Template File ${TEMPLATE_FILE_YAML} is Not Found"
   exit
  fi
}

validateDirectory() {
  local directory=${1}
  if [ -d "$directory" ]
  then
    echo "${directory} is valid"
  else
    echo "${directory} is invalid. Bootstrapping requires a source $directory directory"
    exit 1
  fi
}

generateKubeFile() {
  local directory=${1}
  local oldValue="__ID__"
  local newValue=${ID}
  local newFile=${directory}/${newValue}.yml
  cp ${directory}/${TEMPLATE_FILE} ${directory}/${newValue}.yml
  sed -i -e s/${oldValue}/${newValue}/g ${newFile}
  rm -f ${newFile}-e
}

generateKubeFileYAML() {
  local directory=${1}
  local oldValue="__ID__"
  local newValue=${ID}
  local newFile=${directory}/${newValue}.yaml
  cp ${directory}/${TEMPLATE_FILE_YAML} ${directory}/${newValue}.yaml
  sed -i -e s/${oldValue}/${newValue}/g ${newFile}
  rm -f ${newFile}-e
}

validateDirectory ${DEPLOYMENT_SRC}
validateKubeTemplates ${DEPLOYMENT_SRC}
validateDirectory ${SERVICES_SRC}
validateKubeTemplates ${SERVICES_SRC}
validateDirectory ${STORAGE_SRC}
validateKubeTemplatesYAML ${STORAGE_SRC}
generateKubeFile ${DEPLOYMENT_SRC}
generateKubeFile ${SERVICES_SRC}
generateKubeFileYAML ${STORAGE_SRC}
