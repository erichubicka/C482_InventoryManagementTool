package Model;

/**
 *
 * @author Eric Hubicka
 */

/**
 * Outsourced is a class that inherits from the Part class
 */
public class Outsourced extends Part{
    /**
     * Declare the companyName variable
     */
    private String companyName;

    /**
     * Constructor to initialize a new Outsourced part
     * @param id for the part
     * @param name for the part
     * @param price for the part
     * @param stock for the part
     * @param min for the part
     * @param max for the part
     * @param companyName for the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName sets the companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
