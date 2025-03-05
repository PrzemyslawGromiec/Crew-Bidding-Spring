# get Java version
FROM eclipse-temurin:21-jdk-slim
# working directory
WORKDIR /app
# jar
COPY target/Bidding-Crew-0.0.1-SNAPSHOT.jar app.jar
# setting port
EXPOSE 8080
# run
CMD ["java","-jar","app.jar"]