package Model;

/**
 *
 * @author Eric Hubicka
 */

/**
 * InHouse is a class that inherits from the Part class
 */
public class InHouse extends Part{
    /**
     * Declare the machineId variable
     */
    private int machineId;

    /**
     * Constructor to initialize a new InHouse part
     * @param id for the part
     * @param name for the part
     * @param price for the part
     * @param stock for the part
     * @param min for the part
     * @param max for the part
     * @param machineId for the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max); //these are inherited from Part.java
        this.machineId = machineId; //machineId is not a part of Part.java so it needs to be initialized here
    }

    /**
     * @return the machineID
     */
    public int getMachineID() {
        return machineId;
    }

    /**
     * @param machineId sets the machineId
     */
    public void setMachineID(int machineId) {
        this.machineId = machineId;
    }
}
