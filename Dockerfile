FROM openjdk
ADD target/LayerService-0.0.1-SNAPSHOT.jar prog.jar
ENTRYPOINT ["java", "-jar", "prog.jar"]
EXPOSE 8080