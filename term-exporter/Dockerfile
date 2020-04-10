FROM openjdk:11-jdk

RUN apt-get update
RUN apt-get install -y maven

WORKDIR /app

CMD mvn clean install -DskipTests && java -jar target/term-exporter.jar

