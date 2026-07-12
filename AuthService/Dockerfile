FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY app/build/libs/app.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java","-jar","app.jar"]