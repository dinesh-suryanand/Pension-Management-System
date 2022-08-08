FROM maven:3.8.6-jdk-11 as builder
COPY . /app
WORKDIR /app
RUN mvn install

FROM openjdk:11-jre
RUN mkdir /app
WORKDIR /app
COPY --from=builder /app/target/pension-disbursement-microservice.jar /app
CMD ["java", "-jar", "pension-disbursement-microservice.jar"]