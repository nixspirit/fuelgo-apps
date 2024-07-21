package com.fuelgo.cloud.http;

import com.fuelgo.cloud.http.contract.StationData;
import com.fuelgo.cloud.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
public class MapController {

    private final MapService mapService;

    @Operation(summary = "Get a list of gas stations within a 5km radius from the current location.")
    @GetMapping("/station/{lat},{lon}")
    public Flux<StationData> getStations(@PathVariable int lat, @PathVariable String lon) {
        return mapService.getStations(lat, lon);
    }
}
