package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     *adds part to the allParts observable list
     * @param newPart
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
        Product.addComponents(newPart);
    }

    /**
     *adds product to the allProducts observable list
     * @param newProduct
     */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);
    }

    /**
     *
     * @param partID
     * @return allParts
     */
    public static Part lookupPart(int partID){

        return allParts.get(0);
    }

    /**
     *
     * @param ProductID
     * @return allProducts
     */
    public static Product lookupProduct(int ProductID){

        return allProducts.get(ProductID);
    }

    /**
     *
     * @param partName
     * @return allParts
     */
    public static Part lookupPart(String partName){

        return allParts.get(0);
    }

    /**
     *
     * @param productName
     * @return allProducts
     */
    public static Product lookupProduct(String productName){

        return allProducts.get(0);
    }

    /**
     *saves the updated part to allParts
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){

        allParts.set(index, selectedPart);

    }

    /**
     *saves the updated product to allProducts
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct (int index, Product selectedProduct){

        allProducts.set(index,selectedProduct);
    }

    /**
     *deletes the selected part
     * @param selectedPart
     * @return true
     */
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        Product.removeComponent(selectedPart);

        return true;
    }

    /**
     *deletes the selected product
     * @param selectedProduct
     * @return boolean
     */
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);

        return true;
    }

    /**
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    /**
     *
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /**
     *returns filteredParts, used for parts search
     * @return filteredParts
     */
    public static ObservableList<Part> getFilteredParts() {

        return filteredParts;
    }

    /**
     * updates the filteredParts list when new part is added
     */
    public static void refreshFilteredParts() {

        filteredParts.setAll(Inventory.getAllParts());
    }

    /**
     *part search based on ID and name
     * @param searchText
     * @return boolean
     */
    public static boolean searchParts(String searchText) {
        String searchName = searchText.toLowerCase();

        if (searchName.length() > 0) {
            filteredParts.clear();

            //search partName
            for (Part part : Inventory.getAllParts()) {
                String name = part.getName().toLowerCase();

                if (name.contains(searchName)) {
                    filteredParts.add(part);
                }
            }

            if (filteredParts.size() > 0) {
                return true;
            }

            //search partID
            try {
                int searchID = Integer.parseInt(searchText);

                for (Part part : Inventory.getAllParts()) {
                    int ID = part.getId();

                    if (ID == searchID) {
                        filteredParts.add(part);
                        return true;
                    }
                }

            } catch (NumberFormatException e) {

            }


        }

        else {
            filteredParts.setAll(Inventory.getAllParts());
            return true;
        }

        return false;
    }

    /**
     * updates filteredProducts list when new product is added
     */
    public static void refreshFilteredProducts() {

        filteredProducts.setAll(Inventory.getAllProducts());
    }

    /**
     * returns filtered products for search
     * @return
     */
    public static ObservableList<Product> getFilteredProducts() {

        return filteredProducts;
    }

    /**
     *product search based on name and ID
     * @param searchText
     * @return boolean
     */
    public static boolean searchProducts(String searchText) {
        if (searchText.length() > 0) {

            filteredProducts.clear();

            String searchName = searchText.toLowerCase();

            for (Product product : Inventory.getAllProducts()) {
                String name = product.getName();

                if (name.contains(searchName)) {
                    filteredProducts.add(product);
                }
            }

            if (filteredProducts.size() > 0) {
                return true;
            }

            try {
                int searchID = Integer.parseInt(searchText);

                for (Product product : Inventory.getAllProducts()) {
                    int ID = product.getId();

                    if (ID == searchID) {
                        filteredProducts.add(product);
                        return true;
                    }
                }
            } catch (NumberFormatException e) {

            }

        }

        else {
            filteredProducts.setAll(Inventory.getAllProducts());
            return true;
        }

        return false;
    }

}
