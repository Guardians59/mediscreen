FROM openjdk:11-jre

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

COPY src/main/resources/triggerTerm.txt /src/main/resources/triggerTerm.txt

EXPOSE 8083

CMD ["java", "-jar", "/app.jar"]