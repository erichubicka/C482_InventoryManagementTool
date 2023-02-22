package Model;

/**
 *
 * @author Eric Hubicka
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Product class encompasses the method's associated with product objects
 */
public class Product {
    /**
     * initialize the observable list associatedParts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * add a part to the end of the associatedParts list
     * @param part is the part to be added to the associated parts list
     */
    public void addAssociatedPart(Part part){ //return nothing, takes in one parameter called part{
        associatedParts.add(part);
    }

    /**
     * remove the selected the part associated with product from the associatedParts list
     * @param selectedAssociatedPart is the selected part in the associatedParts list to be removed
     * @return true if the remove was successful, false if unsuccessful
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){ //return boolean, takes in one parameter called selectedAssociatedPart
        if (associatedParts.contains(selectedAssociatedPart)){ //if the selected part exists in the associatedParts list
            associatedParts.remove(selectedAssociatedPart); //remove this selected part from the list
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return the list of associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
