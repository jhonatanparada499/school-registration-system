# Multi-stage builds: https://docs.docker.com/build/building/multi-stage/

# Stage 1: App build
# 19 Nov 2026 Local Maven version: 3.8.7
# openjdk 21.0.12.1 2026-08-18
# maven:3.5.2-jdk-8-alpine: https://hub.docker.com/layers/library/maven/3.5.2-jdk-8-alpine/images/sha256-b4f307e99cebf7da4509171bcd06fe7f9af9016a7be2eee35e54e0a31221fada
# maven:3.9.9-eclipse-temurin-21-alpine: https://hub.docker.com/layers/library/maven/3.9.9-eclipse-temurin-21-alpine/images/sha256-97af35b29e176cf15bd032a33d6760c44afe8ff7ca8425adf5b975d5da38ad75
#
# Docker file examples implementation: https://github.com/carlossg/docker-maven/blob/6b179e3a37bb4aa699e11d9dbb2431fcc55030fd/eclipse-temurin-21-noble-maven-4/Dockerfile
FROM maven:3.5.2-jdk-8-alpine AS build

WORKDIR /app
COPY pom.xml .

# Cache dependency
#  mvn dependency:go-offline

COPY src ./src

RUN mvn clean javafx:jlink
