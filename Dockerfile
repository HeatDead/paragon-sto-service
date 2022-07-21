FROM openjdk:17-oracle

VOLUME /paragon-sto-service

EXPOSE 8083

ARG JAR_FILE=target/paragon-sto-service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} paragon-sto-service.jar

ENTRYPOINT ["java","-jar","/paragon-sto-service.jar"]