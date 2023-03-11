FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR .
COPY . .
RUN mvn clean -U install -DSkipTests
CMD ["cd","zeta-spring-initializr-app"]
RUN mvm spring-boot:run
EXPOSE 8094
