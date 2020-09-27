package com.ingfrf.carpooling.dao;

import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;

import java.util.List;

public interface CarpoolingDAO {
    void addCars(List<Car> carList);
    void addJourney(Journey journey);
    void removeJourneyFromTravelsById(Integer journeyId);
    void removeJourneyFromWaitingQueueById(Integer journeyId);
    Car retrieveCarByJourneyId(Integer journeyId);
    boolean isJourneyInTravels(Integer journeyId);
    boolean isJourneyInWaitingQueue(Integer journeyId);
}
