package com.fuelgo.pump.stationcloud;

import com.fuelgo.pump.stationcloud.petrol.PetrolEntity;
import com.fuelgo.pump.stationcloud.pump.PumpEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Utils {

    private Utils() {
    }

    public static <T> Set<T> toSet(Iterable<T> items) {
        return toStream(items).collect(Collectors.toSet());
    }

    public static <T> Stream<T> toStream(Iterable<T> all) {
        return StreamSupport.stream(all.spliterator(), false);

    }

    public static <T> List<T> toList(Iterable<T> items) {
        return toStream(items).collect(Collectors.toList());
    }
}
