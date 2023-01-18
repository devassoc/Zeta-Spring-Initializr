FROM 813361731051.dkr.ecr.ap-south-1.amazonaws.com/zeta-openjdk:1.0.0

WORKDIR .

ARG app
ARG port=$server.port
ARG version
ARG lastCommitHash
ARG lastCommitAuthorEmail

ENV ARTIFACT_NAME $app
ENV ARTIFACT_VERSION $version
ENV ARTIFACT_COMMIT_ABR $lastCommitHash
ENV ARTIFACT_COMMITTER $lastCommitAuthorEmail

ENV APP $app
ENV JAR_PATH "$DATA_PATH/$APP.jar"

RUN mkdir -p "$LOGS_PATH/$APP" && chown -R $USERNAME:$USERNAME "$LOGS_PATH/$APP" && mvn clean -U install
COPY --chown=$USERNAME:$USERNAME ./application/target/$APP.jar $JAR_PATH

USER $USERNAME:$USERNAME
EXPOSE $server.port
