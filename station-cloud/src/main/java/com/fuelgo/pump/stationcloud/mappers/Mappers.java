package com.fuelgo.pump.stationcloud.mappers;

import com.fuelgo.pump.stationcloud.http.PumpData;
import com.fuelgo.pump.stationcloud.petrol.PetrolEntity;
import com.fuelgo.pump.stationcloud.pump.PumpEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface Mappers {

    Mappers INSTANCE = org.mapstruct.factory.Mappers.getMapper(Mappers.class);

    @Mapping(source = "id", target = "objectID")
    @Mapping(source = "petrolList", target = "petrols", qualifiedByName = "toFuelId")
    PumpData toPumpData(PumpEntity pumpEntity);

    @Named("toFuelId")
    static String toFuelId(PetrolEntity petrolEntity) {
        return petrolEntity.getTitle();
    }
}
