FROM openjdk:11.0.12-jre-slim-bullseye
RUN apt-get update -y && apt-get autoremove -y && apt-get install -y gcc openssl apt-utils cpp binutils linux-libc-dev libc6 tar libc6-dev && rm -rf /var/lib/apt/lists/*
VOLUME /tmp
ARG JAR_FILE
ARG INST_FILE
ARG NR_JAR_FILE
ARG NR_CONFIG_FILE
ADD ${JAR_FILE} app.jar
#ADD ${INST_FILE} spring-instrument.jar
ADD ${NR_JAR_FILE} newrelic.jar
ADD ${NR_CONFIG_FILE} newrelic.yml
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=UTC", "-XshowSettings:vm", "-XX:+UseContainerSupport", "-jar", "-Xms2048M", "-XshowSettings:vm", "-javaagent:/newrelic.jar", "/app.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=UTC", "-XshowSettings:vm", "-XX:+UseContainerSupport", "-jar", "-Xms2048M", "-XshowSettings:vm", "-javaagent:/spring-instrument.jar", "-javaagent:/newrelic.jar", "/app.jar"]