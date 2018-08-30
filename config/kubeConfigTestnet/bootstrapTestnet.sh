#!/bin/bash





set -e

#Usage ./bootstrapTestnet.sh "30caf21b-a91a-4156-a8ba-68235aa9f27c" "1.2.3.4.5" "funder0" London GB
ONE_TIME_DOWNLOAD_KEY=$1
IP=$2
ID=$3
LOCALITY=$4
COUNTRY=$5
INSTALLATION_FOLDER="installations"
installDir=${INSTALLATION_FOLDER}/${ID}
nodeZipLocation=${ID}.zip
HOST_NAME="https://testnet.corda.network"
HOST_NAME_FOR_GENERATING_ONETIMEKEY="https://testnet.corda.network/api/user/node/generate/one-time-key\\?sessionToken\\="
CONDUCTOR="conductor"
TIX_INTEGRATION="tix.integration.conf"

createInstallationIfNotFound() {
  local directory1=${INSTALLATION_FOLDER}
  if [ -d ${directory1} ]
  then
   echo "${directory1} is Found. The generated files will be placed here."
  else
   echo "${directory1} is Not Found. So creating one"
   mkdir ${directory1}
  fi
}

checkJava() {
    echo "Verifying that Java is installed"
    if type -p java; then
        echo "Found Java on path"
        javaBin=java
    elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]]; then
        echo "Found JAVA_HOME at $JAVA_HOME, using $JAVA_HOME/bin/java"
        javaBin="$JAVA_HOME/bin/java"
    else
        echo "Java not found - attempting Java install"
        attemptPackageInstall ${jdkPackage}
    fi
    checkJavaVersion
}


checkJavaVersion() {
    echo "Checking Java version"
    if [[ ${javaBin} ]]; then
        version=$("$javaBin" -version 2>&1 | awk -F '"' '/version/ {print $2}')
        if [[ "$version" > "1.8" || "$version" == "1.8" ]]; then
            echo "Java version meets minimum requirements (1.8)"
        else
            echo "Java version does not meet minimum requirements of 1.8. Found: $version"
            exit 1
        fi
    fi
}

checkDirectory() {
    echo "Installing to $installDir"
    if [ ! -d ${installDir} ]; then
        echo "Directory does not exist, attempting to create"
        mkdir ${installDir}
    elif [ ! -w ${installDir} ]; then
        echo "Current user does not have write permissions to  $installDir"
        exit 1
    fi
}

generateNodePostData()
{
  cat <<EOF
{
  "x500Name": {
    "locality": "${LOCALITY}",
    "country": "${COUNTRY}"
  },
  "configType": "INSTALLSCRIPT"
}
EOF
}

downloadNode() {
    echo "Generating and downloading node - this may take several minutes depending on your connection"
    # Developer note: HOST_NAME should be replaced whenever R3 change the host location. Also, when you move to prodnet
    curl -L -d "$(generateNodePostData)" \
    -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -X POST "${HOST_NAME}/api/user/node/generate/one-time-key/redeem/$ONE_TIME_DOWNLOAD_KEY" \
    -o "$nodeZipLocation"
    echo "Node downloaded to $nodeZipLocation"
}

extractNode() {
    echo "Extracting node to $installDir"
    unzip -d $installDir $nodeZipLocation
    echo "Node extracted"
}

generateCustomFiles() {
  echo "Generating custom files"
  echo "Generating id file..."
  mkdir ${installDir}/id
  touch ${installDir}/id/${ID}.id
  echo "Parsing only the legal name and copying it into the id file.."
  LEGAL_NAME=$(sed -n -e '/^    "myLegalName" /p' $installDir/node.conf)
  echo ${LEGAL_NAME}  >> ${installDir}/id/${ID}.id
  echo "Generated custom files - complete"
}

updateConfig() {
    echo "Updating node config to match the specified environment"
    echo "Replacing __P2PADDRESS__ address with ${IP}"
    sed -i -e s/__P2PADDRESS__/${IP}/g $installDir/node.conf
    echo "Replacing __WEBADDRESS__ address with ${ID}"
    WEB_ADDRESS_OLD="0.0.0.0:8080"
    WEB_ADDRESS_NEW="${IP}:8080"
    sed -i -e s/${WEB_ADDRESS_OLD}/${WEB_ADDRESS_NEW}/g $installDir/node.conf
}

updateKeyStores() {
  cd $installDir
  java -jar corda.jar --just-generate-node-info
  cd ../..
}

positionTixIntegrationConfig() {
  if [ "$ID" = ${CONDUCTOR} ]; then
    echo "We can position this file for the conductor"
  else
    echo "We cannot position this file because this is not a conductor"
  fi;
  if [ -d "${installDir}" ]
  then
    if [ -e ${TIX_INTEGRATION} ]
    then
      cp ${TIX_INTEGRATION} ${installDir}/
      echo "${TIX_INTEGRATION} is positioned for azure upload"
    else
      echo "${TIX_INTEGRATION} does not exist. So not positioning the file for upload"
    fi
  else
    echo "You have decided not to deploy conductor. So not positioning ${TIX_INTEGRATION}"
  fi
}

deleteUnecessaryFiles() {
  echo "Cleaning up .."
  rm ${nodeZipLocation}
  rm ${installDir}/node.conf-e
  echo "Cleaned"
}

remindToBackupAndDelete() {
  read -p "Have you taken a backup of ${nodeZipLocation} and  ${installDir} ? (Y/N) " PROMPT
  echo "Answer is ${PROMPT} "
  case $PROMPT in
      [Nn]* )
      echo "That is not a good idea to avoid the backup. The generated files are not cleaned up, just in case you change your mind";
      exit;;
      * ) echo "Excellent";
      deleteUnecessaryFiles
  esac
}

echo "Starting a node pre-installation for Corda TestNet from $(hostname -f)"
createInstallationIfNotFound
checkJava
checkDirectory
downloadNode
extractNode
generateCustomFiles
updateConfig
updateKeyStores
sleep 10
# TODO remindToBackupAndDelete for conductor too
if [ "$ID" = ${CONDUCTOR} ]
then
  positionTixIntegrationConfig
else
  remindToBackupAndDelete
fi
echo "Pre-Installation finished successfully."
