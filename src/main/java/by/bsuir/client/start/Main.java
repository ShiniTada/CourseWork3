package by.bsuir.client.start;

import by.bsuir.client.creator.Creator;
import by.bsuir.client.messages.Titles;
import by.bsuir.client.path.PathManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Creator creator = Creator.getInstance();
        creator.createListUsersFromFile(PathManager.USER_DATA_FILE);
        creator.createListTrainFlightFromFile(PathManager.TRAIN_FLIGHT_FILE);
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(PathManager.START_PAGE));
        primaryStage.setTitle(Titles.START_TITLE);
        primaryStage.setScene(new Scene(root, 450, 500));
//        Scene scene = new Scene(root, 450, 500);
//        scene.getStylesheets().addAll(this.getClass().getResource("../../style/style.css").toExternalForm());
//        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
