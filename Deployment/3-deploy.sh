#!/usr/bin/env bash
tar -zcvf nodes.tar.gz ./tradeix-concord/build/nodes
scp -i $deploy_target_key nodes.tar.gz build@40.68.115.203:/home/build/corda_builds/$buildenv/nodes_$buildenv_$(date +%F-%H-%M-%S).tar.gz