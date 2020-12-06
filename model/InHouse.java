package model;

public class InHouse extends Part {
    private int machineID = 0000;

    /**
     *data parameters for the InHouse constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);
    }

    /**
     *
     * @return machineID
     */
    public int getMachineID() {

        return machineID;
    }

    /**
     *
     * @param machineID set machineID
     */
    public void setMachineID(int machineID) {

        this.machineID = machineID;
    }
}
