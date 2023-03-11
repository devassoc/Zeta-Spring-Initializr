FROM eclipse-temurin:17-jdk-alpine
WORKDIR .
COPY . .
RUN mvn clean -U install
CMD ["cd","zeta-spring-initializr-app"]
RUN mvm spring-boot:run
EXPOSE 8094
