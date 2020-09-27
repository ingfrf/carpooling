package com.ingfrf.carpooling.dao;

import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class CarpoolingDAOImpl implements CarpoolingDAO {
    //private HashMap<Integer, Queue<Car>> carMap;
    //private CarMap carMap;
    private HashMap<Integer, Car> availableCars;
    private HashMap<Integer, Car> travels;
    private Queue<Journey> waitingQueue;

    public CarpoolingDAOImpl() {
        initialization();
    }

    @Override
    public void addCars(List<Car> carList) {
        // Load the list of available cars in the service and remove all previous data
        // (existing journeys and cars)
        initialization();
        carList.forEach(car -> availableCars.put(car.getId(), car));
        System.out.println("abofe");
    }

    @Override
    public void addJourney(Journey journey) {
        Car selectedCar;

        List<Car> mAvailableCars = availableCars.entrySet().stream()
                .filter(v -> v.getValue().getSeats() == journey.getPeople())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList())
        ;

        System.out.println("/////////////////////////////");
        System.out.println(mAvailableCars);



        List<Car> maxAvailableCars = availableCars.entrySet().stream()
                .filter(v -> v.getValue().getSeats() >= journey.getPeople())
                .map(Map.Entry::getValue)
                .collect(Collectors.groupingBy(Car::getSeats))
                .entrySet().stream()
                .map(Map.Entry::getValue)
                .reduce((l1,l2) -> l1.size() >= l2.size() ? l1: l2)
                .orElse(Collections.emptyList())
        ;

        System.out.println("-------- filtrados ---------------");
        System.out.println(availableCars.entrySet().stream()
                .filter(v -> v.getValue().getSeats() >= journey.getPeople())
                .map(Map.Entry::getValue)
                .collect(Collectors.groupingBy(Car::getSeats)));

        if (maxAvailableCars != null && !maxAvailableCars.isEmpty()) {
            System.out.println("****** DISPONIBLES ******");
            System.out.println(maxAvailableCars);
            System.out.println("-------- SELECTED ------");
            selectedCar = maxAvailableCars.get(0);
            System.out.println(selectedCar);
            travels.put(journey.getId(), selectedCar);
            availableCars.remove(selectedCar.getId());
        } else {
            waitingQueue.add(journey);
        }
        printAll();
    }

    @Override
    public void removeJourneyById(Integer journeyId) {
       if (travels.containsKey(journeyId)) {
           travels.remove(journeyId);
       }
       // se se fan 2 cousas, fanse no servicio
       boolean result = waitingQueue.removeIf(w -> w.getId() == journeyId);
    }

    @Override
    public Car retrieveCarByJourneyId(Integer journeyId) {
        if (travels.containsKey(journeyId)) {
            return travels.get(journeyId);
        }
        waitingQueue.contains(journeyId);
        return null;
    }

    private void initialization() {
        //carMap = new CarMap();
        availableCars = new HashMap<>();
        travels = new HashMap<>();
        waitingQueue = new LinkedList<>();
    }

    // just for test
    private void printAvailableCars() {
        availableCars.entrySet().stream().forEach(System.out::println);
    }
    private void printTravels() {
        travels.entrySet().stream().forEach(System.out::println);
    }
    private void printWaitingQueue() {
        waitingQueue.forEach(System.out::println);
    }
    private void printAll() {
        System.out.println("***************** AVAILABLES *****************");
        printAvailableCars();
        System.out.println("***************** TRAVELS *****************");
        printTravels();
        System.out.println("***************** WAITING *****************");
        printWaitingQueue();
    }
}
