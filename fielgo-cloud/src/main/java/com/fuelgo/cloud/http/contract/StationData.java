package com.fuelgo.cloud.http.contract;

import java.util.List;

public record StationData(int objectID, String title, List<FuelTypeData> fuelTypes, double lat,
                          double lon) implements Comparable<StationData> {
    @Override
    public int compareTo(StationData o) {
        return Integer.compare(o.objectID, objectID);
    }
}
