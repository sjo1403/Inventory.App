package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;

public class addOutsourcedScreenController {

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
    private TextField companyTxt;

    @FXML
    private Button cancelBttn;

    /**
     *closes the screen, returns to the main screen
     */
    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    /**
     *switches to the "add in house" screen
     */
    @FXML
    void inHouseBttn(ActionEvent event) throws IOException {
        cancelBttn(event);

        Parent root = FXMLLoader.load(getClass().getResource("/view/addInHouseScreen.fxml"));
        Stage newStage = new Stage();
        newStage.setTitle("Add Part");
        newStage.setScene(new Scene(root, 560, 450));
        newStage.show();
    }

    @FXML
    void outsourcedBttn(ActionEvent event) {

    }

    /**
     * saves the newly entered data
     */
    @FXML
    void saveBttn(ActionEvent event) {
        try {
            //initializes new InHouse object with values from text fields

            String name = nameTxt.getText();
            Double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            String companyName = companyTxt.getText();

            //validates stock, max, and min values
            if (stock >= min && stock <= max) {

                //generates random machineID
                int ID = (int) (1000 + Math.random() * 9000);

                Outsourced newPart = new Outsourced(ID, name, price, stock, min, max, companyName);

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
