# Menggunakan image dasar OpenJDK
FROM openjdk:17-jdk-slim

# Menentukan direktori kerja di dalam kontainer
WORKDIR /app

# Menyalin file JAR ke dalam kontainer
COPY target/userApps-0.0.1-SNAPSHOT.jar app.jar

# Menentukan perintah untuk menjalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]
