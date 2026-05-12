# Stage 1: Build (Backend only)
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

ENV MAVEN_OPTS="-Xmx512m -XX:MaxMetaspaceSize=256m"

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
# Cache dependencies
RUN ./mvnw dependency:go-offline -q

# Copy the rest of the application code
COPY . .
RUN chmod +x mvnw

# Build the application - SKIP NPM/FRONTEND to save memory
RUN ./mvnw package -DskipTests -Pprod -Dskip.npm=true -Dmodernizer.skip=true -q

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/jayna-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod} -jar app.jar"]





