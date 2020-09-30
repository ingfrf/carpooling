package com.ingfrf.carpooling.dao;

import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;
import com.ingfrf.carpooling.model.Travel;

import java.util.List;

public interface CarpoolingDAO {
    void addCars(List<Car> carList);
    void addTravel(Travel travel);
    void addJourneyToWaitingQueue(Journey journey);
    Car retrieveAvailableCar(Integer people);
    void removeJourneyFromTravelsById(Integer journeyId);
    void removeJourneyFromWaitingQueueById(Integer journeyId);
    Car retrieveCarByJourneyId(Integer journeyId);
    boolean isJourneyInTravels(Integer journeyId);
    boolean isJourneyInWaitingQueue(Integer journeyId);

    void printAll();
}
