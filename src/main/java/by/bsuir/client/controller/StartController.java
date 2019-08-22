package by.bsuir.client.controller;

import by.bsuir.client.entity.User;
import by.bsuir.client.messages.*;
import by.bsuir.client.path.PathManager;
import by.bsuir.client.repository.Repository;
import by.bsuir.client.repository.impl.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartController {
    public Button sighInButton;
    public Button sighUpButton;
    public TextField loginField;
    public PasswordField passwordField;
    public static User user;

    public Label wrongSighIn;

    public static Parent root1;
    public static Stage primaryStageUser = new Stage();
    public static Stage primaryStageRegistration = new Stage();

    private Repository userRepository = UserRepository.getInstance();

    public void signInMethod(ActionEvent actionEvent) throws IOException {
        wrongSighIn.setText("");
        User user2 = (User) userRepository.get(loginField.getText(), passwordField.getText());
        if(user2 != null){
            user = user2;
            if(Objects.equals(user2.getLogin(), "админ")) {
                root1 = FXMLLoader.load(getClass().getResource(PathManager.ADMIN_PAGE));
                primaryStageUser.setTitle(Titles.ADMIN_TITLE);
                primaryStageUser.setScene(new Scene(root1, 1050, 650));
                primaryStageUser.show();
                loginField.setText("");
                passwordField.setText("");
            } else {
                root1 = FXMLLoader.load(getClass().getResource(PathManager.USER_PAGE));
                primaryStageUser.setTitle(Titles.USER_TITLE);
                primaryStageUser.setScene(new Scene(root1, 1050, 650));
                primaryStageUser.show();
                loginField.setText("");
                passwordField.setText("");
            }

        } else {
            wrongSighIn.setText(WrongMessages.INCORRECT_LOGIN_OR_PASSWORD);
        }
    }

    public void sighUpMethod(ActionEvent actionEvent) throws IOException {
            root1 = FXMLLoader.load(getClass().getResource(PathManager.SIGN_UP_PAGE));
            primaryStageRegistration.setTitle(Titles.SIGN_UP_TITLE);
            primaryStageRegistration.setScene(new Scene(root1, 450, 500));
            primaryStageRegistration.show();
    }
}
