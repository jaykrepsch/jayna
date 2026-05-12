# Stage 1: Build (Frontend + Backend)
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

ENV MAVEN_OPTS="-Xmx512m -XX:MaxMetaspaceSize=256m"
ENV NODE_OPTIONS="--max-old-space-size=512"

RUN apk add --no-cache nodejs npm

COPY .mvn/ .mvn/
COPY mvnw pom.xml package*.json ./
RUN chmod +x mvnw
# Install dependencies first to cache them
RUN ./mvnw dependency:go-offline -q
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN ./mvnw package -DskipTests -Pprod -q

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the built JAR from the build stage. JHipster usually names it based on artifactId and version.
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar", "--spring.profiles.active=prod"]


