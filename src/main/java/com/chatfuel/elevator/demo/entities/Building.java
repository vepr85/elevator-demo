package com.chatfuel.elevator.demo.entities;

import com.chatfuel.elevator.demo.utils.ConfigReader;

public class Building {

    private final int floors;
    private final int floorHeight;
    private final ConfigReader config = new ConfigReader();

    private final Elevator elevator;

    public Building() {

        this.floors = config.getFloorsCount();
        this.floorHeight = config.getFloorHeight();
        this.elevator = new Elevator(
                this,
                config.getElevatorSpeed(),
                config.getDoorsDelay(),
                config.getElevatorCapacity());
    }

    public int getFloors() {
        return floors;
    }

    public int getFloorHeight() {
        return floorHeight;
    }

    public Elevator getElevator() {
        return elevator;
    }

    @Override
    public String toString() {
        return "Building{" +
                "floors=" + floors +
                ", floorHeight=" + floorHeight +
                ", config=" + config +
                ", elevator=" + elevator +
                '}';
    }
}
