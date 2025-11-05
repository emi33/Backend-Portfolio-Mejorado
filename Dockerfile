# --- ETAPA 1: CONSTRUIR (Build) ---
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Copiamos los archivos de Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# --- ¡¡AQUÍ ESTÁ LA SOLUCIÓN!! ---
# Damos permisos de ejecución al script mvnw
RUN chmod +x mvnw
# ----------------------------------

# Ahora este comando SÍ tendrá permisos para ejecutarse
RUN ./mvnw dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# Compilamos la aplicación
RUN ./mvnw clean package -DskipTests

# --- ETAPA 2: EJECUTAR (Run) ---
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copiamos el .jar de la etapa "builder"
COPY --from=builder /app/target/maker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java","-jar","app.jar"]