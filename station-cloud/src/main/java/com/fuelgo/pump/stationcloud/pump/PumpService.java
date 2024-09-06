package com.fuelgo.pump.stationcloud.pump;

import com.fuelgo.pump.stationcloud.Utils;
import com.fuelgo.pump.stationcloud.http.FuelTypeData;
import com.fuelgo.pump.stationcloud.http.PumpData;
import com.fuelgo.pump.stationcloud.mappers.Mappers;
import com.fuelgo.pump.stationcloud.petrol.PetrolEntity;
import com.fuelgo.pump.stationcloud.petrol.PetrolRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
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
                .map(f -> {
                    PumpData data = Mappers.INSTANCE.toPumpData(f);
                    log.info(">>> PumpData: {}", data);
                    return data;
                }));
    }

    /**
     * @return a set of fuel types available.
     */
    public Set<FuelTypeData> getFuelTypes() {
        return Utils.toStream(petrolRepository.findAll())
                .map(f -> new FuelTypeData(f.getId(), f.getTitle()))
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

    public Flux<FuelTypeData> getPumpFuelTypes(int id) {
        return Flux.fromIterable(
                Utils.toStream(petrolRepository.findAll())
                        .map(t -> new FuelTypeData(t.getId(), t.getTitle()))
                        .collect(Collectors.toSet()));
    }

    /**
     * Saves or updates the pump with new data.
     *
     * @param pumpData the current state
     */
    public void saveOrUpdatePumpData(PumpData pumpData) {
        log.info("Updating pump data {}", pumpData);
        Set<PetrolEntity> petrolEntities = updatePetrol(pumpData);
        Optional<PumpEntity> pumpEntity = pumpRepository.findById(pumpData.objectID());
        if (pumpEntity.isEmpty()) {
            PumpEntity e = pumpRepository.save(new PumpEntity(pumpData.objectID(), Set.of()));
            e.setPetrolList(petrolEntities);
            pumpRepository.save(e);
        }
    }

    private Set<PetrolEntity> updatePetrol(PumpData pumpData) {
        Map<String, PetrolEntity> persisted = Utils.toStream(petrolRepository.findAll())
                .collect(Collectors.toMap(PetrolEntity::getTitle, v -> v));

        return pumpData.petrols().stream()
                .map(p -> {
                    if (persisted.containsKey(p))
                        return persisted.get(p);

                    PetrolEntity entity = new PetrolEntity();
                    entity.setTitle(p);
                    entity.setId(getIdByTitle(p));
                    try {
                        entity = petrolRepository.save(entity);
                    } catch (Throwable ex) {
                        log.error("Petrol exists {}, {}", p, ex.getMessage());
                    }
                    return entity;
                })
                .collect(Collectors.toSet());
    }

    private Integer getIdByTitle(String p) {
        return switch (p) {
            case "E5" -> 5;
            case "E10" -> 10;
            case "Diesel" -> 20;
            default -> null;
        };
    }
}
