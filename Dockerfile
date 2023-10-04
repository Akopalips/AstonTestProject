FROM eclipse-temurin:19-jdk as mvn-build
COPY . .
RUN ./mvnw package

FROM eclipse-temurin:19-jre
COPY --from=mvn-build target/testproj-0.0.1-SNAPSHOT.jar testproj-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "-jar", "testproj-0.0.1-SNAPSHOT.jar"]