package com.fuelgo.cloud.pump;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuelgo.cloud.http.PetrolData;
import com.fuelgo.cloud.http.PumpData;
import com.fuelgo.cloud.http.PumpState;
import com.fuelgo.cloud.http.StationData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class PumpService {

    private final ObjectMapper jsonMapper;

    public Flux<StationData> getStations(int lat, String lon) {
        return Flux.just(new StationData(1, "Test", List.of(), 0, 0));
    }

    public Mono<StationData> getStation(int stationId) {
        return Mono.just(new StationData(1, "Test", List.of(), 0, 0));
    }

    public Flux<PumpData> getStationPumps(String stationId) {
        return Flux.fromIterable(List.of(new PumpData(1, List.of()), new PumpData(2, List.of())));
    }

    public Flux<PetrolData> getStationPumpPetrol(String stationId, String pumpId) {
        return Flux.fromIterable(List.of(new PetrolData("E5"), new PetrolData("E10")));
    }

    public Flux<String> fueling(String stationId, String pumpId, String petrolId) {
        Random rd = new Random();

        return Flux.interval(Duration.ofSeconds(1))
                .handle((sequence, sink) -> {
                    PumpState state = new PumpState(1, rd.nextFloat(), 10f);
                    try {
                        sink.next(jsonMapper.writeValueAsString(state));
                    } catch (JsonProcessingException e) {
                        sink.error(new RuntimeException(e));
                    }
                });
    }


}
