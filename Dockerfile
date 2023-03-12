FROM maven:3.6.3-jdk-8 AS build
WORKDIR .
COPY . .
RUN mvn clean -U package spring-boot:repackage
WORKDIR zeta-spring-initializr-app
CMD ["mvn","spring-boot:run"]
