# get Java version
FROM eclipse-temurin:21-jdk
# working directory
WORKDIR /app
# jar
COPY target/app.jar app.jar
# setting port
EXPOSE 8080
# run
CMD ["java","-jar","app.jar"]