package com.fuelgo.pump.stationcloud.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class PumpMessageHandler {

    @MessageMapping("/pump/{id}/state")
    @SendTo(value = {"/topic/pump/{id}"})
    public PumpMessage pumpMessage(@DestinationVariable("id") int id, PumpMessage message) throws Exception {
        log.info("PumpMessage received: {}# {}", id, message);
        return message;
    }
}
