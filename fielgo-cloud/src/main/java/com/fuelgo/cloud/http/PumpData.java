package com.fuelgo.cloud.http;

import lombok.Data;

import java.util.List;

public record PumpData(int id, List<String> petrols) {

}
