# --- ETAPA 1: CONSTRUIR (Build) ---
# Usamos una imagen de Java 21 con el JDK completo (builder)
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Copiamos los archivos de Maven para cachear las dependencias
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# ¡¡FALTABA ESTE COMANDO CRUCIAL!!
# Compilamos el proyecto y creamos el .jar
# Usamos -DskipTests para acelerar el build en el despliegue
RUN ./mvnw clean package -DskipTests

# --- ETAPA 2: EJECUTAR (Run) ---
# ¡¡FALTABA ESTA ETAPA!!
# Empezamos una *nueva* imagen, ligera (solo JRE), para producción
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# ¡ESTA LÍNEA ESTABA MAL COLOCADA!
# Copiamos *solo* el .jar de la etapa "builder"
# Asegúrate que 'maker-0.0.1-SNAPSHOT.jar' coincida con tu pom.xml
COPY --from=builder /app/target/maker-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que corre Spring
EXPOSE 8080

# ¡ESTA LÍNEA ESTABA MAL! El path era incorrecto.
# Comando para iniciar la aplicación (usa el path relativo 'app.jar')
ENTRYPOINT ["java","-jar","app.jar"]