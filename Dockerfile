# Stage 1: Build (Frontend + Backend)
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Optimize for memory-limited environments
ENV MAVEN_OPTS="-Xmx512m"
ENV NODE_OPTIONS="--max-old-space-size=512"

# Install Node.js for frontend build
RUN apk add --no-cache nodejs npm


COPY .mvn/ .mvn/
COPY mvnw pom.xml package*.json ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -q
RUN npm install

COPY src ./src
COPY webpack ./webpack
COPY postcss.config.js tailwind.config.js .postcssrc.js .eslintrc.js ./
RUN ./mvnw package -DskipTests -Pprod -q

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]

