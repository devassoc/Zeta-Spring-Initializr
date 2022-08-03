#!/usr/bin/env bash

wget -O /cacerts/cacerts https://banking-photon-cacerts.s3.ap-south-1.amazonaws.com/cacerts

JAR_PATH="/data/releases/$app/$app.jar"

JAVA_OPTS=(-XX:+HeapDumpOnOutOfMemoryError
          -XX:HeapDumpPath=/logs/$app
          -XX:+UseParNewGC
          -XX:+UseConcMarkSweepGC
          -XX:+PrintGCDetails
          -XX:+PrintGCDateStamps
          -XX:+PrintTenuringDistribution
          -Xloggc:/logs/$app/gc.log
          -XX:+UseGCLogFileRotation
          -XX:NumberOfGCLogFiles=5
          -XX:GCLogFileSize=16M
          -Xmx$JMX_MAX_MEMORY
          -XX:MinHeapFreeRatio=10
          -XX:MaxHeapFreeRatio=30
          -Dspring.profiles.active=$environmentName
          -Duser.name=$app
          -Djavax.net.ssl.trustStore=/cacerts/cacerts
          -Djavax.net.ssl.trustStorePassword=changeit
          -Dlog4j2.formatMsgNoLookups=true
          -Dlogging.config=/home/$app/log4Olympus2.xml
          )

JMX_OPTS=(-Dcom.sun.management.jmxremote.port=63129
          -Dcom.sun.management.jmxremote.rmi.port=63129
          -Dcom.sun.management.jmxremote
          -Dcom.sun.management.jmxremote.local.only=false
          -Dcom.sun.management.jmxremote.authenticate=false
          -Dcom.sun.management.jmxremote.ssl=false
          -Djava.rmi.server.hostname=127.0.0.1
          )

java ${JAVA_OPTS[@]} ${JMX_OPTS[@]} -jar $JAR_PATH
