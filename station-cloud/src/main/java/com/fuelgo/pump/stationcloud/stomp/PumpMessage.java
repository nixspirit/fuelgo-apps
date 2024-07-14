package com.fuelgo.pump.stationcloud.stomp;

//STOMP message
public record PumpMessage(int id, float litres, int grade, String gradeName, int event, String eventName) {

}
