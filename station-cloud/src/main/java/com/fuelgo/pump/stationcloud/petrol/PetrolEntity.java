package com.fuelgo.pump.stationcloud.petrol;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode
public class PetrolEntity implements Comparable<PetrolEntity> {

    @Id
    private String id;

    @Override
    public int compareTo(PetrolEntity o) {
        return id.compareTo(o.id);
    }
}
