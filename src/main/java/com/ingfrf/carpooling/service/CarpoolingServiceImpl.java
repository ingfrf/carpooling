package com.ingfrf.carpooling.service;

import com.ingfrf.carpooling.dao.CarpoolingDAO;
import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarpoolingServiceImpl implements CarpoolingService {

    @Autowired
    private CarpoolingDAO carpoolingDAO;

    @Override
    public void addCars(List<Car> carList) {
        carpoolingDAO.addCars(carList);
    }

    @Override
    public void addJourney(Journey journey) {
        carpoolingDAO.addJourney(journey);
    }
}
