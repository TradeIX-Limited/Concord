#!/bin/sh

set -eux

#if [ -z ${node+x} ]; then
#if [ -z "${1-}" ]; then
#  exec java -jar corda.jar --config-file=${CONFIG_FILE} --no-local-shell --log-to-console
#fi

#./wait-for ${node}:10002 -- java -jar corda-webserver.jar --config-file=${CONFIG_FILE} --log-to-console
#exec java -jar corda-webserver.jar --config-file=${CONFIG_FILE} --log-to-console

if [ -z "${1-}" ]; then
	echo "No arguments passed. Cannot start corda nodes"
elif [ ! -z "${2-}" ]; then
   echo "Starting webserver after ${2}";
   	./wait-for ${2}:10002 -t 180 -- java -jar corda-webserver.jar --config-file=${CONFIG_FILE} --log-to-console
elif [  -z "${2-}"]  && [ ! -z "${1}" ]  &&  [ "${1}" = "none" ]; then
   echo "Starting Network map service....";
   exec java -jar corda.jar --config-file=${CONFIG_FILE} --no-local-shell --log-to-console
elif [  -z "${2-}"]  && [ ! -z "${1}" ] && [ "${1}" != "none" ]; then
   echo "Starting node after ${1}";
   ./wait-for ${1}:10002 -t 120 -- java -jar corda.jar --config-file=${CONFIG_FILE} --no-local-shell --log-to-console   
fi