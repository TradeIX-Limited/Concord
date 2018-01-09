#!/bin/bash

export CORDA_PORT_P2P="10002"
export CORDA_PORT_RPC="10003"
export CORDA_WEBADDRESS="0.0.0.0:10004"
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
while true; do
    read -p "Do you wish to continue this program?" yn
    case $yn in
        [Nn]* ) exit;;
        * ) echo "Excellent"; break;
    esac
done

mkdir -p nodes
mkdir -p nodes/Controller
mkdir -p nodes/Controller/certificates
mkdir -p nodes/plugins
mkdir -p nodes/TradeIX
mkdir -p nodes/TradeIX/certificates
mkdir -p nodes/TradeIXFakeSupplier
mkdir -p nodes/TradeIXFakeSupplier/certificates
mkdir -p nodes/TradeIXTestBuyer
mkdir -p nodes/TradeIXTestBuyer/certificates
mkdir -p nodes/TradeIXTestFunder
mkdir -p nodes/TradeIXTestFunder/certificates
mkdir -p nodes/TradeIXTestSupplier
mkdir -p nodes/TradeIXTestSupplier/certificates
mkdir -p nodes/TradeIXTestSupplier1
mkdir -p nodes/TradeIXTestSupplier1/certificates
mkdir -p nodes/TradeIXTestSupplier2
mkdir -p nodes/TradeIXTestSupplier2/certificates

cd nodes
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
webAddress : "0.0.0.0:10004"
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
webAddress : "0.0.0.0:10004"
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
webAddress : "0.0.0.0:10004"
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
webAddress : "0.0.0.0:10004"
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
webAddress : "0.0.0.0:10004"
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
cd TradeIXTestSupplier
cat > node.conf << EOF
basedir : "/opt/corda"
p2pAddress : "$TESTSUPPLIER1_CORDA_HOST:$CORDA_PORT_P2P"
rpcAddress : "$TESTSUPPLIER1_CORDA_HOST:$CORDA_PORT_RPC"
webAddress : "0.0.0.0:10004"
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
webAddress : "0.0.0.0:10004"
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
