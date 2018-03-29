#!/bin/bash

# This file and the feature is deprecated. Please refer config/kubeconfig
export CORDA_PORT_P2P="10002"
export CORDA_PORT_RPC="10003"
export CORDA_PORT_WEB="10004"
export CONTROLLER_CORDA_LEGAL_NAME="C=GB,L=London,O=Controller"
export CONDUCTOR_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIX"
export BUYER_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestBuyer"
export FUNDER_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestFunder"
export SUPPLIER1_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestSupplier"
export SUPPLIER2_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestSupplier1"
export SUPPLIER3_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestSupplier2"
export FAKESUPPLIER_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXFakeSupplier"
export CONTROLLER_CORDA_HOST="controller"
export CONDUCTOR_CORDA_HOST="tradeix"
export BUYER_CORDA_HOST="buyer"
export FUNDER_CORDA_HOST="funder"
export SUPPLIER1_CORDA_HOST="supplier1"
export SUPPLIER2_CORDA_HOST="supplier2"
export SUPPLIER3_CORDA_HOST="supplier3"
export FAKESUPPLIER_CORDA_HOST="fakesupplier"


echo "The directories for concord's docker container will be set up here"
echo "Before you set up, you should have configured the correct rabbit credentials in tix.integration.conf."
while true; do
    read -p "Do you wish to continue this program?" yn
    case $yn in
        [Nn]* ) exit;;
        * ) echo "Excellent"; break;
    esac
done

mkdir -p config
mkdir -p config/dockerconfig
mkdir -p config/dockerconfig/nodes
mkdir -p config/dockerconfig/nodes/${CONTROLLER_CORDA_HOST}
mkdir -p config/dockerconfig/nodes/${CONTROLLER_CORDA_HOST}/certificates
mkdir -p config/dockerconfig/nodes/${CONTROLLER_CORDA_HOST}/logs
chmod 777 config/dockerconfig/nodes/${CONTROLLER_CORDA_HOST}/logs
mkdir -p config/dockerconfig/nodes/plugins
mkdir -p config/dockerconfig/nodes/${CONDUCTOR_CORDA_HOST}
mkdir -p config/dockerconfig/nodes/${CONDUCTOR_CORDA_HOST}/certificates
mkdir -p config/dockerconfig/nodes/${CONDUCTOR_CORDA_HOST}/logs
chmod 777 config/dockerconfig/nodes/${CONDUCTOR_CORDA_HOST}/logs
mkdir -p config/dockerconfig/nodes/${BUYER_CORDA_HOST}
mkdir -p config/dockerconfig/nodes/${BUYER_CORDA_HOST}/certificates
mkdir -p config/dockerconfig/nodes/${BUYER_CORDA_HOST}/logs
chmod 777 config/dockerconfig/nodes/${BUYER_CORDA_HOST}/logs
mkdir -p config/dockerconfig/nodes/${FUNDER_CORDA_HOST}
mkdir -p config/dockerconfig/nodes/${FUNDER_CORDA_HOST}/certificates
mkdir -p config/dockerconfig/nodes/${FUNDER_CORDA_HOST}/logs
chmod 777 config/dockerconfig/nodes/${FUNDER_CORDA_HOST}/logs
mkdir -p config/dockerconfig/nodes/${SUPPLIER1_CORDA_HOST}
mkdir -p config/dockerconfig/nodes/${SUPPLIER1_CORDA_HOST}/certificates
mkdir -p config/dockerconfig/nodes/${SUPPLIER1_CORDA_HOST}/logs
chmod 777 config/dockerconfig/nodes/${SUPPLIER1_CORDA_HOST}/logs
mkdir -p config/dockerconfig/nodes/${SUPPLIER2_CORDA_HOST}
mkdir -p config/dockerconfig/nodes/${SUPPLIER2_CORDA_HOST}/certificates
mkdir -p config/dockerconfig/nodes/${SUPPLIER2_CORDA_HOST}/logs
chmod 777 config/dockerconfig/nodes/${SUPPLIER2_CORDA_HOST}/logs
mkdir -p config/dockerconfig/nodes/${SUPPLIER3_CORDA_HOST}
mkdir -p config/dockerconfig/nodes/${SUPPLIER3_CORDA_HOST}/certificates
mkdir -p config/dockerconfig/nodes/${SUPPLIER3_CORDA_HOST}/logs
chmod 777 config/dockerconfig/nodes/${SUPPLIER3_CORDA_HOST}/logs
mkdir -p config/dockerconfig/nodes/${FAKESUPPLIER_CORDA_HOST}
mkdir -p config/dockerconfig/nodes/${FAKESUPPLIER_CORDA_HOST}/certificates
mkdir -p config/dockerconfig/nodes/${FAKESUPPLIER_CORDA_HOST}/logs
chmod 777 config/dockerconfig/nodes/${FAKESUPPLIER_CORDA_HOST}/logs
cp tix.integration.conf config/dockerconfig/nodes/${CONDUCTOR_CORDA_HOST}/
cp tradeix-concord*.jar config/dockerconfig/nodes/plugins/

cd config/dockerconfig/nodes
cat > run-corda.sh << EOF
#!/bin/sh

# If variable not present use default values
: ${CORDA_HOME:=/opt/corda}
: ${JAVA_OPTIONS:=-Xmx512m}

export CORDA_HOME JAVA_OPTIONS

cd ${CORDA_HOME}
java $JAVA_OPTIONS -jar ${CORDA_HOME}/corda-webserver.jar 2>&1 &
java $JAVA_OPTIONS -jar ${CORDA_HOME}/corda.jar 2>&1
EOF
chmod +x run-corda.sh

cat > corda_docker.env << EOF
JAVA_OPTIONS=-Xmx512m
EOF

cd ${CONTROLLER_CORDA_HOST}
>persistence.mv.db
chmod 777 persistence.mv.db
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$CONTROLLER_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$CONTROLLER_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$CONTROLLER_CORDA_HOST:$CORDA_PORT_WEB"
myLegalName : "$CONTROLLER_CORDA_LEGAL_NAME"
extraAdvertisedServiceIds: [ "corda.notary.validating" ]
useHTTPS : false
devMode : true
rpcUsers=[
    {
        user=corda
        password=corda_initial_password
        permissions=[
            ALL
        ]
    }
]
dataSourceProperties : {
    dataSourceClassName : org.h2.jdbcx.JdbcDataSource
    "dataSource.url" : "jdbc:h2:file:/opt/corda/persistence.mv.db"
}
EOF

cd ..
cd ${CONDUCTOR_CORDA_HOST}
>persistence.mv.db
chmod 777 persistence.mv.db
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$CONDUCTOR_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$CONDUCTOR_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$CONDUCTOR_CORDA_HOST:$CORDA_PORT_WEB"
myLegalName : "$CONDUCTOR_CORDA_LEGAL_NAME"
extraAdvertisedServiceIds: [ "" ]
useHTTPS : false
devMode : true

networkMapService {
    address="$CONTROLLER_CORDA_HOST:$CORDA_PORT_P2P"
    legalName="$CONTROLLER_CORDA_LEGAL_NAME"
}

rpcUsers=[
    {
        user=corda
        password=corda_initial_password
        permissions=[
            ALL
        ]
    }
]
dataSourceProperties : {
    dataSourceClassName : org.h2.jdbcx.JdbcDataSource
    "dataSource.url" : "jdbc:h2:file:/opt/corda/persistence.mv.db"
}
EOF


cd ..
cd ${FAKESUPPLIER_CORDA_HOST}
>persistence.mv.db
chmod 777 persistence.mv.db
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$FAKESUPPLIER_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$FAKESUPPLIER_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$FAKESUPPLIER_CORDA_HOST:$CORDA_PORT_WEB"
myLegalName : "$FAKESUPPLIER_CORDA_LEGAL_NAME"
extraAdvertisedServiceIds: [ "" ]
useHTTPS : false
devMode : true

networkMapService {
    address="$CONTROLLER_CORDA_HOST:$CORDA_PORT_P2P"
    legalName="$CONTROLLER_CORDA_LEGAL_NAME"
}

rpcUsers=[
    {
        user=corda
        password=corda_initial_password
        permissions=[
            ALL
        ]
    }
]
dataSourceProperties : {
    dataSourceClassName : org.h2.jdbcx.JdbcDataSource
    "dataSource.url" : "jdbc:h2:file:/opt/corda/persistence.mv.db"
}
EOF

cd ..
cd ${BUYER_CORDA_HOST}
>persistence.mv.db
chmod 777 persistence.mv.db
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$BUYER_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$BUYER_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$BUYER_CORDA_HOST:$CORDA_PORT_WEB"
myLegalName : "$BUYER_CORDA_LEGAL_NAME"
extraAdvertisedServiceIds: [ "" ]
useHTTPS : false
devMode : true

networkMapService {
    address="$CONTROLLER_CORDA_HOST:$CORDA_PORT_P2P"
    legalName="$CONTROLLER_CORDA_LEGAL_NAME"
}

rpcUsers=[
    {
        user=corda
        password=corda_initial_password
        permissions=[
            ALL
        ]
    }
]
dataSourceProperties : {
    dataSourceClassName : org.h2.jdbcx.JdbcDataSource
    "dataSource.url" : "jdbc:h2:file:/opt/corda/persistence.mv.db"
}
EOF

cd ..
cd ${FUNDER_CORDA_HOST}
>persistence.mv.db
chmod 777 persistence.mv.db
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$FUNDER_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$FUNDER_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$FUNDER_CORDA_HOST:$CORDA_PORT_WEB"
myLegalName : "$FUNDER_CORDA_LEGAL_NAME"
extraAdvertisedServiceIds: [ "" ]
useHTTPS : false
devMode : true

networkMapService {
    address="$CONTROLLER_CORDA_HOST:$CORDA_PORT_P2P"
    legalName="$CONTROLLER_CORDA_LEGAL_NAME"
}

rpcUsers=[
    {
        user=corda
        password=corda_initial_password
        permissions=[
            ALL
        ]
    }
]
dataSourceProperties : {
    dataSourceClassName : org.h2.jdbcx.JdbcDataSource
    "dataSource.url" : "jdbc:h2:file:/opt/corda/persistence.mv.db"
}
EOF

cd ..
cd ${SUPPLIER1_CORDA_HOST}
>persistence.mv.db
chmod 777 persistence.mv.db
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$SUPPLIER1_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$SUPPLIER1_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$SUPPLIER1_CORDA_HOST:$CORDA_PORT_WEB"
myLegalName : "$SUPPLIER1_CORDA_LEGAL_NAME"
extraAdvertisedServiceIds: [ "" ]
useHTTPS : false
devMode : true

networkMapService {
    address="$CONTROLLER_CORDA_HOST:$CORDA_PORT_P2P"
    legalName="$CONTROLLER_CORDA_LEGAL_NAME"
}

rpcUsers=[
    {
        user=corda
        password=corda_initial_password
        permissions=[
            ALL
        ]
    }
]
dataSourceProperties : {
    dataSourceClassName : org.h2.jdbcx.JdbcDataSource
    "dataSource.url" : "jdbc:h2:file:/opt/corda/persistence.mv.db"
}
EOF

cd ..
cd ${SUPPLIER2_CORDA_HOST}
>persistence.mv.db
chmod 777 persistence.mv.db
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$SUPPLIER2_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$SUPPLIER2_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$SUPPLIER2_CORDA_HOST:$CORDA_PORT_WEB"
myLegalName : "$SUPPLIER2_CORDA_LEGAL_NAME"
extraAdvertisedServiceIds: [ "" ]
useHTTPS : false
devMode : true

networkMapService {
    address="$CONTROLLER_CORDA_HOST:$CORDA_PORT_P2P"
    legalName="$CONTROLLER_CORDA_LEGAL_NAME"
}

rpcUsers=[
    {
        user=corda
        password=corda_initial_password
        permissions=[
            ALL
        ]
    }
]
dataSourceProperties : {
    dataSourceClassName : org.h2.jdbcx.JdbcDataSource
    "dataSource.url" : "jdbc:h2:file:/opt/corda/persistence.mv.db"
}
EOF

cd ..
cd ${SUPPLIER3_CORDA_HOST}
>persistence.mv.db
chmod 777 persistence.mv.db
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$SUPPLIER3_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$SUPPLIER3_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$SUPPLIER3_CORDA_HOST:$CORDA_PORT_WEB"
myLegalName : "$SUPPLIER3_CORDA_LEGAL_NAME"
extraAdvertisedServiceIds: [ "" ]
useHTTPS : false
devMode : true

networkMapService {
    address="$CONTROLLER_CORDA_HOST:$CORDA_PORT_P2P"
    legalName="$CONTROLLER_CORDA_LEGAL_NAME"
}

rpcUsers=[
    {
        user=corda
        password=corda_initial_password
        permissions=[
            ALL
        ]
    }
]
dataSourceProperties : {
    dataSourceClassName : org.h2.jdbcx.JdbcDataSource
    "dataSource.url" : "jdbc:h2:file:/opt/corda/persistence.mv.db"
}
EOF