package com.fuelgo.pump.stationcloud.http;

import com.fuelgo.pump.stationcloud.pump.PumpService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@AllArgsConstructor
@Slf4j
@RestController
public class PumpController {

    private final PumpService pumpService;

    @Operation(summary = "Saves the current state of a pump.")
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/pump/{id}")
    public Mono<ResponseEntity<?>> register(@RequestBody PumpData pumpData) {
        log.info("Pump {} registered with pumpData {}", pumpData.id(), pumpData);
        pumpService.saveOrUpdatePumpData(pumpData);
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body("{\"status\":\"ok\"}"));
    }

    @Operation(summary = "Returns a list of all registered pumps.")
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/pump/")
    public Flux<PumpData> getPumps() {
        return pumpService.getPumps();
    }

    @Operation(summary = "Returns a state of a pump with the given id.")
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/pump/{id}")
    public Mono<PumpData> getPumpById(@PathVariable("id") int id) {
        return pumpService.getPump(id);
    }

    @Operation(summary = "Returns a list of all registered fuel types (E5, Diesel etc).")
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/fuel/")
    public Set<String> getFuelType() {
        return pumpService.getFuelTypes();
    }

}
