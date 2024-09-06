package com.fuelgo.pump.stationcloud.petrol;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode
public class PetrolEntity implements Comparable<PetrolEntity> {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;

    @Override
    public int compareTo(PetrolEntity o) {
        return Integer.compare(id, o.id);
    }
}
