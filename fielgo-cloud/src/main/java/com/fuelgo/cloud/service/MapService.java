package com.fuelgo.cloud.service;

import com.fuelgo.cloud.http.contract.FuelTypeData;
import com.fuelgo.cloud.http.contract.StationData;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MapService {

    public Flux<StationData> getStations(double lat, double lon) {
        return Flux.just(
                new StationData(
                        10,
                        "Shell",
                        List.of(new FuelTypeData(5, "E5"), new FuelTypeData(10, "E10"), new FuelTypeData(100, "Diesel")),
                        52.429691722292816,
                        4.843483005707954
                ),

                new StationData(
                        12,
                        "Total",
                        List.of(new FuelTypeData(5, "E5"), new FuelTypeData(10, "E10"), new FuelTypeData(100, "Diesel"), new FuelTypeData(200, "AdBlue")),
                        52.43125594769359,
                        4.853267499617956
                )
        );
    }

    public Mono<StationData> getStationById(int gasStationId) {
        return Mono.just(new StationData(
                gasStationId,
                "Shell",
                List.of(new FuelTypeData(5, "E5"), new FuelTypeData(10, "E10"), new FuelTypeData(100, "Diesel")),
                52.429691722292816,
                4.843483005707954
        ));
    }
}
