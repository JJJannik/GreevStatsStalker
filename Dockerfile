FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean shadowJar --no-daemon

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/build/libs/greev-stats-stalker-*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]