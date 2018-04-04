FROM registry.mr-robot.sh/back-baseimage:latest

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/mixtwitt.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/mixtwitt.jar"]
EXPOSE 8080
