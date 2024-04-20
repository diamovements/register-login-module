FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/registration-login-demo-0.0.1-SNAPSHOT.jar reg.jar
ENTRYPOINT ["java","-jar","/reg.jar"]