# create the builder stage
# The builder stage extracts the folders that are needed later. Each of the COPY commands relates to the layers that we listed earlier.
FROM docker.novopayment.net/novo-docker-images-openjdk:11.0.10 as builder

# set working directory
WORKDIR /home/app/bin

# extract the compiled jar and rename it to app.jar
COPY ./target/*.jar app.jar

# We establish how the jar should start with the layertools mode:
# and additionally we extract the layers that make up the microservice
RUN java -Djarmode=layertools -jar app.jar extract

# the second working layer is created
FROM docker.novopayment.net/novo-docker-images-openjdk:11.0.10

# we proceed to create the microservice layer and establish the working directory
WORKDIR /home/app/bin

# the app group and the app user are created to minimize the permissions on the pod
RUN addgroup app && adduser \
                 --disabled-password \
                 --gecos "" \
                 --ingroup app \
                 --no-create-home \
                 app && \
                mkdir /logs

# the default output port of the pod is configured
EXPOSE 8000

# the variables of the apm server are configured
ARG JAR_FILE=target/*.jar
ARG SERVICE_NAME
ARG SERVER_URL
ARG SECRET_TOKEN
ARG ENVIRONMENT_APP

# environment variables are assigned for apm configuration and microservice time zone
ENV ENV_JAR_FILE=$JAR_FILE \
    ENV_SERVICE_NAME=$SERVICE_NAME \
    ENV_SERVER_URL=$SERVER_URL \
    ENV_SECRET_TOKEN=$SECRET_TOKEN \
    TZ="America/Bogota" \
    ELASTIC_APM_ENVIRONMENT=$ENVIRONMENT_APP

# the apm jar is copied, this step will be changed to an apm model of the pod and not the jar
COPY 'apm/elastic-apm-agent-1.19.0.jar' 'elastic-apm-agent-1.19.0.jar'

# the app.jar is copied from the builder layer
COPY --from=builder /home/app/bin/app.jar ./

# the app.jar is copied from the builder layer
COPY --from=builder /home/app/bin/dependencies ./
RUN true

# spring-boot-loader dependencies are copied from the builder layer
COPY --from=builder /home/app/bin/spring-boot-loader ./
RUN true

# snapshot-dependencies are copied from the builder layer
COPY --from=builder /home/app/bin/snapshot-dependencies ./
RUN true

# the application layer is copied from the builder layer
COPY --from=builder /home/app/bin/application ./

# write permissions are assigned on the / logs folder
RUN chown -R app:app /home/app && chown -R app:app /logs

# the parameters of the jvm and the default server port are configured
ENTRYPOINT java \
-Xms100m -Xmx400m -XX:MaxRAM=400m -XX:MaxMetaspaceSize=400m -XX:+CMSClassUnloadingEnabled -XX:+UseG1GC -XX:+OptimizeStringConcat \
-XX:+UseStringDeduplication -XX:+UseLWPSynchronization -Djavax.xml.accessExternalDTD=all -Dserver.port=8000 \
-javaagent:elastic-apm-agent-1.19.0.jar -Delastic.apm.service_name=$ENV_SERVICE_NAME -Delastic.apm.server_urls=$ENV_SERVER_URL \
-Delastic.apm.secret_token=$ENV_SECRET_TOKEN -Delastic.apm.application_packages=com.novo.microservices \
org.springframework.boot.loader.JarLauncher