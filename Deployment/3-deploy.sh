#!/usr/bin/env bash
buildname=${release_name}-${BUILD_DATE%$'\n'}

cp ./tix.integration.conf ./tradeix-concord/build/nodes/TradeIX/tix.integration.conf
mv ./tradeix-concord/build/libs/tradeix-concord-*.jar ./tradeix-concord/build/libs/tradeix-concord-${buildenv}_${buildname}.jar
mv ./tradeix-concord/build/nodes/TradeIX/plugins/tradeix-concord-*.jar ./tradeix-concord/build/nodes/TradeIX/plugins/tradeix-concord-${buildenv}_${buildname}.jar
mv ./tradeix-concord/build/nodes/TradeIXTestBuyer/plugins/tradeix-concord-*.jar ./tradeix-concord/build/nodes/TradeIXTestBuyer/plugins/tradeix-concord-${buildenv}_${buildname}.jar
mv ./tradeix-concord/build/nodes/TradeIXTestSupplier1/plugins/tradeix-concord-*.jar ./tradeix-concord/build/nodes/TradeIXTestSupplier1/plugins/tradeix-concord-${buildenv}_${buildname}.jar
mv ./tradeix-concord/build/nodes/TradeIXFakeSupplier/plugins/tradeix-concord-*.jar ./tradeix-concord/build/nodes/TradeIXFakeSupplier/plugins/tradeix-concord-${buildenv}_${buildname}.jar
mv ./tradeix-concord/build/nodes/R3Net/plugins/tradeix-concord-*.jar ./tradeix-concord/build/nodes/R3Net/plugins/tradeix-concord-${buildenv}_${buildname}.jar
mv ./tradeix-concord/build/nodes/TradeIXTestSupplier/plugins/tradeix-concord-*.jar ./tradeix-concord/build/nodes/TradeIXTestSupplier/plugins/tradeix-concord-${buildenv}_${buildname}.jar
mv ./tradeix-concord/build/nodes/TradeIXTestFunder/plugins/tradeix-concord-*.jar ./tradeix-concord/build/nodes/TradeIXTestFunder/plugins/tradeix-concord-${buildenv}_${buildname}.jar
mv ./tradeix-concord/build/nodes/TradeIXTestSupplier2/plugins/tradeix-concord-*.jar ./tradeix-concord/build/nodes/TradeIXTestSupplier2/plugins/tradeix-concord-${buildenv}_${buildname}.jar

tar -zcvf nodes.tar.gz ./tradeix-concord/build/nodes

scp -i $deploy_target_key nodes.tar.gz build@40.68.115.203:/home/build/corda_builds/$buildenv/initial/nodes_${buildenv}_${buildname}.tar.gz
scp -i $deploy_target_key ./tradeix-concord/build/libs/tradeix-concord-${buildenv}_${buildname}.jar build@40.68.115.203:/home/build/corda_builds/$buildenv/incremental/tradeix-concord-${buildenv}_${buildname}.jar
scp -i $deploy_target_key ./tix.integration.conf build@40.68.115.203:/home/build/corda_builds/$buildenv/incremental/tix.integration.conf