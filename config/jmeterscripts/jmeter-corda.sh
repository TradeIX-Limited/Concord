#!/usr/bin/env bash

# todo : pending refactoring

set -e

JVM_ARGS="-Xms1024m -Xmx2048m -XX:NewSize=512m -XX:MaxNewSize=1024m"
A=$(export JVM_ARGS)

read -p "Do you want to execute performance tests on Corda Network ? Enter y or n : " ANSWER_START

case "$ANSWER_START" in
  [yY]*)
  echo "Enter your choice"
  echo "1. Issuance"
  echo "2. Change of Ownership"
  echo "3. Concurrent Issuance and Change of Owneship"
  echo "4. Exit"
  read -p "Enter your choice : " ANSWER_TYPE
        case "$ANSWER_TYPE" in
          1) echo "Your choice is Issuance"
             read -p "Enter the JMX file with absolute path : " JMX_FILE
             read -p "Enter the Jmeter executable file with absolute path : " PATH_TO_SCRIPT
             read -p "Enter the file to record results with absolute path : " RESULTS_FILE
             read -p "Enter the directory path for webreport with absolute path : " FILE_TO_WEBREPORT
            ;;
          2) echo "Your choice is Change of Ownership"
          read -p "Enter the JMX file with absolute path : " JMX_FILE
          read -p "Enter the Jmeter executable file with absolute path : " PATH_TO_SCRIPT
          read -p "Enter the file to record results with absolute path : " RESULTS_FILE
          read -p "Enter the directory path for webreport with absolute path : " FILE_TO_WEBREPORT
            ;;
          3) echo "Your choice is Concurrent Issuance and Change of Ownership"
          read -p "Enter the JMX file with absolute path : " JMX_FILE
          read -p "Enter the Jmeter executable file with absolute path : " PATH_TO_SCRIPT
          read -p "Enter the file to record results with absolute path : " RESULTS_FILE
          read -p "Enter the directory path for webreport with absolute path : " FILE_TO_WEBREPORT
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



#JMX_FILE="/Users/rajesh/Workspace/JMeterSpace/IssuanceCOO.jmx"
#RESULTS_FILE="/Users/rajesh//Workspace/JMeterSpace/Results50IssuanceCOO4vm1.csv"
#FILE_TO_WEBREPORT="/Users/rajesh/Workspace/JMeterSpace/webreport50IssueCOORequests4vm1"
#PATH_TO_SCRIPT="/Users/rajesh/Workspace/apache-jmeter-4.0/bin/jmeter.sh"
# jmeter -n -t [jmx file] -l [results file] -e -o [Path to web report folder]
#./jmeter.sh -n -t ~/Workspace/JMeterSpace/IssuanceCOO.jmx -l ~/Workspace/JMeterSpace/Results50IssuanceCOO4vm.csv -e -o ~/Workspace/JMeterSpace/webreport50IssueCOORequests4vm

/bin/bash ${PATH_TO_SCRIPT} -n -t ${JMX_FILE} -l ${RESULTS_FILE} -e -o ${FILE_TO_WEBREPORT}
