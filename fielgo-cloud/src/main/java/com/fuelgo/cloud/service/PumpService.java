package com.fuelgo.cloud.service;

import com.fuelgo.cloud.http.contract.FuelTypeData;
import com.fuelgo.cloud.http.contract.PumpData;
import com.fuelgo.cloud.http.contract.PumpState;
import com.fuelgo.cloud.out.GasStationService;
import com.fuelgo.cloud.out.GasStationStompHandler;
import com.fuelgo.cloud.out.PumpMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class PumpService {

    private final GasStationService gasStationService;
    private final ApplicationContext applicationContext;

    public Flux<PumpData> getStationPumps(int stationId) {
        return gasStationService.getPumps();
    }

    public Mono<PumpData> getStationPumpPetrol(int stationId, int pumpId) {
        return gasStationService.getPumpById(pumpId);
    }

    public Flux<FuelTypeData> getStationPumpFuelType(int stationId, int pumpId) {
        return gasStationService.getPumpFuelTypes(pumpId);
    }

    /**
     * This method sends events back to the caller leveraging SSE and FluxSink emitter.
     * GasStationStompHandler subscribes for new events from an external service (Gas Station Backend).
     * Every time a new events published by the Gas Station Backend, this event gets queued.
     * The next message on the queue is sent to the Mobile App.
     *
     * @param stationId a gas station identifier
     * @param pumpId    a pump identifier
     * @param petrolId  a fuel identifier (e.g. E5, E10)
     * @return a stream of events
     */
    public Flux<PumpState> fueling(int stationId, int pumpId, int petrolId) {
        GasStationStompHandler sessionHandler = new GasStationStompHandler(pumpId);
        WebSocketStompClient stationStompClient = getStationStompClient(sessionHandler);
        return Flux.<PumpMessage>generate(fluxSink -> {
                    try {
                        PumpMessage t = sessionHandler.nextMessage();
                        log.info("> Message sent to the fuelgo-app back {}", t);
                        fluxSink.next(t);
                    } catch (InterruptedException e) {
                        fluxSink.error(new RuntimeException(e));
                    }
                }).takeUntil(PumpMessage::isTerminate)
                .map(pm -> new PumpState(pm.id(), pm.litres(), pm.litres(), pm.event()))
                .doFinally(d -> stationStompClient.stop());
    }

    /**
     * @param sessionHandler describes the message handling process.
     * @return a new instance of the stomp client.
     */
    private WebSocketStompClient getStationStompClient(StompSessionHandler sessionHandler) {
        return (WebSocketStompClient) applicationContext.getBean("stationStompClient", sessionHandler);
    }

}
