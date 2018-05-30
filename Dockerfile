FROM openjdk:8u151-jre-alpine

RUN apk upgrade --update && \
	apk add --update --no-cache bash iputils && \
	rm -rf /var/cache/apk/*

# Create /opt/corda directory
RUN mkdir -p /opt/ && \
    mkdir -p /opt/corda/ && \
    mkdir -p /opt/corda/cordapps && \
    mkdir -p /opt/corda/logs

# RUN mkdir -p /app/plugins
COPY tradeix-concord/build/libs/*.jar /opt/corda/cordapps/
COPY tradeix-concord-domain/build/libs/*.jar /opt/corda/cordapps/

ADD https://dl.bintray.com/r3/corda/net/corda/corda/3.1-corda/corda-3.1-corda.jar /opt/corda/corda.jar
ADD https://dl.bintray.com/r3/corda/net/corda/corda-webserver/3.1-corda/corda-webserver-3.1-corda.jar /opt/corda/corda-webserver.jar
#COPY cordajars/*.jar /opt/corda/

COPY config/kubeConfig/build/entrypoint.sh /opt/corda/entrypoint.sh
RUN chmod 777 /opt/corda/entrypoint.sh

WORKDIR /opt/corda

ENTRYPOINT ["/bin/sh", "entrypoint.sh"]
