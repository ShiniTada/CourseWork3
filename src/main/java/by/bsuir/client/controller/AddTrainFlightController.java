package by.bsuir.client.controller;

import by.bsuir.client.entity.TrainFlight;
import by.bsuir.client.exception.WriteDataException;
import by.bsuir.client.messages.WrongMessages;
import by.bsuir.client.path.PathManager;
import by.bsuir.client.repository.Repository;
import by.bsuir.client.repository.impl.TrainFlightRepository;
import by.bsuir.client.txtworker.TxtWorker;
import by.bsuir.client.txtworker.impl.TxtWorkerImpl;
import by.bsuir.client.validator.DataValidator;
import by.bsuir.client.validator.impl.DataValidatorImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddTrainFlightController {

    private static final Logger LOGGER = LogManager.getLogger(AddTrainFlightController.class);

    public TextField numberField;
    public TextField typeField;
    public TextField routeField;
    public TextField departureField;
    public TextField arrivalField;
    public Button okButton;
    public Button cancelButton;
    public Label incorrectNumber;
    public Label incorrectType;
    public Label incorrectRoute;
    public Label incorrectDeparture;
    public Label incorrectArrival;

    private Repository<TrainFlight> trainFlightRepository = TrainFlightRepository.getInstance();
    private DataValidator validator = DataValidatorImpl.getInstance();

    private String number;
    private String type;
    private String route;
    private String departure;
    private String arrival;

    private void init() {
        incorrectNumber.setText("");
        incorrectType.setText("");
        incorrectRoute.setText("");
        incorrectDeparture.setText("");
        incorrectArrival.setText("");
        number = numberField.getText();
        type = typeField.getText();
        route = routeField.getText();
        departure = departureField.getText();
        arrival = arrivalField.getText();
    }

    private boolean checkInput() {
        if(number.equals("") || !validator.checkTrainFlightNumber(number)){
            incorrectNumber.setText(WrongMessages.INCORRECT_INPUT);
            return false;
        }
        if(trainFlightRepository.get(number) != null){
            incorrectNumber.setText(WrongMessages.INCORRECT_TRAIN_FLIGHT_NUMBER);
            return false;
        }
        if(type.equals("") || !validator.checkTrainFlightType(type)){
            incorrectType.setText(WrongMessages.INCORRECT_INPUT);
            return false;
        }
        if(route.equals("") || !validator.checkTrainFlightRoute(route)){
            incorrectRoute.setText(WrongMessages.INCORRECT_INPUT);
            return false;
        }
        if(departure.equals("") || !validator.checkTrainFlightTime(departure)){
            incorrectDeparture.setText(WrongMessages.INCORRECT_INPUT);
            return false;
        }
        if(arrival.equals("") || !validator.checkTrainFlightTime(arrival)){
            incorrectArrival.setText(WrongMessages.INCORRECT_INPUT);
            return false;
        }
        return true;

    }

    public void addMethod(ActionEvent actionEvent) {
        init();
        if(checkInput()) {
            saveNewTrain();
            AdminController.primaryStageAddTrainFlight.close();
        }
    }

    private void saveNewTrain() {
        List<String> record = Arrays.asList(number, type, route, departure, arrival);
        TrainFlight trainFlight = new TrainFlight(record);
        trainFlightRepository.add(trainFlight);

        List<String> records = new ArrayList<>();
        String stringRecord;
        for (TrainFlight t: trainFlightRepository.getAll()) {
            stringRecord = t.getNumber() + " " + t.getType() + " " + t.getRoute() + " " + t.getDeparture() + " " + t.getArrival();
            records.add(stringRecord);
        }
        TxtWorker txtWorker = TxtWorkerImpl.getInstance();
        try {
            txtWorker.rewriteDataTxt(PathManager.TRAIN_FLIGHT_FILE, records);
        } catch (WriteDataException e) {
            LOGGER.warn("Запись " + record + " не добавлена в файл " + PathManager.TRAIN_FLIGHT_FILE);
        }

    }

    public void cancelMethod(ActionEvent actionEvent) {
        AdminController.primaryStageAddTrainFlight.close();
    }
}
