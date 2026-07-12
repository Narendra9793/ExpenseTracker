FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/expenseService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 6060

ENTRYPOINT ["java","-jar","app.jar"]