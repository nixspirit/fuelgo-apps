package com.fuelgo.cloud.pump;

import com.fuelgo.cloud.http.StationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PumpServiceTest {

    @Autowired
    private PumpService pumpService;

    @Test
    public void test_updatePump_updated() {

        StationData stationData = pumpService.getStation(1).block();

    }

}
