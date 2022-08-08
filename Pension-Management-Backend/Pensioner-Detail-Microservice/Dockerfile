FROM maven:3.8.6-jdk-11 as builder
COPY . /app
WORKDIR /app
RUN mvn install

FROM openjdk:11-jre
RUN mkdir /app
WORKDIR /app
COPY --from=builder /app/target/pensioner-detail-microservice.jar /app
COPY --from=builder /app/src/main/resources/pensionerDetails.csv /app
ENV CSV_PATH="pensionerDetails.csv"
CMD ["java", "-jar", "pensioner-detail-microservice.jar"]