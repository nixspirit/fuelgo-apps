package com.fuelgo.pump.stationcloud.petrol;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetrolRepository extends CrudRepository<PetrolEntity, String> {
}
