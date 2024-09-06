package com.fuelgo.cloud.http;

import com.fuelgo.cloud.http.contract.StationData;
import com.fuelgo.cloud.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@RestController
public class MapController {

    private final MapService mapService;

    @Operation(summary = "Get a list of gas stations within a 5km radius from the current location.")
    @GetMapping(value = "/map/station", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<StationData> getStations(@RequestParam("lat") double lat, @RequestParam("lon") double lon) {
        log.info("MapController#getStations lat={}, lon={}", lat, lon);
        return mapService.getStations(lat, lon);
    }

    @Operation(summary = "Get a gas stations by its ID.")
    @GetMapping(value = "/map/station/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<StationData> getStationById(@PathVariable("id") int gasStationId) {
        log.info("MapController#getStationById {}", gasStationId);
        return mapService.getStationById(gasStationId);
    }
}
