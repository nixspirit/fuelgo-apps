package com.fuelgo.pump.stationcloud.pump;

import com.fuelgo.pump.stationcloud.petrol.PetrolEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Getter
@Entity
public class PumpEntity {

    @Id
    private int id;

    @ManyToMany
    private List<PetrolEntity> petrolList;

}
