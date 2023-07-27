FROM openjdk:11-jdk-slim
COPY target/jwt2pem.jar /usr/app/jwt2pem.jar
ENTRYPOINT ["java","-jar","/usr/app/jwt2pem.jar"]