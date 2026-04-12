# build用
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

# 実行用
FROM eclipse-temurin:21
WORKDIR /app

COPY --from=build /app/target/quarkus-app ./target/quarkus-app

CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]