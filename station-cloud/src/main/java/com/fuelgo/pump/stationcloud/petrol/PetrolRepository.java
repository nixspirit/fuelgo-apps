package com.fuelgo.pump.stationcloud.petrol;

import com.fuelgo.pump.stationcloud.pump.PumpEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetrolRepository  extends CrudRepository<PetrolEntity, String> {
}
