FROM openjdk:11-jre

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8083

CMD ["java", "-jar", "/app.jar"]