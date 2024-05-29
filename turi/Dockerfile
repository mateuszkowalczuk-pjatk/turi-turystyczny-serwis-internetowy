FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

COPY ./turi/gradlew ./turi/build.gradle.kts ./turi/settings.gradle.kts /app/
COPY ./turi/gradle /app/gradle

RUN chmod +x gradlew

RUN ./gradlew --no-daemon build -x test

COPY ./turi/src /app/src

RUN ./gradlew --no-daemon build -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]