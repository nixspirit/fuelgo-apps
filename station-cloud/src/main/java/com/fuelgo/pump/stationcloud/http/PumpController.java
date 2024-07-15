package com.fuelgo.pump.stationcloud.http;

import com.fuelgo.pump.stationcloud.pump.PumpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@RestController
public class PumpController {

    private final PumpService pumpService;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/pump/{id}")
    public Mono<ResponseEntity<?>> register(@RequestBody PumpData pumpData) {
        log.info("Pump {} registered with pumpData {}", pumpData.getId(), pumpData);
        pumpService.updatePumpData(pumpData);
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body("{\"status\":\"ok\"}"));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/pump/")
    public Flux<PumpData> getPumps() {
        return pumpService.getPumps();
    }

}
