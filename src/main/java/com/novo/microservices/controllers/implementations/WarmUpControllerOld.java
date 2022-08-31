package com.novo.microservices.controllers.implementations;


import com.novo.microservices.dtos.WarmUpRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        path = "/warmup",
        consumes = APPLICATION_JSON_VALUE
)
public class WarmUpControllerOld {

    @PostMapping
    //public ResponseEntity<String> post(@RequestBody @Valid WarmUpRequestDto warmUpRequestDto) {
    public Mono<ResponseEntity<String>> post(@RequestBody WarmUpRequestDto warmUpRequestDto) {
        return Mono.just(ResponseEntity.ok(UUID.randomUUID().toString() + warmUpRequestDto));
    }
}