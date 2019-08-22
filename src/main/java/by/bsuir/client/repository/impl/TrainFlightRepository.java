package by.bsuir.client.repository.impl;

import by.bsuir.client.entity.TrainFlight;
import by.bsuir.client.repository.Repository;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class TrainFlightRepository implements Repository<TrainFlight> {

    private static TrainFlightRepository INSTANCE = new TrainFlightRepository();

    public static TrainFlightRepository getInstance() {
        return INSTANCE;
    }

    private SortedSet<TrainFlight> trainFlights = new TreeSet<>();

    private TrainFlightRepository() {
    }

    @Override
    public void add(TrainFlight trainFlight) {
        trainFlights.add(trainFlight);
    }

    @Override
    public void remove(TrainFlight trainFlight) {
        trainFlights.remove(trainFlight);
    }

    @Override
    public TrainFlight get(String login, String password) {
        return null;
    }

    @Override
    public TrainFlight get(String number) {
        int realNumber = Integer.parseInt(number);
        Iterator<TrainFlight> iter = trainFlights.iterator();
        while (iter.hasNext()) {
            TrainFlight trainFlightFromRepository = iter.next();
            if (trainFlightFromRepository.getNumber() == realNumber) {
                return trainFlightFromRepository;
            }
        }
        return null;
    }

    @Override
    public SortedSet<TrainFlight> getAll() {
        return  trainFlights;
    }
}
