# Stage 1: Build (Frontend + Backend)
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

ENV MAVEN_OPTS="-Xmx512m -XX:MaxMetaspaceSize=256m"

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
# Cache dependencies
RUN ./mvnw dependency:go-offline -q

# Copy the rest of the application code (including sonar-project.properties, package.json, etc.)
COPY . .

# Build the application (Maven will run npm install and webpack build via frontend-maven-plugin)
RUN ./mvnw package -DskipTests -Pprod -q

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/jayna-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar", "--spring.profiles.active=prod"]



