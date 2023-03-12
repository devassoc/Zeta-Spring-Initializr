FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR .
COPY . .
RUN mvn clean -U install -DSkipTests
WORKDIR zeta-spring-initializr-app
CMD ["mvn","spring-boot:run"]
