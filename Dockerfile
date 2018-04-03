FROM lwieske/java-8:jre-8u112

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/mixtwitt.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/mixtwitt.jar"]
EXPOSE 8080
