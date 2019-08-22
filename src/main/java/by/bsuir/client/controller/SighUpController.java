package by.bsuir.client.controller;

import by.bsuir.client.entity.User;
import by.bsuir.client.exception.WriteDataException;
import by.bsuir.client.messages.Titles;
import by.bsuir.client.messages.WrongMessages;
import by.bsuir.client.path.PathManager;
import by.bsuir.client.repository.Repository;
import by.bsuir.client.repository.impl.UserRepository;
import by.bsuir.client.txtworker.TxtWorker;
import by.bsuir.client.txtworker.impl.TxtWorkerImpl;
import by.bsuir.client.validator.DataValidator;
import by.bsuir.client.validator.impl.DataValidatorImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class SighUpController {
    private static final Logger LOGGER = LogManager.getLogger(SighUpController.class);

    public Button nextButton;
    public Button backButton;
    public TextField loginField;
    public PasswordField passwordField;
    public PasswordField repeatedPasswordField;

    public Label incorrectPassword;
    public Label incorrectLogin;

    private Repository<User> userRepository = UserRepository.getInstance();
    private DataValidator validator = DataValidatorImpl.getInstance();

    public static Parent root1;
    public static Stage primaryStageUser = new Stage();

    private String login;
    private String password;
    private String repeaterPassword;
    public static User user;

    private void init(){
        incorrectLogin.setText(" ");
        incorrectPassword.setText(" ");
        login = loginField.getText();
        password = passwordField.getText();
        repeaterPassword = repeatedPasswordField.getText();
    }

    private boolean checkInput() {
        if (login.equals("")) {
            incorrectLogin.setText(WrongMessages.INCORRECT_INPUT);
            return false;
        }
        if (userRepository.get(login) != null) {
            incorrectLogin.setText(WrongMessages.LOGIN_IS_EXIST);
            return false;
        }
        if (password.equals("")) {
            incorrectPassword.setText(WrongMessages.INCORRECT_INPUT);
            return false;
        }
        if (!password.equals(repeaterPassword)) {
            incorrectPassword.setText(WrongMessages.PASSWORD_ARE_DIFFERENT);
            return false;
        }
        return true;
    }

    public void registrateMethod(ActionEvent actionEvent) throws IOException {
       init();
        if(checkInput()) {
            root1 = FXMLLoader.load(getClass().getResource(PathManager.USER_PAGE));
            primaryStageUser.setTitle(Titles.USER_TITLE);
            primaryStageUser.setScene(new Scene(root1, 1050, 650));
            primaryStageUser.show();
            saveNewUser();
            StartController.primaryStageRegistration.close();
        }
    }

    private void saveNewUser() {
        user = new User(login, password);
        userRepository.add(user);
        TxtWorker txtWorker = TxtWorkerImpl.getInstance();
        String record = login + " " + password;
        try {
            txtWorker.addDataTxt(PathManager.USER_DATA_FILE, record);
        } catch (WriteDataException e) {
            LOGGER.warn("Запись " + record + " не добавлена в файл " + PathManager.USER_DATA_FILE);
        }

    }

    public void backMethod(ActionEvent actionEvent) {
        StartController.primaryStageRegistration.close();
    }
}