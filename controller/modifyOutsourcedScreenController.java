package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modifyOutsourcedScreenController implements Initializable {

    private static Outsourced oldItem;

    private static int selectedRow;

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
     *passes the selectedItem from mainscreen to current screen
     */
    public static void modifySelectedItem(Part selectedItem, int row) {

        oldItem = (Outsourced) selectedItem;
        selectedRow = row;
        System.out.println(selectedRow);
    }

    /**
     * changes the subclass of the part
     */
    public static void modifySelectedSubclass(Part selectedPart, int row) {
        selectedPart = new Outsourced(selectedPart.getId(),selectedPart.getName(),selectedPart.getPrice(),selectedPart.getStock(),selectedPart.getMin(),selectedPart.getMax()," ");

        oldItem = (Outsourced) selectedPart;
        selectedRow = row;
    }

    /**
     *closes the current screen
     */
    @FXML
    void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    /**
     * changes the screen to modify InHouse
     */
    @FXML
    void inHouseBttn(ActionEvent event) throws IOException {
        cancelBttn(event);

        modifyInHouseScreenController.modifySelectedSubclass(oldItem, selectedRow);

        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyInHouse.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(new Scene(root, 560, 450));
        stage.show();
    }

    @FXML
    void outsourcedBttn(ActionEvent event) {

    }

    /**
     * saves the implemented changes, return app to mainscreen
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
            String companyName = companyTxt.getText();

            //validates stock, max, and min values
            if (stock >= min && stock <= max) {

                Outsourced updatedPart = new Outsourced(oldItem.getId(), name, price, stock, min, max, companyName);

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
        companyTxt.setText(oldItem.getCompanyName());
    }
}
