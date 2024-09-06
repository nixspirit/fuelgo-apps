package com.fuelgo.pump.stationcloud.pump;

import com.fuelgo.pump.stationcloud.petrol.PetrolEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class PumpEntity implements Comparable<PumpEntity> {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToMany
    private Set<PetrolEntity> petrolList;

    @Override
    public int compareTo(PumpEntity o) {
        return Integer.compare(id, o.id);
    }
}
