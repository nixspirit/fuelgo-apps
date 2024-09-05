package com.fuelgo.cloud.http;

import com.fuelgo.cloud.http.contract.PumpData;
import com.fuelgo.cloud.http.contract.PumpState;
import com.fuelgo.cloud.http.contract.StationData;
import com.fuelgo.cloud.service.PumpService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@RestController
public class PumpController {

    private final PumpService pumpService;

    @Operation(summary = "Get a gas station information.")
    @GetMapping("/station/{stationId}")
    public Mono<StationData> getStation(@PathVariable int stationId) {
        log.info("Get station {}", stationId);
        return pumpService.getStation(stationId);
    }

    @Operation(summary = "Get a list of pumps the gas station has.")
    @GetMapping("/station/{stationId}/pumps/")
    public Flux<PumpData> getStationPumps(@PathVariable int stationId) {
        log.info("Get station {} pumps", stationId);
        return pumpService.getStationPumps(stationId);
    }

    @Operation(summary = "Get the information on a particular pump.")
    @GetMapping("/station/{stationId}/pumps/{pumpId}")
    public Mono<PumpData> getStationPump(@PathVariable int stationId, @PathVariable int pumpId) {
        log.info("Get station {} pump {}", stationId, pumpId);
        return pumpService.getStationPumpPetrol(stationId, pumpId);
    }

    @Operation(summary = "Provides a real time update on the fueling process using SSE.")
    @GetMapping(path = "/station/{stationId}/pumps/{pumpId}/petrol/{petrolId}/fueling", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PumpState> fuelingProgress(@PathVariable int stationId, @PathVariable int pumpId, @PathVariable int petrolId) {
        log.info("Get station {} pump {} petrol {}", stationId, pumpId, petrolId);
        return pumpService.fueling(stationId, pumpId, petrolId);
    }
}
