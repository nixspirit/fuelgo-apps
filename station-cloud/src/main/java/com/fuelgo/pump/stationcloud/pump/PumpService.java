package com.fuelgo.pump.stationcloud.pump;

import com.fuelgo.pump.stationcloud.Utils;
import com.fuelgo.pump.stationcloud.http.PumpData;
import com.fuelgo.pump.stationcloud.mappers.Mappers;
import com.fuelgo.pump.stationcloud.petrol.PetrolEntity;
import com.fuelgo.pump.stationcloud.petrol.PetrolRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class PumpService {

    private final PumpRepository pumpRepository;
    private final PetrolRepository petrolRepository;

    /**
     * @return list of pumps available along with their current state.
     */
    public Flux<PumpData> getPumps() {
        return Flux.fromStream(Utils.toStream(pumpRepository.findAll())
                .map(Mappers.INSTANCE::toPumpData));
    }

    /**
     * @return a set of fuel types available.
     */
    public Set<String> getFuelTypes() {
        return Utils.toStream(petrolRepository.findAll())
                .map(PetrolEntity::getId)
                .collect(Collectors.toSet());
    }

    /**
     * @param id is pump identifier
     * @return information on a particular pump
     */
    public Mono<PumpData> getPump(int id) {
        return Mono.just(pumpRepository.findById(id)
                .map(Mappers.INSTANCE::toPumpData)
                .orElseThrow());
    }

    /**
     * Saves or updates the pump with new data.
     *
     * @param pumpData the current state
     */
    public void saveOrUpdatePumpData(PumpData pumpData) {
        log.info("Updating pump data {}", pumpData);
        Set<PetrolEntity> petrolEntities = updatePetrol(pumpData);
        Optional<PumpEntity> pumpEntity = pumpRepository.findById(pumpData.id());
        if (pumpEntity.isPresent()) {
            PumpEntity pumpEntity1 = pumpEntity.get();
            pumpEntity1.setPetrolList(petrolEntities);
            pumpRepository.save(pumpEntity1);
        } else {
            pumpRepository.save(new PumpEntity(pumpData.id(), petrolEntities));
        }
    }

    private Set<PetrolEntity> updatePetrol(PumpData pumpData) {
        Set<PetrolEntity> petrolEntitySet = pumpData.petrols().stream()
                .map(Mappers.INSTANCE::toEntity).collect(Collectors.toSet());

        return petrolEntitySet.stream().map(p -> {
                    try {
                        p = petrolRepository.save(p);
                    } catch (Throwable ex) {
                        log.error("Petrol exists {}, {}", p, ex.getMessage());
                    }

                    return p;
                }
        ).collect(Collectors.toSet());
    }

}
