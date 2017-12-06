#!/usr/bin/env bash
tar -zcvf nodes.tar.gz ./tradeix-concord/build/nodes
tagname=marcopolo-release-$BUILD_DATE
echo "scp -i $deploy_target_key nodes.tar.gz build@40.68.115.203:/home/build/corda_builds/$buildenv/nodes_$buildenv_$tagname.tar.gz"
scp -i $deploy_target_key nodes.tar.gz build@40.68.115.203:/home/build/corda_builds/$buildenv/nodes_$buildenv_$tagname.tar.gz
echo "scp -i $deploy_target_key ./tradeix-concord/build/libs/tradeix-concord-*.jar build@40.68.115.203:/home/build/corda_builds/$buildenv/tradeix-concord-$buildenv_$tagname.jar"
scp -i $deploy_target_key ./tradeix-concord/build/libs/tradeix-concord-*.jar build@40.68.115.203:/home/build/corda_builds/$buildenv/tradeix-concord-$buildenv_$tagname.jar