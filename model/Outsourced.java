package model;

public class Outsourced extends Part {
    private String companyName = " ";

    /**
     *data parameters for Outsourced constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    }

    /**
     *
     * @return companyName
     */
    public String getCompanyName() {

        return companyName;
    }

    /**
     *
     * @param companyName set companyName
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}