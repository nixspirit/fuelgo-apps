package com.fuelgo.cloud.http;

import com.fuelgo.cloud.http.contract.StationData;
import com.fuelgo.cloud.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
public class MapController {

    private final MapService mapService;

    @Operation(summary = "Get a list of gas stations within a 5km radius from the current location.")
    @GetMapping(value = "/map/station/{lat}/{lon}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<StationData> getStations(@PathVariable double lat, @PathVariable double lon) {
        return mapService.getStations(lat, lon);
    }
}
