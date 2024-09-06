package com.fuelgo.cloud.service;

import com.fuelgo.cloud.http.contract.StationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GasStationServiceTest {

    @Autowired
    private MapService mapService;

    @Test
    public void test_getStationByCoordinates_ok() {
        List<StationData> items = mapService.getStations(52.4296577360724, 4.842836525941346)
                .toStream().sorted().toList();
        Assertions.assertFalse(items.isEmpty());
        Assertions.assertEquals(2, items.size());
        Assertions.assertNotNull(items.get(0));
        Assertions.assertNotNull(items.get(1));
        Assertions.assertEquals(12, items.get(0).objectID());
        Assertions.assertEquals(10, items.get(1).objectID());

    }

    @Test
    public void test_getStationById_ok() {
        StationData stationData = mapService.getStationById(12).block();
        Assertions.assertNotNull(stationData);
        Assertions.assertEquals(12, stationData.objectID());
        Assertions.assertEquals("Shell", stationData.title());
    }

}
