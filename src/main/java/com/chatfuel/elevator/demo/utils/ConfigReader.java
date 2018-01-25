package com.chatfuel.elevator.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    private static final String BUILDING_FLOORS_COUNT = "building.floors";
    private static final String BUILDING_FLOOR_HEIGHT = "building.floor.height";

    private static final String ELEVATOR_CAPACITY = "elevator.capacity";
    private static final String ELEVATOR_SPEED = "elevator.speed";
    private static final String ELEVATOR_DOORS_DELAY = "elevator.doors.delay";

    private final Properties properties;

    public ConfigReader() {
        this("application.properties");
    }

    public ConfigReader(String fileName) {

        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(fileName));
            validateConfig();
        } catch (IOException ex) {
            logger.error("ConfigReader error", ex);
        }
    }

    // GETTERS
    public int getFloorsCount() {
        return Integer.parseInt(properties.getProperty(BUILDING_FLOORS_COUNT));
    }

    public int getFloorHeight() {
        return Integer.parseInt(properties.getProperty(BUILDING_FLOOR_HEIGHT));
    }

    public double getElevatorSpeed() {
        return Integer.parseInt(properties.getProperty(ELEVATOR_SPEED));
    }

    public int getDoorsDelay() {
        return Integer.parseInt(properties.getProperty(ELEVATOR_DOORS_DELAY));
    }

    public int getElevatorCapacity() {
        return Integer.parseInt(properties.getProperty(ELEVATOR_CAPACITY));
    }

    // PRIVATE MEMBERS
    private void validateConfig() {

        if (Objects.isNull(properties.getProperty(BUILDING_FLOORS_COUNT))) {
            String message = String.format("Argument: %s must not be null!", "building.floors");
            logger.error(message);
            throw new IllegalStateException(message);
        }

        if (Objects.isNull(properties.getProperty(BUILDING_FLOOR_HEIGHT))) {
            String message = String.format("Argument: %s must not be null!", "building.stage.height");
            logger.error(message);
            throw new IllegalStateException(message);
        }

        if (Objects.isNull(properties.getProperty(ELEVATOR_SPEED))) {
            String message = String.format("Argument: %s must not be null!", "elevator.speed");
            logger.error(message);
            throw new IllegalStateException(message);
        }

        if (Objects.isNull(properties.getProperty(ELEVATOR_DOORS_DELAY))) {
            String message = String.format("Argument: %s must not be null!", "elevator.doors.delay");
            logger.error(message);
            throw new IllegalStateException(message);
        }
        
        if (Objects.isNull(properties.getProperty(ELEVATOR_CAPACITY))) {
            String message = String.format("Argument: %s must not be null!", "elevator.capacity");
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }
}
