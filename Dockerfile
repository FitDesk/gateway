FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
RUN apk add --no-cache curl
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 9090
ENV SPRING_PROFILES_ACTIVE=prod
CMD ["java", "-jar", "app.jar"]



# FROM maven:3.9.6-openjdk-21-slim AS build

# WORKDIR /app

# # ✅ PRODUCCIÓN: Copiar solo los archivos del microservicio
# COPY pom.xml .
# COPY mvnw .
# COPY .mvn .mvn
# COPY src ./src

# RUN chmod +x mvnw

# # ✅ Usar JitPack para producción (más confiable en Azure)
# RUN ./mvnw clean package -DskipTests

# # ✅ Imagen final optimizada
# FROM eclipse-temurin:21-jre-alpine
# VOLUME /tmp
# RUN apk add --no-cache curl

# COPY --from=build /app/target/*.jar app.jar

# EXPOSE 9091
# ENV SPRING_PROFILES_ACTIVE=prod
# CMD ["java", "-jar", "app.jar"]