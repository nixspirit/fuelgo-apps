package com.fuelgo.cloud.http.contract;

import java.util.List;

public record StationData(int id, String name, List<PumpData> pumps, double lat, double lon) {
}
