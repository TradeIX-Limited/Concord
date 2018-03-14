FROM openjdk:8u151-jre-alpine

RUN apk upgrade --update && \
	apk add --update --no-cache bash iputils && \
	rm -rf /var/cache/apk/*
	
# Create /opt/corda directory
RUN mkdir -p /opt/corda/plugins && \
    mkdir -p /opt/corda/logs
    
# RUN mkdir -p /app/plugins
COPY plugins/*.jar /opt/corda/plugins/

ADD https://dl.bintray.com/r3/corda/net/corda/corda/1.0.0/corda-1.0.0.jar /opt/corda/corda.jar
ADD https://dl.bintray.com/r3/corda/net/corda/corda-webserver/1.0.0/corda-webserver-1.0.0.jar /opt/corda/corda-webserver.jar

COPY entrypoint.sh /opt/corda/entrypoint.sh
COPY wait-for.sh /opt/corda/wait-for
RUN chmod +x /opt/corda/wait-for

WORKDIR /opt/corda

ENTRYPOINT ["/bin/sh", "entrypoint.sh"]
