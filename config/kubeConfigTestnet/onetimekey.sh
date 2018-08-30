#!/usr/bin/env bash

set -eu

SESSION_KEY=${1}
KEY="$(curl https://testnet.corda.network/api/user/node/generate/one-time-key\?sessionToken\=${SESSION_KEY})"
echo ${KEY}
