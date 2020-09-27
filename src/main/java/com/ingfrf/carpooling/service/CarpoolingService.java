package com.ingfrf.carpooling.service;

import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;

import java.util.List;

public interface CarpoolingService {
    void addCars(List<Car> carList);
    void addJourney(Journey journey);
}
