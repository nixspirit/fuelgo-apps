package com.fuelgo.cloud.pump;

import com.fuelgo.cloud.helper.Utils;
import com.fuelgo.cloud.http.PumpData;
import com.fuelgo.cloud.http.StationData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PumpServiceTest {

    @Autowired
    private PumpService pumpService;

    @Test
    public void test_getAllStations_ok() {

    }

    @Test
    public void test_getStationByCoordinates_ok() {

    }

    @Test
    public void test_getStationById_ok() {

        StationData stationData = pumpService.getStation(1).block();

    }

    @Test
    public void test_getStationPumps_ok() {
        List<PumpData> pumpDataList = Utils.toList(pumpService.getStationPumps(1).toIterable());
        System.out.println(">>>>>>>>> PUMPS " + pumpDataList);
    }

    @Test
    public void test_getPetrolList_ok() {
        PumpData pumpData = pumpService.getStationPumpPetrol(1, 2).block();
        System.out.println(">>>>>>>>> PUMP " + pumpData);
    }

}
