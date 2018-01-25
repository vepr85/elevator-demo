package com.chatfuel.elevator.demo;

import com.chatfuel.elevator.demo.entities.Building;
import com.chatfuel.elevator.demo.entities.Elevator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * * Программа запускается из командной строки, в качестве параметров задается:
 * * - кол-во этажей в подъезде — N (от 5 до 20);
 * * - высота одного этажа;
 * * - скорость лифта при движении в метрах в секунду (ускорением пренебрегаем, считаем, что когда лифт едет — он сразу едет с определенной скоростью);
 * * - время между открытием и закрытием дверей.
 * *
 * * После запуска программа должна постоянно ожидать ввода от пользователя и выводить действия лифта в реальном времени. События, которые нужно выводить:
 * * - лифт проезжает некоторый этаж;
 * * - лифт открыл двери;
 * * - лифт закрыл двери.
 * *
 * * Возможный ввод пользователя:
 * * - вызов лифта на этаж из подъезда;
 * * - нажать на кнопку этажа внутри лифта.
 * *
 * * Считаем, что пользователь не может помешать лифту закрыть двери.
 * * Все данные, которых не хватает в задаче, можно выбрать на свое усмотрение.
 * * Решение можно прислать в виде ссылки на любой публичный git-репозиторий: GitHub, Bitbucket, GitLab и т.п.
 * *
 * Created by abyakimenko on 19.01.2018.
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Building building = new Building();
        Elevator elevator = building.getElevator();

        System.out.println(">>>Building and Elevator were initiated: " + building);
        System.out.println("");
        System.out.println(">>>Please enter the floor number (from 1 till " + (building.getFloors()) + ")");

        Set<Integer> treeSet = new TreeSet<>();

        treeSet.add(14);
        treeSet.add(1);
        treeSet.add(4);
        treeSet.add(17);
        treeSet.add(16);

        for (; ; ) {
            try {

                Scanner scanner = new Scanner(System.in);
                int floor = scanner.nextInt();

                System.out.println("Users's entered floor: " + floor);
                
                elevator.moveTo(floor);
            } catch (Exception ex) {
                logger.error("Something went wrong", ex);
            }
        }
    }
}
