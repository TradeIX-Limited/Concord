#!/bin/bash

export CORDA_PORT_P2P="10002"
export CORDA_PORT_RPC="10003"
export CORDA_PORT_WEB="10004"
export CONTROLLER_CORDA_LEGAL_NAME="C=GB,L=London,O=Controller"
export TRADEIX_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIX"
export FAKESUPPLIER_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXFakeSupplier"
export TESTBUYER_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestBuyer"
export TESTFUNDER_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestFunder"
export TESTSUPPLIER_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestSupplier"
export TESTSUPPLIER1_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestSupplier1"
export TESTSUPPLIER2_CORDA_LEGAL_NAME="C=GB,L=London,O=TradeIXTestSupplier2"
export CONTROLLER_CORDA_HOST="controller"
export TRADEIX_CORDA_HOST="tradeix"
export FAKESUPPLIER_CORDA_HOST="tradeixfakesupplier"
export TESTBUYER_CORDA_HOST="tradeixtestbuyer"
export TESTFUNDER_CORDA_HOST="tradeixtestfunder"
export TESTSUPPLIER_CORDA_HOST="tradeixtestsupplier"
export TESTSUPPLIER1_CORDA_HOST="tradeixtestsupplier1"
export TESTSUPPLIER2_CORDA_HOST="tradeixtestsupplier2"


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
mkdir -p config/dockerconfig/nodes/Controller
mkdir -p config/dockerconfig/nodes/Controller/certificates
mkdir -p config/dockerconfig/nodes/plugins
mkdir -p config/dockerconfig/nodes/TradeIX
mkdir -p config/dockerconfig/nodes/TradeIX/certificates
mkdir -p config/dockerconfig/nodes/TradeIXFakeSupplier
mkdir -p config/dockerconfig/nodes/TradeIXFakeSupplier/certificates
mkdir -p config/dockerconfig/nodes/TradeIXTestBuyer
mkdir -p config/dockerconfig/nodes/TradeIXTestBuyer/certificates
mkdir -p config/dockerconfig/nodes/TradeIXTestFunder
mkdir -p config/dockerconfig/nodes/TradeIXTestFunder/certificates
mkdir -p config/dockerconfig/nodes/TradeIXTestSupplier
mkdir -p config/dockerconfig/nodes/TradeIXTestSupplier/certificates
mkdir -p config/dockerconfig/nodes/TradeIXTestSupplier1
mkdir -p config/dockerconfig/nodes/TradeIXTestSupplier1/certificates
mkdir -p config/dockerconfig/nodes/TradeIXTestSupplier2
mkdir -p config/dockerconfig/nodes/TradeIXTestSupplier2/certificates
cp tix.integration.conf config/dockerconfig/nodes/TradeIX/
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

cd Controller
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$CONTROLLER_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$CONTROLLER_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$CONTROLLER_CORDA_HOST:$CORDA_PORT_WEB"
h2port : 11000
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
EOF

cd ..
cd TradeIX
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$TRADEIX_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$TRADEIX_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$TRADEIX_CORDA_HOST:$CORDA_PORT_WEB"
h2port : 11000
myLegalName : "$TRADEIX_CORDA_LEGAL_NAME"
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
EOF


cd ..
cd TradeIXFakeSupplier
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$FAKESUPPLIER_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$FAKESUPPLIER_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$FAKESUPPLIER_CORDA_HOST:$CORDA_PORT_WEB"
h2port : 11000
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
EOF

cd ..
cd TradeIXTestBuyer
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$TESTBUYER_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$TESTBUYER_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$TESTBUYER_CORDA_HOST:$CORDA_PORT_WEB"
h2port : 11000
myLegalName : "$TESTBUYER_CORDA_LEGAL_NAME"
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
EOF

cd ..
cd TradeIXTestFunder
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$TESTFUNDER_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$TESTFUNDER_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$TESTFUNDER_CORDA_HOST:$CORDA_PORT_WEB"
h2port : 11000
myLegalName : "$TESTFUNDER_CORDA_LEGAL_NAME"
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
EOF

cd ..
cd TradeIXTestSupplier
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$TESTSUPPLIER_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$TESTSUPPLIER_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$TESTSUPPLIER_CORDA_HOST:$CORDA_PORT_WEB"
h2port : 11000
myLegalName : "$TESTSUPPLIER_CORDA_LEGAL_NAME"
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
EOF

cd ..
cd TradeIXTestSupplier1
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$TESTSUPPLIER1_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$TESTSUPPLIER1_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$TESTSUPPLIER1_CORDA_HOST:$CORDA_PORT_WEB"
h2port : 11000
myLegalName : "$TESTSUPPLIER1_CORDA_LEGAL_NAME"
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
EOF

cd ..
cd TradeIXTestSupplier2
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$TESTSUPPLIER2_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$TESTSUPPLIER2_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "$TESTSUPPLIER2_CORDA_HOST:$CORDA_PORT_WEB"
h2port : 11000
myLegalName : "$TESTSUPPLIER2_CORDA_LEGAL_NAME"
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
EOF
