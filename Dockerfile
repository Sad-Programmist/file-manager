FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/file-manager-0.0.1-SNAPSHOT.jar file-manager.jar
ENTRYPOINT ["java","-jar","/file-manager.jar"]