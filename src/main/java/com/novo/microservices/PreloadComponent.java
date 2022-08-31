package com.novo.microservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Component
public class PreloadComponent {

    @Autowired
    private Environment environment;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ObjectMapper objectMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent event) {

        // instead, a full request including validation is required
        sendWarmUpRestRequest();
    }

    private void sendWarmUpRestRequest() {

        try {

            final String serverPort = environment.getProperty("local.server.port");
            final String baseUrl = "http://localhost:" + serverPort;
            final String warmUpEndpoint = baseUrl + "/switching/v1.0/transactions/orchestration/transactions";

            log.info("sending rest request to force initialization of Jackson...");

            String command = "curl --location --request POST 'http://localhost:8000/switching/v1.0/transactions/orchestration/transactions' \\\n" +
                "--header 'Content-Type: application/json' \\\n" +
                "--data-raw '{\n" +
                "    \"paymentHeader\": {\n" +
                "        \"messageType\": \"P\"        \n" +
                "    },\n" +
                "    \"transaction\": {       \n" +
                "        \"de126\": \"601548\"\n" +
                "    }\n" +
                "}'";

            Process process = Runtime.getRuntime().exec(command);
            process.getInputStream();
            process.destroy();
            /*
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.directory(new File("/home/"));
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            int exitCode = process.exitValue();
            process.destroy();

             */

            /*
            final String response = webClientBuilder.build().post()
                .uri(warmUpEndpoint)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(OrchestrationTransactionRequest.builder().build()), OrchestrationTransactionRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(12))
                .block();

             */

            /*
            final String response = webClientBuilder.build().get()
                .uri(warmUpEndpoint)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .block();

             */

            //log.info("...done, response received: " + response);
            log.info("...done, response received:");
        } catch (Exception e) {
            log.error("error in process sendWarmUpRestRequest, error {}" + e.getMessage());
        }
    }
}
