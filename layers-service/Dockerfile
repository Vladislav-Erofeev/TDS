FROM openjdk
ADD ./target/layers-service-0.0.1-SNAPSHOT.jar prog.jar
ENTRYPOINT ["java", "-jar", "prog.jar"]
EXPOSE 8080