#!/bin/sh

set -eux

if [ -z "${1-}" ]; then
	echo "No arguments passed. Cannot start corda nodes"
elif [ ! -z "${2-}" ]; then
   echo "Starting webserver after ${2}";
	 java -jar corda-webserver.jar --log-to-console
elif [  -z "${2-}" ]  && [ ! -z "${1}" ]  &&  [ "${1}" = "none" ]; then
   echo "Starting Notary service....";
   exec java -jar corda.jar --no-local-shell --log-to-console
elif [  -z "${2-}"]  && [ ! -z "${1}" ] && [ "${1}" != "none" ]; then
   echo "Starting node after ${1}";
#exec java -jar corda.jar -Dcapsule.jvm.args=-Xmx1G --config-file=${CONFIG_FILE} --no-local-shell --log-to-console
	java -jar corda.jar --no-local-shell --log-to-console
fi
