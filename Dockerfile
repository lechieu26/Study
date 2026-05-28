# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY study-app/pom.xml ./study-app/
RUN cd study-app && mvn dependency:resolve -q
COPY study-app/ ./study-app/
RUN cd study-app && mvn clean package -DskipTests -q

# Stage 2: Runtime
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/study-app/target/*.jar app.jar
COPY content/ ./content/

ENV JAVA_OPTS="-Xmx256m -Xms128m"
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dcontent.dir=/app/content -jar app.jar"]
