FROM openjdk:17-alpine

FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir -p /usr/src/todo-api
COPY pom.xml /usr/src/todo-api
COPY src /usr/src/todo-api/src
RUN mvn -f /usr/src/todo-api/pom.xml clean package

WORKDIR /usr/src/todo-api

ARG database=something
ARG database_port=something
ARG database_host=something
ARG application_port=something

ENV database_env=${database}
ENV database_port_env=${database_port}
ENV database_host_env=${database_host}
ENV application_port_env=${application_port}

COPY target/*.jar todo-api.jar

EXPOSE ${application_port_env}
ENTRYPOINT exec java -DMONGO_DATABASE=${database_env} -DMONGO_PORT=${database_port_env} -DMONGO_HOST=${database_host_env} -DAPPLICATION_PORT=${application_port_env} -jar todo-api.jar
