package com.fuelgo.cloud.http.contract;

import java.util.List;

public record PumpData(int objectID, List<String> petrols) implements Comparable<PumpData> {

    @Override
    public int compareTo(PumpData o) {
        return Integer.compare(objectID, o.objectID);
    }

}
