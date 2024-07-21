package com.fuelgo.pump.stationcloud;

import com.fuelgo.pump.stationcloud.http.PumpData;
import com.fuelgo.pump.stationcloud.petrol.PetrolEntity;
import com.fuelgo.pump.stationcloud.petrol.PetrolRepository;
import com.fuelgo.pump.stationcloud.pump.PumpEntity;
import com.fuelgo.pump.stationcloud.pump.PumpRepository;
import com.fuelgo.pump.stationcloud.pump.PumpService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PumpServiceTest {

    @Autowired
    private PumpService pumpService;
    @Autowired
    private PumpRepository pumpRepository;
    @Autowired
    private PetrolRepository petrolRepository;

    @Test
    public void test_updatePump_updated() {
        int pumpId = 10;
        Assertions.assertEquals(0, petrolRepository.count());
        Assertions.assertEquals(0, pumpRepository.count());

        PumpData data = new PumpData(pumpId, List.of("E5", "E10"));
        pumpService.saveOrUpdatePumpData(data);

        List<PetrolEntity> petrolEntities = Utils.toList(petrolRepository.findAll());
        Assertions.assertNotNull(petrolEntities);
        Assertions.assertEquals(2, petrolEntities.size());
        Assertions.assertEquals("E10", petrolEntities.get(0).getId());
        Assertions.assertEquals("E5", petrolEntities.get(1).getId());

        Optional<PumpEntity> entity = pumpRepository.findById(pumpId);
        Assertions.assertFalse(entity.isEmpty());
        PumpEntity pumpEntity = entity.get();

        Assertions.assertNotNull(pumpEntity);

        List<PetrolEntity> petrolList = pumpEntity.getPetrolList().stream().sorted().toList();
        Assertions.assertFalse(petrolList.isEmpty());
        Assertions.assertEquals("E10", petrolList.get(0).getId());
        Assertions.assertEquals("E5", petrolList.get(1).getId());
    }

    @Test
    public void test_getPumps_returnedList() {
        Assertions.assertEquals(0, petrolRepository.count());
        Assertions.assertEquals(0, pumpRepository.count());

        Set<PetrolEntity> petrolEntitySet = Utils.toSet(petrolRepository.saveAll(List.of(new PetrolEntity("E5"), new PetrolEntity("E10"))));
        pumpRepository.save(new PumpEntity(1, petrolEntitySet));
        pumpRepository.save(new PumpEntity(2, petrolEntitySet));

        List<PumpData> pumpDataList = Objects.requireNonNull(pumpService.getPumps().collectList().block()).stream().sorted().toList();
        Assertions.assertFalse(pumpDataList.isEmpty());
        Assertions.assertEquals(1, pumpDataList.get(0).id());
        Assertions.assertEquals(List.of("E10", "E5"), pumpDataList.get(0).petrols().stream().sorted().toList());

        Assertions.assertEquals(2, pumpDataList.get(1).id());
        Assertions.assertEquals(List.of("E10", "E5"), pumpDataList.get(0).petrols().stream().sorted().toList());
    }
}
