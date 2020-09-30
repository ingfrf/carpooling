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
    private HashMap<Integer, Car> availableCars;
    private HashMap<Integer, Car> travels;
    private Queue<Journey> waitingQueue;

    public CarpoolingDAOImpl() {
        availableCars = new HashMap<>();
        travels = new HashMap<>();
        waitingQueue = new LinkedList<>();
    }

    @Override
    public void addCars(List<Car> carList) {
        // Load the list of available cars in the service and remove all previous data
        // (existing journeys and cars)
        availableCars.clear();
        travels.clear();
        waitingQueue.clear();
        carList.forEach(car -> availableCars.put(car.getId(), car));
    }

    @Override
    public void addJourney(Journey journey, Car car) {
        travels.put(journey.getId(), car);
        availableCars.remove(car.getId());
    }

    @Override
    public void addJourneyToWaitingQueue(Journey journey) {
        waitingQueue.add(journey);
    }

    @Override
    public Car retrieveAvailableCar(Integer people) {
        // TODO Delete
        System.out.println("-------- filtrados ---------------");
        System.out.println(availableCars.values().stream()
                        .filter(c -> c.getSeats() >= people)
                        .sorted(Comparator.comparingInt(Car::getSeats))
                .collect(Collectors.groupingBy(Car::getSeats)));


        Optional<Car> maxAvailableCarsBySeat = availableCars.values().stream()
                .filter(c -> c.getSeats() >= people)
                .min(Comparator.comparingInt(Car::getSeats));
                //.sorted(Comparator.comparingInt(Car::getSeats))
                //.findFirst();
        if (maxAvailableCarsBySeat.isPresent()) {
            //TODO delete
            System.out.println("Selected::"+maxAvailableCarsBySeat.get());

            return maxAvailableCarsBySeat.get();
        }
        return null;
    }

    @Override
    public void removeJourneyFromTravelsById(Integer journeyId) {
        // the journey drops off the people
        // so a new car is available
        Car car = retrieveCarByJourneyId(journeyId);
        travels.remove(journeyId);
        // check if the car can pick up a waiting journey
        Optional<Journey> waitingJourney = waitingQueue.stream()
                .filter(j -> j.getPeople() <= car.getSeats())
                .findFirst();
        if (waitingJourney.isPresent()) {
            travels.put(waitingJourney.get().getId(), car);
            removeJourneyFromWaitingQueueById(waitingJourney.get().getId());
        } else {
            availableCars.put(car.getId(), car);
        }
        printAll();
    }

    @Override
    public void removeJourneyFromWaitingQueueById(Integer journeyId) {
        waitingQueue.removeIf(w -> w.getId().intValue() == journeyId.intValue());
    }

    @Override
    public Car retrieveCarByJourneyId(Integer journeyId) {
        return travels.get(journeyId);
    }

    @Override
    public boolean isJourneyInTravels(Integer journeyId) {
        return travels.containsKey(journeyId);
    }

    @Override
    public boolean isJourneyInWaitingQueue(Integer journeyId) {
        return waitingQueue.stream()
                .anyMatch(j -> j.getId().intValue() == journeyId.intValue());
    }

    // just for test
    private void printAvailableCars() {
        availableCars.entrySet().forEach(System.out::println);
    }

    private void printTravels() {
        travels.entrySet().forEach(System.out::println);
    }

    private void printWaitingQueue() {
        waitingQueue.forEach(System.out::println);
    }

    @Override
    public void printAll() {
        System.out.println("***************** AVAILABLES *****************");
        printAvailableCars();
        System.out.println("***************** TRAVELS *****************");
        printTravels();
        System.out.println("***************** WAITING *****************");
        printWaitingQueue();
    }
}
