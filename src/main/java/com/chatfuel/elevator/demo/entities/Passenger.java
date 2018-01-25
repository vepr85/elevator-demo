package com.chatfuel.elevator.demo.entities;


public class Passenger {

    private final int startFloor;
    private final int targetFloor;

    public Passenger(int startFloor, int targetFloor) {
        this.startFloor = startFloor;
        this.targetFloor = targetFloor;
    }

    public int getStartFloor() {
        return startFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }
}
