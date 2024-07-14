package com.fuelgo.pump.stationcloud.pump;

import com.fuelgo.pump.stationcloud.http.PumpData;
import com.fuelgo.pump.stationcloud.petrol.PetrolRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class PumpService {

    private final PumpRepository pumpRepository;
    private final PetrolRepository petrolRepository;

    public void updatePumpData(PumpData pumpData) {
        log.info("Updating pump data {}", pumpData);
    }
}
