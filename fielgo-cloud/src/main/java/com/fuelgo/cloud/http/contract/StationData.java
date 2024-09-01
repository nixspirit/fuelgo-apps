package com.fuelgo.cloud.http.contract;

import java.util.List;

public record StationData(int objectID, String title, List<PetrolData> fuelTypes, double lat, double lon) {
}
