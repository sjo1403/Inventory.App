package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modifyInHouseScreenController implements Initializable {

    private static int selectedRow;

    private static InHouse oldItem;

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
     *passes the selectedItem from mainscreen to current screen
     */
    public static void modifySelectedItem(Part selectedItem, int row) {
        oldItem = (InHouse) selectedItem;
        selectedRow = row;
        System.out.println(selectedRow);
    }

    /**
     * changes the subclass of the part
     */
    public static void modifySelectedSubclass(Part selectedPart, int row) {
        selectedPart = new InHouse(selectedPart.getId(),selectedPart.getName(),selectedPart.getPrice(),selectedPart.getStock(),selectedPart.getMin(),selectedPart.getMax(),0);

        oldItem = (InHouse) selectedPart;
        selectedRow = row;
    }

    /**
     *closes the current screen
     */
    @FXML
    void cancelBttn(ActionEvent event) {
        //closes the stage, returns to main screen
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();

    }

    /**
     * changes the screen to modify OutSourced
     */
    @FXML
    void outsourcedBttn(ActionEvent event) throws IOException {
        //closes the stage, opens outsourced parts
        cancelBttn(event);

        modifyOutsourcedScreenController.modifySelectedSubclass(oldItem, selectedRow);

        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyOutsourced.fxml"));
        Stage newStage = new Stage();
        newStage.setTitle("Add Part");
        newStage.setScene(new Scene(root, 600, 450));
        newStage.show();
    }

    @FXML
    void inHouseBttn(ActionEvent event) {

    }

    /**
     *saves the implemented changes, return app to mainscreen
     */
    @FXML
    void saveBttn(ActionEvent event) {

        try {
            //initializes new InHouse object with values from

            String name = nameTxt.getText();
            Double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int machineID = Integer.parseInt(machineTxt.getText());

            //validates stock, min, and max values
            if (stock >= min && stock <= max) {

                //generates random machineID
                int ID = (int) (1000 + Math.random() * 9000);

                InHouse updatedPart = new InHouse(oldItem.getId(), name, price, stock, min, max, machineID);

                Inventory.updatePart(selectedRow, updatedPart);
                System.out.println(selectedRow);
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

    /**
     *sets text fields to previous values
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameTxt.setText(oldItem.getName());
        invTxt.setText(Integer.toString(oldItem.getStock()));
        priceTxt.setText(Double.toString(oldItem.getPrice()));
        maxTxt.setText(Integer.toString(oldItem.getMax()));
        minTxt.setText(Integer.toString(oldItem.getMin()));
        machineTxt.setText(Integer.toString(oldItem.getMachineID()));
    }
}
