package com.ingfrf.carpooling.dao;

import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;
import com.ingfrf.carpooling.model.Travel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class CarpoolingDAOImpl implements CarpoolingDAO {
    private HashMap<Integer, Car> availableCars;
    private HashMap<Integer, Travel> travels;
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
    public void addTravel(Travel travel) {
        travels.put(travel.getJourney().getId(), travel);
        availableCars.remove(travel.getCar().getId());
    }

    @Override
    public void addJourneyToWaitingQueue(Journey journey) {
        waitingQueue.add(journey);
    }

    @Override
    public Car retrieveAvailableCar(Integer people) {
        // just for tests
        log.debug("\n-------- filtered ---------------\n" +
                availableCars.values().stream()
                        .filter(c -> c.getSeats() >= people)
                        .sorted(Comparator.comparingInt(Car::getSeats))
                .collect(Collectors.groupingBy(Car::getSeats))
                .toString()
        );


        Optional<Car> maxAvailableCarsBySeat = availableCars.values().stream()
                .filter(c -> c.getSeats() >= people)
                .min(Comparator.comparingInt(Car::getSeats));
                //.sorted(Comparator.comparingInt(Car::getSeats))
                //.findFirst();
        if (maxAvailableCarsBySeat.isPresent()) {
            // just for test
            log.debug("Selected::"+maxAvailableCarsBySeat.get());

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
            Travel travel = Travel.builder()
                    .car(car)
                    .journey(waitingJourney.get())
                    .build();
            travels.put(waitingJourney.get().getId(), travel);
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
        return travels.get(journeyId).getCar();
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
        availableCars.values().forEach(c -> log.debug(c.toString()));
    }

    private void printTravels() {
        travels.values().forEach(t -> log.debug(t.toString()));
    }

    private void printWaitingQueue() {
        waitingQueue.forEach(j-> log.debug(j.toString()));
    }

    @Override
    public void printAll() {
        log.debug("***************** AVAILABLES *****************");
        printAvailableCars();
        log.debug("***************** TRAVELS *****************");
        printTravels();
        log.debug("***************** WAITING *****************");
        printWaitingQueue();
    }
}
