package com.fuelgo.pump.stationcloud.pump;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PumpRepository extends CrudRepository<PumpEntity, Integer> {
}
