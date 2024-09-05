package com.fuelgo.cloud.service;

import com.fuelgo.cloud.http.contract.PetrolData;
import com.fuelgo.cloud.http.contract.PumpData;
import com.fuelgo.cloud.http.contract.StationData;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class MapService {

    public Flux<StationData> getStations(double lat, double lon) {
        return Flux.just(
                new StationData(
                        10,
                        "Shell",
                        List.of(new PetrolData(5, "E5"), new PetrolData(10, "E10"), new PetrolData(100, "Diesel")),
                        52.429691722292816,
                        4.843483005707954
                ),

                new StationData(
                        12,
                        "Total",
                        List.of(new PetrolData(5, "E5"), new PetrolData(10, "E10"), new PetrolData(100, "Diesel"), new PetrolData(200, "AdBlue")),
                        52.43125594769359,
                        4.853267499617956
                )
        );
    }

}
