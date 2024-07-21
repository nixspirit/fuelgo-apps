package com.fuelgo.cloud.out;

import com.fuelgo.cloud.http.contract.PumpData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GasStationService {

    @GetExchange("/pump/")
    Flux<PumpData> getPumps();


    @GetExchange("/pump/{id}")
    Mono<PumpData> getPumpById(@PathVariable int id);

}
