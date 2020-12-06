package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private static ObservableList<Part> components = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id = 0000;
    private String Name = " ";
    private double Price = 0.00;
    private int stock = 0;
    private int min = 0;
    private int max = 0;

    /**
     *data parameters for Product constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        setId(id);
        setMax(max);
        setMin(min);
        setPartName(name);
        setPrice(price);
        setStock(stock);
        setAssociatedParts(associatedParts);
    }

    /**
     *
     * @return id
     */
    public int getId() {

        return id;
    }

    /**
     *
     * @param id sets id
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     *
     * @return Name
     */
    public String getName() {

        return Name;
    }

    /**
     *sets Name
     * @param Name
     */
    public void setPartName(String Name) {

        this.Name = Name;
    }

    /**
     *
     * @return Price
     */
    public double getPrice() {

        return Price;
    }

    /**
     *
     * @param Price sets price
     */
    public void setPrice(double Price) {

        this.Price = Price;
    }

    /**
     *
     * @return stock
     */
    public int getStock() {

        return stock;
    }

    /**
     *
     * @param stock sets stock
     */
    public void setStock(int stock) {

        this.stock = stock;
    }

    /**
     *
     * @return min
     */
    public int getMin() {

        return min;
    }

    /**
     *
     * @param min sets min
     */
    public void setMin(int min) {

        this.min = min;
    }

    /**
     *
     * @return max
     */
    public int getMax() {

        return max;
    }

    /**
     *
     * @param max sets max
     */
    public void setMax(int max) {

        this.max = max;
    }

    /**
     *adds associated part to product
     * @param selectedItem
     */
    public void addAssociatedPart(Part selectedItem) {

        associatedParts.add(selectedItem);
    }

    /**
     *sets associatedParts
     * @param associatedParts
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {

        this.associatedParts = associatedParts;
    }

    /**
     *
     * @return associatedParts
     */
    public ObservableList<Part> getAssociatedParts() {

        return associatedParts;
    }

    /**
     *adds part to the list of elligible parts
     * @param part
     */
    public static void addComponents(Part part) {

        components.add(part);
    }

    /**
     *removes part from the list of elligible parts
     * @param selectedPart
     */
    public static void removeComponent(Part selectedPart) {

        components.remove(selectedPart);
    }

    /**
     *
     * @param selectedAssociatedPart
     * @return boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){

        return true;
    }

    /**
     *
     * @return components
     */
    public static ObservableList<Part> getAllComponents(){

        return components;
    }
}
