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

    @Mapping(source = "petrolId", target = "id")
    PetrolEntity toEntity(String petrolId);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "petrolList", target = "petrols", qualifiedByName = "toPetrolId")
    PumpData toPumpData(PumpEntity pumpEntity);

    @Named("toPetrolId")
    static String toPetrolId(PetrolEntity petrolEntity) {
        return petrolEntity.getId();
    }
}
