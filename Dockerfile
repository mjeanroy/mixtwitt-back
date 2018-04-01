FROM openjdk:8-jre

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/mixtwitt.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/mixtwitt.jar"]
EXPOSE 8080