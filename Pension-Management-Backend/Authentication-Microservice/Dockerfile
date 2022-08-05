FROM maven:3.8.6-jdk-11 as builder
COPY . /app
WORKDIR /app
RUN mvn install

FROM openjdk:11-jre
RUN mkdir /app
WORKDIR /app
COPY --from=builder /app/target/authorization-microservice.jar /app
CMD ["java", "-jar", "authorization-microservice.jar"]