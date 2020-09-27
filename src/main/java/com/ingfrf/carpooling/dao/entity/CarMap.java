package com.ingfrf.carpooling.dao.entity;

import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CarMap extends HashMap<Integer, Queue<Car>> {

    public CarMap() {
        init();
    }
    
    private void init() {
       CarSeatEnum.stream().forEach(seat -> put(seat.getSeat(), new LinkedList<Car>()));
    }

    /**
     * add each car to a queue where its number of seats is the map id
     * @param carList
     */
    public void addCars(List<Car> carList) {
        carList.forEach(car -> {
            get(car.getSeats()).add(car);
        });
    }

    public Car getCarForJourney(Journey journey) {
        int people = journey.getPeople();
        CarSeatEnum carSeatEnum = CarSeatEnum.getInstanceBySeat(people);
        switch (carSeatEnum) {
            case Under_min:
            case Four_seats:
            case Five_seats:
            case Six_seats:
        }
        return null;
    }

    private Car getAvailableCarFromMostOccupiedQueue() {
        return null;
    }

}
