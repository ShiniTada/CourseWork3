package by.bsuir.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GoodOtchetController {
    public Label message;
    public Label pathField;

    @FXML
    private void initialize(){
        pathField.setText(KondorseController.otchetPath);
    }
}
