#!/usr/bin/env bash

set -e

JVM_ARGS="-Xms1024m -Xmx2048m -XX:NewSize=512m -XX:MaxNewSize=1024m"
A=$(export JVM_ARGS)

function validateJmterShellScript() {
  if ! { [ -a ${PATH_TO_SCRIPT} ] && [ -x ${PATH_TO_SCRIPT} ] ;  };
  then
    echo "You are yet to configure the jmeter.sh correctly. Please update PATH_TO_SCRIPT property in the jmeter-corda.sh "
    exit
  fi
}

# Enter the path from your local system
PATH_TO_SCRIPT="/Users/rajesh/Workspace/apache-jmeter-4.0/bin/jmeter.sh"

validateJmterShellScript

read -p "Do you want to execute performance tests on Corda Network ? Enter y or n : " ANSWER_START

case "$ANSWER_START" in
  [yY]*)
  echo "Enter your choice"
  echo "1. Issuance"
  echo "2. Change of Ownership"
  echo "3. Concurrent Issuance and Change of Ownership"
  echo "4. Exit"
  read -p "Enter your choice : " ANSWER_TYPE
        case "$ANSWER_TYPE" in
          1) echo "Your choice is Issuance"
             JMX_FILE="IssuanceRequest.jmx"
            ;;
          2) echo "Your choice is Change of Ownership"
          JMX_FILE="COO.jmx"
            ;;
          3) echo "Your choice is Concurrent Issuance and Change of Ownership"
          JMX_FILE="IssuanceCOO.jmx"
            ;;
          4) echo "Your choice is to Exit"
          exit
            ;;
          *) echo "Sorry! Invalid choice"
          exit
          ;;
        esac
    ;;
    *) echo "OK..BYE"
    exit
    ;;
esac

read -p "Enter the csv file to record results : " RESULTS_FILE
read -p "Enter the directory path for webreport : " FILE_TO_WEBREPORT
read -p "Enter the number of Requests : " NUMBER_OF_REQUESTS
read -p "Enter the IP Address of TradeIX Node : " TEST_IP

#JMX_FILE="/Users/rajesh/Workspace/JMeterSpace/IssuanceCOO.jmx"
#RESULTS_FILE="/Users/rajesh//Workspace/JMeterSpace/Results50IssuanceCOO4vm1.csv"
#FILE_TO_WEBREPORT="/Users/rajesh/Workspace/JMeterSpace/webreport50IssueCOORequests4vm1"
#PATH_TO_SCRIPT="/Users/rajesh/Workspace/apache-jmeter-4.0/bin/jmeter.sh"
# jmeter -n -t [jmx file] -l [results file] -e -o [Path to web report folder]
#./jmeter.sh -n -t ~/Workspace/JMeterSpace/IssuanceCOO.jmx -l ~/Workspace/JMeterSpace/Results50IssuanceCOO4vm.csv -e -o ~/Workspace/JMeterSpace/webreport50IssueCOORequests4vm

/bin/bash ${PATH_TO_SCRIPT} -n -t ${JMX_FILE} -l ${RESULTS_FILE} -e -o ${FILE_TO_WEBREPORT} -Jcount=${NUMBER_OF_REQUESTS} -JTestIP=${TEST_IP}
