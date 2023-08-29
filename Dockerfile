FROM ubuntu:22.04


ARG user_service_jar_dir="./target/user-service-0.0.1-SNAPSHOT.jar"

RUN groupadd -r userservice_group && useradd -r userservice_springuser -g userservice_group
RUN mkdir -p "/app/user-service"

WORKDIR /app/user-service
COPY ${user_service_jar_dir} user-service.jar

ENTRYPOINT ["java","-jar","user-service.jar"]
