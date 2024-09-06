package com.fuelgo.cloud.service;

import com.fuelgo.cloud.helper.Utils;
import com.fuelgo.cloud.http.contract.FuelTypeData;
import com.fuelgo.cloud.http.contract.PumpData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PumpServiceTest {

    @Autowired
    private PumpService pumpService;

    @Test
    public void test_getStationPumps_ok() {
        List<PumpData> pumpDataList = Utils.toList(pumpService.getStationPumps(12).toIterable());
        Assertions.assertNotNull(pumpDataList);
        Assertions.assertEquals(pumpDataList.size(), 0);
    }

    @Test
    public void test_getPetrolList_notFound() {
        Assertions.assertThrows(Throwable.class, () -> {
            pumpService.getStationPumpPetrol(1, 2).block();
        });
    }

    @Test
    public void test_getStationPumpFuelTypes_ok() {
        List<FuelTypeData> fuelTypeData = Utils.toList(pumpService.getStationPumpFuelType(12, 10).toIterable());
        Assertions.assertNotNull(fuelTypeData);
        Assertions.assertEquals(fuelTypeData.size(), 0);
    }

}
