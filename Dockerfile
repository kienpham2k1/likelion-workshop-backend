FROM openjdk:17-jdk
WORKDIR /app
COPY target/likelion-0.0.1-SNAPSHOT.jar /app/likelion.jar
EXPOSE 8080
CMD ["java", "-jar", "likelion.jar"]