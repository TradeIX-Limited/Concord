#!/usr/bin/env bash

# Remember to change the Account values
ACCOUNT_NAME="cordastorage2"
ACCOUNT_KEY="BNmXBdzIdGs2ZeIX0YY5uPdYXfQGT/ss5GBoWCRfJvXMvk3b0tk1VPQKqSKuYSHAMZshKuBe5AFEkb0PvohIIQ=="
PREFIX='kubernetes-dynamic-'
declare -a MODULES=("notary" "conductor" "buyer" "buyer0" "supplier1" "supplier2" "funder" "funder0" "funder1" "funder2" "funder3" "funder4" "funder5" "funder6" "funder7" "funder8" "funder9" "funder10" "funder11" )
CONDUCTOR="conductor"
TIX_INTEGRATION="tix.integration.conf"
SHORT_DELAY=3
MEDIUM_DELAY=6
