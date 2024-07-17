package com.fuelgo.cloud.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuelgo.cloud.out.GasStationService;
import com.fuelgo.cloud.out.GasStationStompHandler;
import com.fuelgo.cloud.out.PumpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
public class ExternalServiceConfig {

    String url = "ws://localhost:8081/state";

    @Bean
    public GasStationService gasStationService() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081/").build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GasStationService.class);
    }

    @Bean
    public WebSocketStompClient gasStationStompClient(ObjectMapper jsonMapper) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new PumpMessageConverter(jsonMapper));

        StompSessionHandler sessionHandler = new GasStationStompHandler();
        stompClient.connectAsync(url, sessionHandler);

        return stompClient;
    }
}
