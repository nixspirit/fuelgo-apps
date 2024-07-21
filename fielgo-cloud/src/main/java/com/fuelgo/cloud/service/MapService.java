package com.fuelgo.cloud.service;

import com.fuelgo.cloud.http.contract.StationData;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class MapService {

    public Flux<StationData> getStations(int lat, String lon) {
        return Flux.just(new StationData(1, "Test", List.of(), 0, 0));
    }

}
