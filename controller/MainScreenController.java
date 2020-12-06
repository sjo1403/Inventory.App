package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainScreenController implements Initializable {

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> prodIDCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Integer> prodInvCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    @FXML
    private TextField searchPartTxt;

    @FXML
    private TextField searchProdTxt;

    /**
     *initialize controller class
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb){ //displays the tableviews when the app is opened
        //populates the parts table
        partsTable.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //populates the products table
        productsTable.setItems(Inventory.getAllProducts());

        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * opens the add part screen
     */
    @FXML
    void addPartBttn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addInHouseScreen.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Part");
        stage.setScene(new Scene(root, 560, 450));
        stage.show();
    }

    /**
     *opens the add product screen
     */
    @FXML
    void addProductBttn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root, 925, 587));
        stage.show();
    }

    /**
     *deletes the selected part from allParts
     */
    @FXML
    void deletePartBttn(ActionEvent event) {
        Part selectedItem = partsTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select the part you wish to delete.");
            alert.showAndWait();
            return;
        }

        for (Product product : Inventory.getAllProducts()) {

            if (product.getAssociatedParts().contains(selectedItem)) {

                Alert alert = new Alert(Alert.AlertType.WARNING, selectedItem.getName() + " is a component of " + product.getName() + " and cannot be deleted.");
                alert.showAndWait();
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("This will delete the selected part. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedItem);
        }
    }

    /**
     *deletes the selected product from allProducts
     */
    @FXML
    void deleteProductBttn(ActionEvent event) {
        Product selectedItem = productsTable.getSelectionModel().getSelectedItem();

        if (Inventory.getAllProducts() == null || Inventory.getAllProducts().isEmpty()) {
            return;
        }

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select the product you wish to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("This will delete the selected product. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(selectedItem);
        }
    }

    /**
     * closes the application
     */
    @FXML
    void exitBttn(ActionEvent event) {

        System.exit(0);
    }

    /**
     *opens new screen to modify the selected part
     */
    @FXML
    void modifyPartBttn(ActionEvent event) throws IOException {

        if (Inventory.getAllProducts()== null || Inventory.getAllParts().isEmpty()) {
            return;
        }

        //gets current selection
        Part selectedItem = partsTable.getSelectionModel().getSelectedItem();
        int row = partsTable.getSelectionModel().selectedIndexProperty().get();

        //open "modify" screen with InHouse view
        if (selectedItem instanceof InHouse) {
            //communicates row index and selected part to the modifyInHouseScreenController
            modifyInHouseScreenController.modifySelectedItem((InHouse) selectedItem, row);

            Parent root = FXMLLoader.load(getClass().getResource("/view/modifyInHouse.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(root, 560, 450));
            stage.show();
        }

        else if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a part to modify.");
            alert.showAndWait();
            return;
        }

        //open "modify" screen with outsourced view
        else {
            //communicates row index and selected part to the modifyInHouseScreenController
            modifyOutsourcedScreenController.modifySelectedItem((Outsourced) selectedItem, row);

            Parent root = FXMLLoader.load(getClass().getResource("/view/modifyOutsourced.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("Add Part");
            newStage.setScene(new Scene(root, 600, 450));
            newStage.show();
        }
    }

    /**
     *opens new screen to modify the selected product
     */
    @FXML
    void modifyProductBttn(ActionEvent event) throws IOException {

        if (Inventory.getAllProducts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please add a product before modifying.");
            alert.showAndWait();
            return;
        }

        //gets current selection
        Product selectedItem = productsTable.getSelectionModel().getSelectedItem();
        int selectedRow = productsTable.getSelectionModel().selectedIndexProperty().get();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a product to modify.");
            alert.showAndWait();
            return;
        }

        modifyProductController.passRow(selectedRow);

        //opens "modifyProduct" screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProduct.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root, 925, 587));
        stage.show();
    }

    /**
     * search and partial search for part based on name and ID
     */
    @FXML
    void searchPartBttn(ActionEvent event) {
        if (Inventory.searchParts(searchPartTxt.getText())) {

            partsTable.setItems(Inventory.getFilteredParts());
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }

    /**
     *search and partial search for product based on name and ID
     */
    @FXML
    void searchProductBttn(ActionEvent event) {
        if (Inventory.searchProducts(searchProdTxt.getText())) {

            productsTable.setItems(Inventory.getFilteredProducts());
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found.");
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }
    }

}
