package com.fuelgo.pump.stationcloud.http;

import lombok.Data;

import java.util.List;

@Data
public class PumpData implements Comparable<PumpData> {

    private final int id;
    private final List<String> petrols;

    @Override
    public int compareTo(PumpData o) {
        return Integer.compare(id, o.id);
    }
}
