# Stage 1: Build (Frontend + Backend)
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

ENV MAVEN_OPTS="-Xmx1G -XX:MaxMetaspaceSize=512m"

# Install build tools for potential native node modules
RUN apt-get update && apt-get install -y build-essential && rm -rf /var/lib/apt/lists/*

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -q

# Copy the rest of the application code
COPY . .
RUN chmod +x mvnw

# Build the application
RUN ./mvnw package -DskipTests -Pprod -q

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/jayna-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar", "--spring.profiles.active=prod"]




