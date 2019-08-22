package by.bsuir.client.creator;

import by.bsuir.client.entity.TrainFlight;
import by.bsuir.client.entity.User;
import by.bsuir.client.exception.IncorrectDataException;
import by.bsuir.client.exception.ReadDataException;
import by.bsuir.client.parser.DataParser;
import by.bsuir.client.parser.impl.DataParserImpl;
import by.bsuir.client.repository.Repository;
import by.bsuir.client.repository.impl.TrainFlightRepository;
import by.bsuir.client.repository.impl.UserRepository;
import by.bsuir.client.txtworker.TxtWorker;
import by.bsuir.client.txtworker.impl.TxtWorkerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Creator {
    private static final Logger LOGGER = LogManager.getLogger(Creator.class);

    private static final Creator INSTANCE = new Creator();

    private Creator() {}

    public static Creator getInstance() {
        return INSTANCE;
    }

    private TxtWorker reader = TxtWorkerImpl.getInstance();
    private DataParser parser = DataParserImpl.getInstance();
    private Repository<User> userRepository = UserRepository.getInstance();
    private Repository<TrainFlight> trainFlightRepository = TrainFlightRepository.getInstance();


    public void createListUsersFromFile(String path) {
        List<String> strings = getDataFromFile(path);
        for (String string : strings) {
            try {
                User user = new User(parser.parseUser(string));
                userRepository.add(user);
            } catch (IncorrectDataException e) {
                LOGGER.warn("Incorrect data:" + string);
            }
            LOGGER.info("Users was created.");
        }
    }

    public void createListTrainFlightFromFile(String path) {
        List<String> strings = getDataFromFile(path);
        for (String string : strings) {
            try {
                TrainFlight trainFlight = new TrainFlight(parser.parseTrainFlight(string));
                trainFlightRepository.add(trainFlight);
            } catch (IncorrectDataException e) {
                LOGGER.warn("Incorrect data:" + string);
            }
            LOGGER.info("TrainFlight was created.");
        }
    }

    public List<String> getDataFromFile(String path) {
        try {
            return reader.readDataTxt(path);
        } catch (ReadDataException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }
    }


}
