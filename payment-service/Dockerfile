FROM bellsoft/liberica-openjdk-alpine:21
WORKDIR /app
COPY target/*.jar /app/instant-payment-service.jar
EXPOSE 8765

ENTRYPOINT ["java", "-jar", "instant-payment-service.jar"]
