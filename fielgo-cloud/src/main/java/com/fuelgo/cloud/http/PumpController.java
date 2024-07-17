package com.fuelgo.cloud.http;

import com.fuelgo.cloud.pump.PumpService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class PumpController {

    private final PumpService pumpService;

    @GetMapping("/station/{lat},{lon}")
    public Flux<StationData> getStations(@PathVariable int lat, @PathVariable String lon) {
        return pumpService.getStations(lat, lon);
    }

    @GetMapping("/station/{stationId}")
    public Mono<StationData> getStation(@PathVariable int stationId) {
        return pumpService.getStation(stationId);
    }

    @GetMapping("/station/{stationId}/pumps/")
    public Flux<PumpData> getStationPumps(@PathVariable int stationId) {
        return pumpService.getStationPumps(stationId);
    }

    @GetMapping("/station/{stationId}/pumps/{pumpId}")
    public Mono<PumpData> getStationPump(@PathVariable int stationId, @PathVariable int pumpId) {
        return pumpService.getStationPumpPetrol(stationId, pumpId);
    }

    @GetMapping(path = "/station/{stationId}/pumps/{pumpId}/petrol/{petrolId}/fueling", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fuelingProgress(@PathVariable String stationId, @PathVariable String pumpId, @PathVariable String petrolId) {
        return pumpService.fueling(stationId, pumpId, petrolId);
    }

}
