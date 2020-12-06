package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyProductController implements Initializable {

    private static int row;

    private static Product oldProduct;

    private ObservableList<Part> addedParts = FXCollections.observableArrayList();

    private static ObservableList<Part> elligibleParts = Product.getAllComponents();

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
    private TableColumn<Part, Integer> lowIDCol;

    @FXML
    private TableColumn<Part, String> lowNameCol;

    @FXML
    private TableColumn<Part, Integer> lowInvCol;

    @FXML
    private TableColumn<Part, Double> lowPriceCol;

    /**
     *passes the row # and selected item from the existing product
     */
    public static void passRow(int selectedRow) {
        row = selectedRow;
        oldProduct = Inventory.getAllProducts().get(row);
    }

    /**
     * adds parts to the specified product and displays them in the lower table
     */
    @FXML
    void addBttn(ActionEvent event) {
        //adds selected part to lower table
        Part selectedItem = upperTable.getSelectionModel().getSelectedItem();
        oldProduct.addAssociatedPart(selectedItem);
        addedParts = oldProduct.getAssociatedParts();

    }

    /**
     * closes the screen and returns to the main screen
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
     * removes the associated part from the product
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
     * saves the changes made to the product, closes the screen, and returns to the main screen
     */
    @FXML
    void saveBttn(ActionEvent event) {
        try {
            String name = nameTxt.getText();
            Double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());

            //validates stock, min, and max
            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please enter valid values for inv, max, and min.");
                alert.showAndWait();
            }

            else if (oldProduct.getAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("A product must consist of at least one part.");
                alert.showAndWait();
            }

            else {
                oldProduct.setPartName(name);
                oldProduct.setPrice(price);
                oldProduct.setStock(stock);
                oldProduct.setMin(min);
                oldProduct.setMax(max);
                oldProduct.setAssociatedParts(oldProduct.getAssociatedParts());

                Inventory.updateProduct(row, oldProduct);
                Inventory.refreshFilteredProducts();

                cancelBttn(event);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values for each text field!\n" + e);
            alert.showAndWait();
        }

    }

    /**
     * search and partial search for part names, search for part IDs
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
     * displays the data in a table view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //sets the text fields with the original product data
        nameTxt.setText(oldProduct.getName());
        invTxt.setText(Integer.toString(oldProduct.getStock()));
        priceTxt.setText(Double.toString(oldProduct.getPrice()));
        maxTxt.setText(Integer.toString(oldProduct.getMax()));
        minTxt.setText(Integer.toString(oldProduct.getMin()));

        //populates upper table
        upperTable.setItems(elligibleParts);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //populates lower table
        lowerTable.setItems(oldProduct.getAssociatedParts());

        lowIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        lowNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lowPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        lowInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

}
