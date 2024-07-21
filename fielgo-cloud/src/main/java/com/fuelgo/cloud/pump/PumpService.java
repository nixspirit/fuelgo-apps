package com.fuelgo.cloud.pump;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuelgo.cloud.http.contract.PumpData;
import com.fuelgo.cloud.http.contract.PumpState;
import com.fuelgo.cloud.http.contract.StationData;
import com.fuelgo.cloud.out.GasStationService;
import com.fuelgo.cloud.out.GasStationStompHandler;
import com.fuelgo.cloud.out.PumpMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
@Service
public class PumpService {

    private final GasStationService gasStationService;
    private final ApplicationContext applicationContext;

    public Flux<StationData> getStations(int lat, String lon) {
        return Flux.just(new StationData(1, "Test", List.of(), 0, 0));
    }

    public Mono<StationData> getStation(int stationId) {
        return Mono.just(new StationData(1, "Test", List.of(), 0, 0));
    }

    public Flux<PumpData> getStationPumps(int stationId) {
        return gasStationService.getPumps();
    }

    public Mono<PumpData> getStationPumpPetrol(int stationId, int pumpId) {
        return gasStationService.getPumpById(pumpId);
    }

    public Flux<PumpState> fueling(int stationId, int pumpId, int petrolId) {
        GasStationStompHandler sessionHandler = new GasStationStompHandler(pumpId);
        WebSocketStompClient stationStompClient = getStationStompClient(sessionHandler);
        return Flux.<PumpMessage>create(fluxSink -> {
                    while (true) {
                        try {
                            PumpMessage t = sessionHandler.nextMessage();
                            fluxSink.next(t);
                            if (t.isTerminate())
                                break;

                        } catch (InterruptedException e) {
                            fluxSink.error(new RuntimeException(e));
                        }
                    }
                }).takeUntil(PumpMessage::isTerminate)
                .map(pm -> new PumpState(pm.id(), pm.litres()))
                .doFinally(d -> stationStompClient.stop());
    }

    private WebSocketStompClient getStationStompClient(StompSessionHandler sessionHandler) {
        return (WebSocketStompClient) applicationContext.getBean("stationStompClient", sessionHandler);
    }
}
