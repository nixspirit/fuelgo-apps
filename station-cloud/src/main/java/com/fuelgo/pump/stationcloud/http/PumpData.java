package com.fuelgo.pump.stationcloud.http;

import lombok.Data;

import java.util.List;

@Data
public class PumpData {

    private final int id;
    private final List<String> petrols;
}
