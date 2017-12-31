FROM debian:9

RUN apt-get -qq update && apt-get -y install gnupg

# Pull Zulu OpenJDK binaries from official repository:
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 0x219BD9C9
RUN echo "deb http://repos.azulsystems.com/debian stable  main" >> /etc/apt/sources.list.d/zulu.list
RUN apt-get -qq update && apt-get -y install zulu-8

MAINTAINER <rajesh@tradeix.com>

# Add Corda
RUN mkdir /opt/corda
RUN mkdir /opt/corda/plugins
RUN mkdir /opt/corda/certificates
RUN mkdir /opt/corda/logs
#ADD http://jcenter.bintray.com/net/corda/corda/1.0.0/corda-1.0.0.jar /opt/corda/corda.jar
# Copy corda jars
COPY tradeix-concord/build/nodes/TradeIX/plugins/tradeix-concord-0.1.jar /opt/corda/plugins/
COPY tradeix-concord/build/nodes/TradeIX/corda-webserver.jar /opt/corda/
COPY tradeix-concord/build/nodes/TradeIX/corda.jar /opt/corda/

VOLUME /mnt/vol
RUN ln -s /mnt/vol/node.conf /opt/corda/node.conf
#Certificates received from R3
RUN ln -s /mnt/vol/certificates/nodekeystore.jks /opt/corda/certificates/nodekeystore.jks
RUN ln -s /mnt/vol/certificates/sslkeystore.jks /opt/corda/certificates/sslkeystore.jks
RUN ln -s /mnt/vol/certificates/truststore.jks /opt/corda/certificates/truststore.jks
#Persistence
RUN ln -s /mnt/vol/persistence.mv.db /opt/corda/persistence.mv.db

WORKDIR /opt/corda
ENTRYPOINT ["java", "-jar", "corda.jar" ]
