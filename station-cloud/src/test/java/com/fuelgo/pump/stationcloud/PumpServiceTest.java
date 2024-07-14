package com.fuelgo.pump.stationcloud;

import com.fuelgo.pump.stationcloud.http.PumpData;
import com.fuelgo.pump.stationcloud.pump.PumpEntity;
import com.fuelgo.pump.stationcloud.pump.PumpRepository;
import com.fuelgo.pump.stationcloud.pump.PumpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PumpServiceTest {

    @Autowired
    private PumpService pumpService;
    @Autowired
    private PumpRepository pumpRepository;

    @Test
    public void testPumpService() {
        int pumpId = 10;
        Assertions.assertTrue(pumpRepository.findById(pumpId).isEmpty());

        PumpData data = new PumpData(pumpId, List.of("E5", "E10"));
        pumpService.updatePumpData(data);

        Optional<PumpEntity> entity = pumpRepository.findById(pumpId);
        Assertions.assertFalse(entity.isEmpty());
        PumpEntity pumpEntity = entity.get();

        Assertions.assertNotNull(pumpEntity);
        System.out.println(">>>>>> " + pumpEntity);

        Assertions.assertFalse(pumpEntity.getPetrolList().isEmpty());
        Assertions.assertEquals("E5", pumpEntity.getPetrolList().get(0).getId());
        Assertions.assertEquals("E10", pumpEntity.getPetrolList().get(1).getId());

    }
}
