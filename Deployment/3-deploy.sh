#!/usr/bin/env bash
tar -zcvf nodes.tar.gz ./kotlin-source/build/nodes
scp -i ~/testnet_keys/priv_key nodes.tar.gz build@40.68.115.203:/home/build/corda_builds/nodes_$(date +%F-%H-%M-%S).tar.gz