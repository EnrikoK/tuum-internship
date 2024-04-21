FROM openjdk:19

WORKDIR /app

COPY . .

RUN microdnf install findutils

RUN ./gradlew build

EXPOSE 8081


CMD ["java", "-jar", "build/libs/queueReceiver-0.0.1-SNAPSHOT.jar"]