package com.fuelgo.cloud.out;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class GasStationStompHandler extends StompSessionHandlerAdapter {
    private final int pumpId;
    private StompSession.Subscription subscription;
    private final BlockingQueue<PumpMessage> messageQueue;


    public GasStationStompHandler(int pumpId) {
        this.pumpId = pumpId;
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("Connected to listen to pump#{}", pumpId);
        subscription = session.subscribe("/topic/pump/" + pumpId, this);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return PumpMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Client received: payload {}, headers {}", payload, headers);
        PumpMessage pumpMessage = (PumpMessage) payload;
        if (pumpMessage.isTerminate()) {
            log.info("Process is over, unsubscribed");
            subscription.unsubscribe();
        }
        try {
            messageQueue.put(pumpMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        super.handleException(session, command, headers, payload, exception);
        log.error("Stomp communication error ", exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        super.handleTransportError(session, exception);
        log.error("Stomp transport error ", exception);
    }

    public PumpMessage nextMessage() throws InterruptedException {
            return messageQueue.take();
    }
}
