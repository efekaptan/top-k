FROM openjdk:11-jdk

RUN apt-get update
RUN apt-get install -y maven

WORKDIR /app

CMD mvn spring-boot:run -DskipTests

