package com.fuelgo.cloud.out;


//STOMP message
public record PumpMessage(int id, float litres, int grade, String gradeName, int event, String eventName) {

    public boolean isTerminate() {
        return event() == 3;// || event() == -1;
    }
}
