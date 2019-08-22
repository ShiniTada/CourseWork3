package by.bsuir.client.controller;

import by.bsuir.client.entity.*;
import by.bsuir.client.messages.Titles;
import by.bsuir.client.messages.WrongMessages;
import by.bsuir.client.path.PathManager;
import by.bsuir.client.txtworker.TxtWorker;
import by.bsuir.client.txtworker.impl.TxtWorkerImpl;
import by.bsuir.server.exception.ConnectionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class KondorseController {

    private static final Logger LOGGER = LogManager.getLogger(KondorseController.class);
    public Button okButton;

    public TableColumn<TrainFlight, Integer> tableNumber;
    public TableColumn<TrainFlight, String> tableType;
    public TableColumn<TrainFlight, String> tableRoute;
    public TableColumn<TrainFlight, String> tableDeparture;
    public TableColumn<TrainFlight, String> tableArrival;
    public TableView<TrainFlight> tableData;

    public TableColumn<TrainFlight, Integer> tableNumber1;
    public TableColumn<TrainFlight, String> tableType1;
    public TableColumn<TrainFlight, String> tableRoute1;
    public TableColumn<TrainFlight, String> tableDeparture1;
    public TableColumn<TrainFlight, String> tableArrival1;
    public TableView<TrainFlight> tableData1;
    public ComboBox<Integer> comboBox11, comboBox12, comboBox13, comboBox14, comboBox15;
    public ComboBox<Integer> comboBox21, comboBox22, comboBox23, comboBox24, comboBox25;
    public ComboBox<Integer> comboBox31, comboBox32, comboBox33, comboBox34, comboBox35;
    public Button cancelButton;
    public Label errorMessage;
    public TextField a11, a12, a13, a14, a15;
    public TextField a21, a22, a23, a24, a25;
    public TextField a31, a32, a33, a34, a35;
    public TextField a41, a42, a43, a44, a45;
    public TextField a51, a52, a53, a54, a55;
    public Label lv1, lv2, lv3, lv4, lv5;
    public Label lg1, lg2, lg3, lg4, lg5;
    public Label stringResult;
    public Button deleteButton;
    public Button otchetButton;

    private Expert expert_1 = new Expert();
    private Expert expert_2 = new Expert();
    private Expert expert_3 = new Expert();
    private int bestVariant;
    public static String otchetPath;

    private ObservableList<TrainFlight> trainFlights = FXCollections.observableArrayList();
    private ObservableList<TrainFlight> resultTableRecords = FXCollections.observableArrayList();

    private Socket clientSocket;
    private InputStream IS;
    private OutputStream OS;
    private static final int TASK_CODE = 1;
    private static final int FINISH_CODE = 0;
    private int NEED_SIZE;

    private static Parent rootGoodOtchet;
    private static Stage primaryStageGoodOtchet = new Stage();

    @FXML
    private void initialize() {
        if(StartController.user != null) {
            if (StartController.user.getLogin().equals("админ")) {
                trainFlights.addAll(AdminController.trainsKondorse);
            } else {
                trainFlights.addAll(UserController.trainsKondorse);
            }
        }else {
            trainFlights.addAll(UserController.trainsKondorse);
        }

        tableNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableRoute.setCellValueFactory(new PropertyValueFactory<>("route"));
        tableDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
        tableArrival.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        tableData.setItems(trainFlights);
        ObservableList<Integer> padding = FXCollections.observableArrayList();
        NEED_SIZE = trainFlights.size();
        for (int i = 1; i <= NEED_SIZE; i++) {
            padding.add(i);
        }
        comboBox11.setItems(padding);
        comboBox12.setItems(padding);
        comboBox13.setItems(padding);
        comboBox14.setItems(padding);
        comboBox15.setItems(padding);
        comboBox21.setItems(padding);
        comboBox22.setItems(padding);
        comboBox23.setItems(padding);
        comboBox24.setItems(padding);
        comboBox25.setItems(padding);
        comboBox31.setItems(padding);
        comboBox32.setItems(padding);
        comboBox33.setItems(padding);
        comboBox34.setItems(padding);
        comboBox35.setItems(padding);

        switch (NEED_SIZE) {
            case 2:
                comboBox13.setVisible(false);
                comboBox23.setVisible(false);
                comboBox33.setVisible(false);
                lg3.setVisible(false);
                lv3.setVisible(false);
                a31.setVisible(false);
                a32.setVisible(false);
                a33.setVisible(false);
                a13.setVisible(false);
                a23.setVisible(false);
            case 3:
                comboBox14.setVisible(false);
                comboBox24.setVisible(false);
                comboBox34.setVisible(false);
                lg4.setVisible(false);
                lv4.setVisible(false);
                a41.setVisible(false);
                a42.setVisible(false);
                a43.setVisible(false);
                a44.setVisible(false);
                a14.setVisible(false);
                a24.setVisible(false);
                a34.setVisible(false);
            case 4:
                comboBox15.setVisible(false);
                comboBox25.setVisible(false);
                comboBox35.setVisible(false);
                lg5.setVisible(false);
                lv5.setVisible(false);
                a51.setVisible(false);
                a52.setVisible(false);
                a53.setVisible(false);
                a54.setVisible(false);
                a55.setVisible(false);
                a15.setVisible(false);
                a25.setVisible(false);
                a35.setVisible(false);
                a45.setVisible(false);
                break;
            default:
                break;
        }
    }

    public void deleteTrainMethod(ActionEvent actionEvent) {
        if(NEED_SIZE > 2) {
            TrainFlight t = tableData.getSelectionModel().getSelectedItem();
            if(StartController.user != null) {
                if (StartController.user.getLogin().equals("админ")) {
                    AdminController.trainsKondorse.remove(t);
                } else {
                    UserController.trainsKondorse.remove(t);
                }
            } else {
                UserController.trainsKondorse.remove(t);
            }
            tableData.getItems().removeAll(tableData.getItems());
            LOGGER.info("Deleted from kondorse - " + t);
            initialize();
        }
    }

    public void findMethod(ActionEvent actionEvent) {
        errorMessage.setText(" ");
        stringResult.setText(" ");
        if (!initExperts()) {
            LOGGER.warn("Incorrect input data!!! Have empty opinions");
            errorMessage.setText(WrongMessages.INCORRECT_EXPERT_OPINIONS);
        } else {
            errorMessage.setText(" ");
            try {
                task(initAndGetRequest());
                otchetButton.setDisable(false);
            } catch (ConnectionException e) {
                LOGGER.warn("Connection did't set");
            }
        }

    }


    private boolean initExperts() {
        expert_1.removeAll();
        expert_2.removeAll();
        expert_3.removeAll();
        if (comboBox11.getValue() != null && comboBox21.getValue() != null && comboBox31.getValue() != null) {
            expert_1.addOpinion(comboBox11.getValue());
            expert_2.addOpinion(comboBox21.getValue());
            expert_3.addOpinion(comboBox31.getValue());
        }
        if (comboBox12.getValue() != null && comboBox22.getValue() != null && comboBox32.getValue() != null) {
            expert_1.addOpinion(comboBox12.getValue());
            expert_2.addOpinion(comboBox22.getValue());
            expert_3.addOpinion(comboBox32.getValue());
        }
        if (trainFlights.size() >= 3 && comboBox13.getValue() != null && comboBox23.getValue() != null && comboBox33.getValue() != null) {
            expert_1.addOpinion(comboBox13.getValue());
            expert_2.addOpinion(comboBox23.getValue());
            expert_3.addOpinion(comboBox33.getValue());
        }
        if (trainFlights.size() >= 4 && comboBox14.getValue() != null && comboBox24.getValue() != null && comboBox34.getValue() != null) {
            expert_1.addOpinion(comboBox14.getValue());
            expert_2.addOpinion(comboBox24.getValue());
            expert_3.addOpinion(comboBox34.getValue());
        }
        if (trainFlights.size() == 5 && comboBox15.getValue() != null && comboBox25.getValue() != null && comboBox35.getValue() != null) {
            expert_1.addOpinion(comboBox15.getValue());
            expert_2.addOpinion(comboBox25.getValue());
            expert_3.addOpinion(comboBox35.getValue());
        }
        return (expert_1.amountOpinions() == NEED_SIZE && expert_2.amountOpinions() == NEED_SIZE && expert_3.amountOpinions() == NEED_SIZE);

    }

    private ExpertsDecision initAndGetRequest(){
        List<Integer> list = new ArrayList<>();
        list.addAll(expert_1.getAllOpinions());
        list.addAll(expert_2.getAllOpinions());
        list.addAll(expert_3.getAllOpinions());
        return new ExpertsDecision(list);
    }

    private void task(ExpertsDecision request) throws ConnectionException {
        ObjectOutputStream oos;
        ObjectInputStream ois;
        try {
            // Create a connection to the server socket on the server application
            InetAddress host = InetAddress.getLocalHost();
            clientSocket = new Socket(host.getHostName(), 1020);
            OS = clientSocket.getOutputStream();
            IS = clientSocket.getInputStream(); //получение входной поток для чтения данных

            oos = new ObjectOutputStream(OS);
            oos.writeInt(TASK_CODE);
            oos.flush();
            oos = new ObjectOutputStream(OS);
            oos.writeInt(NEED_SIZE);
            oos.flush();
            oos = new ObjectOutputStream(OS);
            oos.writeObject(request);
            oos.flush();

            ois = new ObjectInputStream(IS);
            ServerDecision response1 = (ServerDecision) ois.readObject();
            LOGGER.info("Answer from server " + response1);
            initDecisionMatrix(response1);

            ois = new ObjectInputStream(IS);
            bestVariant = ois.readInt();
            LOGGER.info("Answer from server " + bestVariant);
            if(bestVariant == 0) {
                errorMessage.setText("У экспертов слишком расбросанные мнения. Они не смогли выбрать наилучший вариант.");
                LOGGER.info("Condorcet Paradox!!!");
            } else {
                initResultTable();
            }

            oos = new ObjectOutputStream(OS);
            oos.writeInt(FINISH_CODE);
            oos.flush();

            IS.close();
            OS.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.warn("На стороне клиента ошибки с соединением.");
            throw new ConnectionException("На стороне клиента ошибки с соединением. " + e);
        }
    }

    private void initDecisionMatrix(ServerDecision response) {
        Map<String, Integer> map = response.getFirstBetterSecond();
        switch (NEED_SIZE) {
            case 5:
                a51.setText(String.valueOf(map.get("4better0")));
                a52.setText(String.valueOf(map.get("4better1")));
                a53.setText(String.valueOf(map.get("4better2")));
                a54.setText(String.valueOf(map.get("4better3")));
                a55.setText(String.valueOf(map.get("4better1")));
                a15.setText(String.valueOf(map.get("0better4")));
                a25.setText(String.valueOf(map.get("1better4")));
                a35.setText(String.valueOf(map.get("2better4")));
                a45.setText(String.valueOf(map.get("3better4")));
            case 4:
                a41.setText(String.valueOf(map.get("3better0")));
                a42.setText(String.valueOf(map.get("3better1")));
                a43.setText(String.valueOf(map.get("3better2")));
                a14.setText(String.valueOf(map.get("0better3")));
                a24.setText(String.valueOf(map.get("1better3")));
                a34.setText(String.valueOf(map.get("2better3")));
            case 3:
                a31.setText(String.valueOf(map.get("2better0")));
                a32.setText(String.valueOf(map.get("2better1")));
                a13.setText(String.valueOf(map.get("0better2")));
                a23.setText(String.valueOf(map.get("1better2")));
            case 2:
                a21.setText(String.valueOf(map.get("1better0")));
                a12.setText(String.valueOf(map.get("0better1")));
            default:
                break;
        }

    }

    private void initResultTable() {
        int response2 = bestVariant;
        stringResult.setText("Наиболее предпочтителен " + response2 + " вариант");
        tableData1.getItems().removeAll(tableData1.getItems());
        resultTableRecords.add(tableData.getItems().get(--response2));

        tableNumber1.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableType1.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableRoute1.setCellValueFactory(new PropertyValueFactory<>("route"));
        tableDeparture1.setCellValueFactory(new PropertyValueFactory<>("departure"));
        tableArrival1.setCellValueFactory(new PropertyValueFactory<>("arrival"));

        tableData1.setItems(resultTableRecords);

    }


    public void otchetMethod(ActionEvent actionEvent) throws IOException {
        int response2 = bestVariant;
        List<String> lines = new ArrayList<>();
        String line;
        lines.add("Выбраны следующие рейсы: ");
        for (TrainFlight trainFlight : trainFlights) {
            lines.add(trainFlight.toString());
        }
        lines.add("Мнения экспертов: ");
        lines.add("Эксперт 1: " + expert_1.getAllOpinions().toString());
        lines.add("Эксперт 2: " + expert_2.getAllOpinions().toString());
        lines.add("Эксперт 3: " + expert_3.getAllOpinions().toString());
        lines.add("\n");
        if(response2 == 0) {
            lines.add("У экспертов слишком расбросанные мнения. Они не смогли выбрать наилучший вариант.");
        } else {
            lines.add("Наиболее предпочтителен " + bestVariant + " вариант.");
            lines.add(tableData.getItems().get(--response2).toString());
        }
        TxtWorker txtWorker = TxtWorkerImpl.getInstance();
        String login = null;

        if(StartController.user != null) {
            if (StartController.user.getLogin() != null) {
                login = StartController.user.getLogin();
            } else {
                login = SighUpController.user.getLogin();
            }
        } else {
            login = SighUpController.user.getLogin();
        }
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd'_'hh.mm.ss");
        otchetPath = "src/resources/otchet/" + login + "Отчет" + formatForDateNow.format(dateNow) + ".txt";
        try {
            txtWorker.createOtchetTxt(otchetPath, lines);
        } catch (IOException e) {
            LOGGER.warn("Отчет пользователя " +  login + " не был создан.");
        }
        rootGoodOtchet = FXMLLoader.load(getClass().getResource(PathManager.GOOD_OTCHET_PAGE));
        primaryStageGoodOtchet.setTitle(Titles.GOOD_OTCHET_TITLE);
        primaryStageGoodOtchet.setScene(new Scene(rootGoodOtchet, 290, 215));
        primaryStageGoodOtchet.show();
        otchetButton.setDisable(true);
    }


    public void cancelMethod(ActionEvent actionEvent) {
        tableData1.getItems().removeAll(tableData1.getItems());
        trainFlights.removeAll(trainFlights);
        if(StartController.user != null) {
            if (StartController.user.getLogin().equals("админ")) {
                AdminController.primaryStageKondorse.close();
            } else {
                UserController.primaryStageKondorse.close();
            }
        } else {
            UserController.primaryStageKondorse.close();
        }
    }
}
