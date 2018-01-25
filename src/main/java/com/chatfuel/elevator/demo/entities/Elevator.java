package com.chatfuel.elevator.demo.entities;

import com.chatfuel.elevator.demo.utils.MiscUtils;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.IntStream;


public class Elevator {

    private final int capacity;
    private final double speed;
    private final int doorsDelay;
    private final Building building;

    private static final Set<Integer> floorsQueue = new TreeSet<>();
    private int currentFloor = 1;
    private boolean isDoorOpened = false;
    private MovementDirection direction = MovementDirection.IDLE;

    private Thread task;

    public Elevator(Building building, double speed, int doorsDelay, int capacity) {
        this.speed = speed;
        this.capacity = capacity;
        this.building = building;
        this.doorsDelay = doorsDelay;
    }

    private enum MinMax {MIN, MAX}

    // GETTERS
    public double getSpeed() {
        return speed;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getDoorsDelay() {
        return doorsDelay;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
        onPassingFloor(currentFloor);
    }

    public boolean isDoorOpened() {
        return isDoorOpened;
    }

    public void setDoorOpened(boolean doorOpened) {

        isDoorOpened = doorOpened;
        if (isDoorOpened) {
            onDoorOpened(currentFloor);
        } else {
            onDoorClosed(currentFloor);
        }
    }

    // METHODS
    private void runTask() {

        LocalTime timer = LocalTime.now();
        int destination = -1;

        for (; ; ) {
            // если что-то есть в очереди задач попробуем проверить надо ли менять текущий целевой этаж
            if (!floorsQueue.isEmpty()) {

                // проверка есть ли на пути следования к вызываемому этажу дополнительные этажи для остановки
                if (direction == MovementDirection.UP) {
                    destination = getDestination(currentFloor, building.getFloors(), MinMax.MIN);
                } else if (direction == MovementDirection.DOWN) {
                    destination = getDestination(1, building.getFloors(), MinMax.MAX);
                }

                // если ничего не было - ищем ближайший этаж к currentFloor
                if (destination == -1) {
                    direction = MovementDirection.IDLE;

                    int dest1 = getDestination(1, currentFloor, MinMax.MAX);
                    int dest2 = getDestination(currentFloor, building.getFloors(), MinMax.MIN);
                    destination = Math.max(dest1, dest2);
                }
            }

            // если лифт остановлен - определяемся куда ехать
            if (direction == MovementDirection.IDLE) {
                int idleDestination = (destination == -1) ? currentFloor : destination;
                if (idleDestination != currentFloor) {
                    direction = destination > currentFloor ? MovementDirection.UP : MovementDirection.DOWN;
                    timer = LocalTime.now();
                    // поехали....
                }
            } else if (destination != -1 && getBetweenTime(timer) >= getaFloorPassingTime()) {// нет - продолжаем ехать
                setCurrentFloor(direction == MovementDirection.UP ? currentFloor + 1 : currentFloor - 1);
                timer = LocalTime.now();
            }

            // если доехали до этажа, открываем дверь, убираем этаж из списка задач
            if (destination == currentFloor) {
                destination = -1;
                synchronized (floorsQueue) {
                    floorsQueue.remove(currentFloor);
                    if (floorsQueue.isEmpty()) {
                        direction = MovementDirection.IDLE;
                    }
                }
                setDoorOpened(true);
                timer = LocalTime.now();
            }

            // как только закончится задержка на открытие двери - закрываем её
            if (isDoorOpened() && getBetweenTime(timer) >= doorsDelay) {
                setDoorOpened(false);
            }

            // если лифт стоит с закрытой дверью и задач больше нет - завершаем цикл
            if (direction == MovementDirection.IDLE && !isDoorOpened() && floorsQueue.isEmpty()) {
                break;
            }
        }
    }

    private double getaFloorPassingTime() {
        return building.getFloorHeight() / getSpeed();
    }

    private long getBetweenTime(LocalTime timer) {
        return ChronoUnit.SECONDS.between(timer, LocalTime.now());
    }

    private int getDestination(int from, int to, MinMax val) {
        synchronized (floorsQueue) {
            IntStream intStream = floorsQueue
                    .stream()
                    .filter(x -> x > from && x < to)
                    .mapToInt(x -> x);

            return val.equals(MinMax.MIN) ? intStream.min().orElse(-1) : intStream.max().orElse(-1);
        }
    }

    public void moveTo(int floor) {
        if (floor > building.getFloors() || floor < 1) {
            throw new IllegalArgumentException("Building has only " + building.getFloors() + " floors");
        }
        // расшариваемый ресурс между запросами пользователя - очередь задач
        synchronized (floorsQueue) {
            floorsQueue.add(floor);
        }

        if (Objects.isNull(task) || !task.isAlive()) {

            task = new Thread(this::runTask);
            task.setDaemon(true);
            task.start();
        }
    }

    // events
    private static final Consumer<Integer> doorsOpenedEvent =
            x -> System.out.println("### Door's opened on the " + MiscUtils.getOrdinalNumberEnding(x) + " floor");
    private static final Consumer<Integer> doorsClosedEvent =
            x -> System.out.println("### Door's closed on the " + MiscUtils.getOrdinalNumberEnding(x) + " floor");
    private static final Consumer<Integer> passFloorEvent =
            x -> System.out.println("### Elevator's passed through the " + MiscUtils.getOrdinalNumberEnding(x) + " floor");

    private void onDoorOpened(int floor) {
        doorsOpenedEvent.accept(floor);
    }

    private void onDoorClosed(int floor) {
        doorsClosedEvent.accept(floor);
    }

    private void onPassingFloor(int floor) {
        passFloorEvent.accept(floor);
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "speed=" + speed +
                ", capacity=" + capacity +
                ", doorsDelay=" + doorsDelay +
                '}';
    }
}
