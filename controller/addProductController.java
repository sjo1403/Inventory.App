/**
 * A runtime error that I corrected in was a null pointer exception.
 * I made the original statement addedParts == null with the intention of making sure the variable was initialized,
 * so it was not pointing at a random location and adding runtime errors to my code.
 *
 * A compatible feature that would extend the functionality of my program would be a "copy parts/ products" button.
 * If a user wanted to create similar parts/products without having to enter all of the data values in again,
 * they could simply copy the base part/product, and modify 1 or 2 specified values to create a new item.
 */

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class addProductController implements Initializable {

    /**
     * Originally, addedParts == null. This however resulted in a null pointer exception when trying to add parts
     * to my temporary container (addedParts). To fix this, I initialized added parts to
     * FXCollections.observableArrayList(). After doing this, I didn't receive the null pointer exception
     * runtime error.
     */

    private ObservableList<Part> addedParts = FXCollections.observableArrayList();

    private static ObservableList<Part> elligibleParts = Product.getAllComponents();

    @FXML
    private TextField IDtxt;

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
    private Button cancelBttn;

    @FXML
    private TextField searchTxt;

    @FXML
    private TableView<Part> upperTable;

    @FXML
    private TableColumn<Part, Integer> idCol;

    @FXML
    private TableColumn<Part, String> nameCol;

    @FXML
    private TableColumn<Part, Integer> invCol;

    @FXML
    private TableColumn<Part, Double> priceCol;

    @FXML
    private TableView<Part> lowerTable;

    @FXML
    private TableColumn<?, ?> lowIDCol;

    @FXML
    private TableColumn<?, ?> lowNameCol;

    @FXML
    private TableColumn<?, ?> lowInvCol;

    @FXML
    private TableColumn<?, ?> lowPriceCol;

    /**
     *closes the screen and returns to the main screen
     */
    @FXML
    void cancelBttn(ActionEvent event) {
        if (addedParts != null) {
            addedParts.clear();
        }

        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    /**
     *adds selected part to lower table
     */
    @FXML
    void addBttn(ActionEvent event) {
        Part selectedItem = upperTable.getSelectionModel().getSelectedItem();
        addedParts.add(selectedItem);
    }

    /**
     * saves data entered into the text fields, closes the screen, and returns to the main screen
     */
    @FXML
    void saveBttn(ActionEvent event) {

        try {
            //generates random ID
            int ID = (int) (1000 + Math.random() * 9000);

            String name = nameTxt.getText();
            Double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());

            //validates stock, min, and max entries
            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please enter valid values for inv, max, and min.");
                alert.showAndWait();
            }

            else if (addedParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("A product must consist of at least one part.");
                alert.showAndWait();
            }

            else {

                Product currentProduct = new Product(ID,name,price,stock,min,max);

                for (Part part : addedParts) {

                    currentProduct.addAssociatedPart(part);
                }

                Inventory.addProduct(currentProduct);
                Inventory.refreshFilteredProducts();

                cancelBttn(event);
            }

        } catch (NumberFormatException e    ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values for each text field!\n" + e);
            alert.showAndWait();
        }

    }


    /**
     *removes the associated part from the product
     */
    @FXML
    void deleteBttn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("This will remove the selected part. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedItem = lowerTable.getSelectionModel().getSelectedItem();
            lowerTable.getItems().remove(selectedItem);
        }
    }

    /**
     * search and partial search based on part name and ID
     */
    @FXML
    void searchProductBttn(ActionEvent event) {
        if (Inventory.searchParts(searchTxt.getText())) {

            upperTable.setItems(Inventory.getFilteredParts());
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }

    /**
     *displays the elligible and associated parts in table views
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //populates upper table
        upperTable.setItems(elligibleParts);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //populates lower table
        lowerTable.setItems(addedParts);

        lowIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        lowNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lowPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        lowInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
