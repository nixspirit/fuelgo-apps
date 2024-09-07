package com.fuelgo.cloud.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuelgo.cloud.out.GasStationService;
import com.fuelgo.cloud.out.PumpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Slf4j
@Configuration
public class ExternalServiceConfig {

    @Autowired
    ObjectMapper jsonMapper;

    @Value("${gas.station.http.url}")
    String gasStationUrl;

    @Value("${gas.station.ws.url}")
    String gasStationWsUrl;

    @Bean
    public GasStationService gasStationService() {
        log.info(">> gasStationService created using {}", gasStationUrl);
        WebClient webClient = WebClient.builder().baseUrl(gasStationUrl).build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GasStationService.class);
    }

    @Bean(name = "stationStompClient")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public WebSocketStompClient stationStompClient(StompSessionHandler sessionHandler) {
        log.info(">> stationStompClient created using {}", gasStationWsUrl);
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new PumpMessageConverter(jsonMapper));
        stompClient.connectAsync(gasStationWsUrl, sessionHandler);
        return stompClient;
    }
}
