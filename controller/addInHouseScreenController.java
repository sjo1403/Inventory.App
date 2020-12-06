package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import model.InHouse;
import model.Inventory;

import java.io.IOException;

public class addInHouseScreenController {

    @FXML
    private ToggleGroup toggleGroup1;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField machineTxt;

    @FXML
    private Button cancelBttn;

    /**
     *closes the stage, returns to main screen
     */
    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();

    }

    /**
     *closes the stage, opens outsourced parts
     */
    @FXML
    void outsourcedBttn(ActionEvent event) throws IOException {
        cancelBttn(event);

        Parent root = FXMLLoader.load(getClass().getResource("/view/addOutsourcedScreen.fxml"));
        Stage newStage = new Stage();
        newStage.setTitle("Add Part");
        newStage.setScene(new Scene(root, 600, 450));
        newStage.show();
     }

     @FXML
     void inHouseBttn(ActionEvent event) {

     }

    /**
     *saves data entered into text fields
     */
    @FXML
    void saveBttn(ActionEvent event) {

        try {

            String name = nameTxt.getText();

            Double price = Double.parseDouble(priceTxt.getText());

            int stock = Integer.parseInt(invTxt.getText());

            int min = Integer.parseInt(minTxt.getText());

            int max = Integer.parseInt(maxTxt.getText());

            int machineID = Integer.parseInt(machineTxt.getText());

            //validates stock, max, min entries
            if (stock >= min && stock <= max) {

                //generates random machineID
                int ID = (int) (1000 + Math.random() * 9000);

                InHouse newPart = new InHouse(ID, name, price, stock, min, max, machineID);

                Inventory.addPart(newPart);
                Inventory.refreshFilteredParts();

                Stage stage = (Stage) cancelBttn.getScene().getWindow();
                stage.close();
            }

            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please enter valid values for inv, max, and min.");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values for each text field!\n" + e);
            alert.showAndWait();
        }

        }
    }

