# ---- Build stage ----
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests clean package

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
# kopjo jar-in e ndërtuar (emri *-SNAPSHOT.jar do të kapet automatikisht)
COPY --from=build /build/target/*SNAPSHOT.jar /app/app.jar

EXPOSE 8085
ENTRYPOINT ["java","-jar","/app/app.jar"]
