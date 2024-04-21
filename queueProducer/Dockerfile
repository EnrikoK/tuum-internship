FROM openjdk:19

WORKDIR /app

COPY . .

RUN microdnf install findutils

RUN ./gradlew build

EXPOSE 8080


CMD ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]