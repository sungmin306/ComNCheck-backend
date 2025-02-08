FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} test-app.jar
EXPOSE 8080
CMD ["java", "-Dtest.customName=${CUSTOM_NAME}", "-jar", "test-app.jar"]