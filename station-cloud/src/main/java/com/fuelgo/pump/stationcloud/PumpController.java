package com.fuelgo.pump.stationcloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class PumpController {

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(path = "/pump/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<?>> register(@PathVariable("id") Integer id) {
        log.info("Pump {} registered", id);
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body("{\"status\":\"ok\"}"));
    }

}
