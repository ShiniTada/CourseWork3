package by.bsuir.client.controller;

import by.bsuir.client.entity.TrainFlight;
import by.bsuir.client.exception.WriteDataException;
import by.bsuir.client.messages.Titles;
import by.bsuir.client.path.PathManager;
import by.bsuir.client.repository.Repository;
import by.bsuir.client.repository.impl.TrainFlightRepository;
import by.bsuir.client.txtworker.TxtWorker;
import by.bsuir.client.txtworker.impl.TxtWorkerImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class AdminController {

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);
    public Button addTrainButton;
    public Button deleteTrainButton;
    public Button kondorseButton;
    public Button exitButton;
    public TableColumn<TrainFlight, Integer> tableNumber;
    public TableColumn<TrainFlight, String> tableType;
    public TableColumn<TrainFlight, String> tableRoute;
    public TableColumn<TrainFlight, String> tableDeparture;
    public TableColumn<TrainFlight, String> tableArrival;
    public TableView<TrainFlight> tableData;
    public Button updateButton;
    public Button addToSelection;

    private ObservableList<TrainFlight> trainFlights = FXCollections.observableArrayList();
    private Repository<TrainFlight> trainFlightRepository = TrainFlightRepository.getInstance();
    public static SortedSet<TrainFlight> trainsKondorse = new TreeSet<>();

    @FXML
    private void initialize() {
        List<TrainFlight> listTrainFlight = new ArrayList<>(trainFlightRepository.getAll());
        trainFlights.addAll(listTrainFlight);

        tableNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableRoute.setCellValueFactory(new PropertyValueFactory<>("route"));
        tableDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
        tableArrival.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        tableData.setItems(trainFlights);
    }

    public static Parent root3;
    public static Stage primaryStageAddTrainFlight = new Stage();
    public static Parent root4;
    public static Stage primaryStageKondorse = new Stage();
    private static Parent rootWarning;
    private static Stage primaryStageWarning = new Stage();

    public void addTrainMethod(ActionEvent actionEvent) throws IOException {
        root3 = FXMLLoader.load(getClass().getResource(PathManager.ADD_TRAIN_FLIGHT_PAGE));
        primaryStageAddTrainFlight.setTitle(Titles.ADD_TRAIN_FLIGHT_TITLE);
        primaryStageAddTrainFlight.setScene(new Scene(root3, 480, 650));
        primaryStageAddTrainFlight.show();
    }

    public void deleteTrainMethod(ActionEvent actionEvent) {
        TrainFlight t = tableData.getSelectionModel().getSelectedItem();
        tableData.getItems().remove(t);
        trainFlightRepository.remove(t);
        LOGGER.info("Deleted - " + t);
        saveChangesInFile();
    }

    public void updateMethod(ActionEvent actionEvent) {
        tableData.getItems().removeAll(tableData.getItems());
        initialize();
    }

    public void kondorseMethod(ActionEvent actionEvent) throws IOException {
        if(trainsKondorse.size()< 2){
            rootWarning = FXMLLoader.load(getClass().getResource(PathManager.WARNING_PAGE));
            primaryStageWarning.setTitle(Titles.WARNING_TITLE);
            primaryStageKondorse.setScene(new Scene(rootWarning, 250, 200));
            primaryStageKondorse.show();
        } else {
            root4 = FXMLLoader.load(getClass().getResource(PathManager.KONDORSE_PAGE));
            primaryStageKondorse.setTitle(Titles.KONDORSE_TITLE);
            primaryStageKondorse.setScene(new Scene(root4, 1050, 800));
            primaryStageKondorse.show();
        }
    }

    public void addToSelectionMethod(ActionEvent actionEvent) throws IOException {
        if (trainsKondorse.size() == 5) {
            rootWarning = FXMLLoader.load(getClass().getResource(PathManager.WARNING_PAGE));
            primaryStageWarning.setTitle(Titles.WARNING_TITLE);
            primaryStageWarning.setScene(new Scene(rootWarning, 250, 200));
            primaryStageWarning.show();
        } else {
            TrainFlight t = tableData.getSelectionModel().getSelectedItem();
            LOGGER.info("Added - " + t);
            trainsKondorse.add(t);
        }
    }


    public void exitMethod(ActionEvent actionEvent) {
        StartController.primaryStageUser.close();
    }


    private void saveChangesInFile(){
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
            LOGGER.warn("Записи не обновились в файле " + PathManager.TRAIN_FLIGHT_FILE);
        }
    }
}
