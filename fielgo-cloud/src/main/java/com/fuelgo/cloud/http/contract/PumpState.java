package com.fuelgo.cloud.http.contract;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PumpState {

    private long id;
    private float diff;
    private float total;
    private int event;
}
