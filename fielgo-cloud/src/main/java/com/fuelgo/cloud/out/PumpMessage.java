package com.fuelgo.cloud.out;

//STOMP message
public record PumpMessage(int id, float litres, int grade, String gradeName, int event, String eventName) {

}
