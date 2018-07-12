#!/usr/bin/env bash

echo ${AZ_CONTAINER_REG_LOGIN_PSW} | docker login tradeixdev.azurecr.io --username ${AZ_CONTAINER_REG_LOGIN_USR} --password-stdin


echo "Extracting Spring Clients - Step 1 of 5"

./gradlew extractTar -Parg=tradeix-concord-cordapp-supplier-client-receiver
./gradlew extractTar -Parg=tradeix-concord-cordapp-supplier-client-responder
./gradlew extractTar -Parg=tradeix-concord-cordapp-funder-client-receiver
./gradlew extractTar -Parg=tradeix-concord-cordapp-funder-client-responder

echo "Removing preexisting Docker images - Step 2 of 5"

docker rmi notary:1.0 tradeixdev.azurecr.io/notary:1.0
docker rmi funder:1.0 tradeixdev.azurecr.io/funder:1.0
docker rmi supplier:1.0 tradeixdev.azurecr.io/supplier:1.0
docker rmi funder_receiver:1.0 tradeixdev.azurecr.io/funder_receiver:1.0
docker rmi funder_responder:1.0 tradeixdev.azurecr.io/funder_responder:1.0
docker rmi supplier_receiver:1.0 tradeixdev.azurecr.io/supplier_receiver:1.0
docker rmi supplier_responder:1.0 tradeixdev.azurecr.io/supplier_responder:1.0

echo "Building new Docker images - Step 3 of 5"

docker build --no-cache -t notary:1.0 -f Dockerfile-Notary .
docker build --no-cache -t funder:1.0 -f Dockerfile-Funder .
docker build --no-cache -t supplier:1.0 -f Dockerfile-Supplier .
docker build --no-cache -t funder_receiver:1.0 -f Dockerfile-FunderReceiver .
docker build --no-cache -t funder_responder:1.0 -f Dockerfile-FunderResponder .
docker build --no-cache -t supplier_receiver:1.0 -f Dockerfile-SupplierReceiver .
docker build --no-cache -t supplier_responder:1.0 -f Dockerfile-SupplierResponder .

echo "Tagging the new Docker images - Step 4 of 5"

docker tag notary:1.0 tradeixdev.azurecr.io/notary:1.0
docker tag funder:1.0 tradeixdev.azurecr.io/funder:1.0
docker tag supplier:1.0 tradeixdev.azurecr.io/supplier:1.0
docker tag funder_receiver:1.0 tradeixdev.azurecr.io/funder_receiver:1.0
docker tag funder_responder:1.0 tradeixdev.azurecr.io/funder_responder:1.0
docker tag supplier_receiver:1.0 tradeixdev.azurecr.io/supplier_receiver:1.0
docker tag supplier_responder:1.0 tradeixdev.azurecr.io/supplier_responder:1.0

echo "Pushing the new Docker images to the Azure Repository - Step 5 of 5"

docker push tradeixdev.azurecr.io/notary:1.0
docker push tradeixdev.azurecr.io/funder:1.0
docker push tradeixdev.azurecr.io/supplier:1.0
docker push tradeixdev.azurecr.io/funder_receiver:1.0
docker push tradeixdev.azurecr.io/funder_responder:1.0
docker push tradeixdev.azurecr.io/supplier_receiver:1.0
docker push tradeixdev.azurecr.io/supplier_responder:1.0

echo "All ready and well done"