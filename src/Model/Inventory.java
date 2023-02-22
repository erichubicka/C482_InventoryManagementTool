package Model;

/**
 *
 * @author Eric Hubicka
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class encompasses the method's associated with the part and product objects
 */
public class Inventory {

//        -----------------------PART-----------------------

    /**
     * initialize the observable list allParts to be used for storing the part inventory data
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * add a new Part to the end of the ObservableList allParts
     * @param newPart is the new part to be created
     */
    public static void addPart(Part newPart){ //return nothing, takes in one parameter called newPart{
        allParts.add(newPart);
    }

    /**
     * search for a part in the part tableview based on its partID
     * @param partId is the ID to be used for the search
     * @return the part with the matched part ID
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts(); //get a list of all the parts in the inventory (for loop will walk through this list)
        for (int i=0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (part.getId() == partId) {
                return part;}}
        return null;
    }

    /**
     * search for a part in the part tableview based on its partName
     * @param partName is the name to be used for the search
     * @return the part(s) with the matched part names
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchedParts = FXCollections.observableArrayList(); //empty list of that will contain matched parts
        ObservableList<Part> allParts = Inventory.getAllParts(); //get a list of all the parts in the inventory (for loop will walk through this list)
        for (Part part:allParts){ //iterate through the allParts list
            if(part.getName().contains(partName)){ //if any matches found from the entered string partName
                matchedParts.add(part);}} //add the part to the matchedParts list
        return matchedParts;
    }

    /**
     * Select an existing part, update it, and add it back into the ObservableList allParts.
     * Used in the ModifyPart controller.
     * @param id is the part id
     * @param selectedPart is the selected part to be updated
     */
    public static void updatePart(int id, Part selectedPart){
        int index = -1; //initialize the index variable
        //loop through all the parts in the inventory to find a partId match then update the matched part
        for(Part part : Inventory.getAllParts()){
            index++;
            if (part.getId() == id)
            {
                Inventory.allParts.set(index, selectedPart);
        }}}

    /**
     * delete part method to remove a selected part from the allParts list
     * @param selectedPart is the part that has been selected on the mainscreen Part tableview
     * @return a boolean, true if the remove was successful, false if unsuccessful
     */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    };

    /**
     * used to obtain the full list of parts
     * @return the list of allParts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * variable for the initial part id to be automatically assigned when creating a new part
     */
    public static int p1PartId = 2;

    /**
     * method used in the AddPart controller to automatically generate a part id
     * @return the automatically generated partid
     */
    public static int autoGeneratePartId(){
        return ++p1PartId;
    }

//    -----------------------PRODUCT-----------------------

    /**
     * initialize the observable list allProducts to be used for storing the product inventory data
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * add a new Product to the end of the ObservableList allProducts
     * @param newProduct is the new product to be created
     */
    public static void addProduct(Product newProduct){ //return nothing, takes in one parameter called newProduct{
        allProducts.add(newProduct);
    }

    /**
     * search for a product in the product tableview based on its productID
     * @param productId is the ID to be used for the search
     * @return the product with the matched product ID
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts(); //get a list of all the products in the inventory (for loop will walk through this list)
        for (int i=0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if (product.getId() == productId) {
                return product;}}
        return null;
    }

    /**
     * search for a product in the product tableview based on its productName
     * @param productName is the name to be used for the search
     * @return the product(s) with the matched product names
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchedProducts = FXCollections.observableArrayList(); //empty list of that will contain matched products
        ObservableList<Product> allProducts = Inventory.getAllProducts(); //get a list of all the products in the inventory (for loop will walk through this list)
        for (Product product:allProducts){ //iterate through the allProducts list
            if(product.getName().contains(productName)){ //if any matches found from the entered string partName
                matchedProducts.add(product);}} //add the part to the matchedProducts list
        return matchedProducts;
    }

    /**
     * Select an existing product, update it, and add it back into the ObservableList allProducts.
     * Used in the ModifyProduct controller.
     * @param id is the product id
     * @param newProduct is the selected product to be updated
     */
    public static void updateProduct(int id, Product newProduct){
        int index = -1;

        for(Product product : Inventory.getAllProducts()){
            index++;
            if (product.getId() == id)
            {
                Inventory.allProducts.set(index, newProduct);
            }}}

    /**
     * delete product method to remove a selected product from the allProducts list
     * @param selectedProduct is the product that has been selected on the mainscreen Product tableview
     * @return a boolean, true if the remove was successful, false if unsuccessful
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * used to obtain the full list of products
     * @return the list of allProduts
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * variable for the initial product id to be automatically assigned when creating a new product
     */
    public static int p1ProductId = 1002;
    /**
     * method used in the AddProduct controller to automatically generate a product id
     * @return the automatically generated productid
     */
    public static int autoGenerateProductId(){
        return ++p1ProductId;
    }

}
