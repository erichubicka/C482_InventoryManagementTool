package Controller;

/**
 *
 * @author Eric Hubicka
 */

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.lookupPart;

/**
 * This class will utilize the AddProduct form which creates a new instance of a product to be added into the products inventory
 */
public class AddProduct implements Initializable {
    /**
     * Product ID textfield
     */
    public TextField idTextField;
    /**
     * Product name textfield
     */
    public TextField nameTextField;
    /**
     * Product inventory textfield
     */
    public TextField invTextField;
    /**
     * Product price textfield
     */
    public TextField priceTextField;
    /**
     * Product max textfield
     */
    public TextField maxTextField;
    /**
     * Product min textfield
     */
    public TextField minTextField;
    /**
     * Tableview for the top parts table
     */
    public TableView partsTable;
    /**
     * Part ID column in the top parts table
     */
    public TableColumn partIDCol;
    /**
     * Part name column in the top parts table
     */
    public TableColumn partNameCol;
    /**
     * Part inventory column in the top parts table
     */
    public TableColumn partInventoryCol;
    /**
     * Part price column in the top parts table
     */
    public TableColumn partPriceCol;
    /**
     * Search bar to search parts in the top parts table
     */
    public TextField partSearchTextField;
    /**
     * Error label for the top parts table
     */
    public Label partErrorLabel;
    /**
     * Tableview for the bottom associated parts table
     */
    public TableView productPartsTable;
    /**
     * Part ID column in the bottom associated parts table
     */
    public TableColumn productPartIDCol;
    /**
     * Part name column in the bottom associated parts table
     */
    public TableColumn productPartNameCol;
    /**
     * Part inventory column in the bottom associated parts table
     */
    public TableColumn productPartInventoryCol;
    /**
     * Part price column in the bottom associated parts table
     */
    public TableColumn productPartPriceCol;
    /**
     * Separate list for the bottom tableview, parts associated with the product
     */
    private ObservableList<Part> productParts = FXCollections.observableArrayList();

    /**
     * Used to initialize the controller with any code that needs to be implemented when the form is accessed.
     * In this controller it initializes the inventory with any associated parts.
     * Both tables columns are initialized to accept specific data.
     * @param url passes the resources associated with the FXML file
     * @param resourceBundle manages location specific resources (strings, various data-types, etc)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this table is a copy of the parts table from the mainscreen
        partsTable.setItems(Inventory.getAllParts()); //partsTable tableview will be able to work with allParts ObservableList
        //let columns of partsTable tableview know where they will be getting their data from
        //these are like calling a for loop on the getter's of the objects and populating them in the tableview
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));//get id of the part object, populate the cell in the id column of the tableview
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));//get id of the part object, populate the cell in the id column of the tableview
        productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPartsTable.setItems(productParts);
    }

    /**
     * Adds an associated part to the product.
     * Retrieves the selected item from the top tableview and adds is to the bottom tableview.
     * @param actionEvent when the Add button is pressed
     */
    public void onAddPartToProduct(ActionEvent actionEvent) {
        Part addPartToProduct = (Part) partsTable.getSelectionModel().getSelectedItem(); //take selection in top partsTable, and assign it to a Part variable addPartToProduct
        if (addPartToProduct != null){ //if a part was selected from the top table
            productParts.add(addPartToProduct); //take the selected part from the top table and add it to the productParts list (not the actual table but the list)
            productPartsTable.setItems(productParts); //set the productParts list to the bottom parts table
        } else { //alert if no part was selected before the add button was pressed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Part Error");
            alert.setHeaderText("Add Part Error");
            alert.setContentText("No Part Selected to be Added.");
            alert.showAndWait();
        }
    }

    /**
     * Removes an associated part from the product.
     * Retrieves the selected item from the bottom tableview and removes it from the associated parts list.
     * @param actionEvent when the Remove Associated Part button is pressed
     */
    public void onRemovePartFromProduct(ActionEvent actionEvent) {
        Part removePartFromProduct = (Part) productPartsTable.getSelectionModel().getSelectedItem(); //take selection in bottom productPartsTable, and assign it to a Part variable removePartFromProduct
        if (removePartFromProduct != null) { //if a part was selected from the bottom table
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //confirmation box to ensure the user really wants to remove the associated part
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Part Deletion Confirmation");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (((Optional<?>) result).get() == ButtonType.OK){ //if the user confirms to remove the associated part
                productParts.remove(removePartFromProduct); //remove the selected associated part from the productParts list
                productPartsTable.setItems(productParts); //update the bottom table to reflect the current records in the productParts list
            }
        } else { //alert if no part was selected before the remove button was pressed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Remove Part Error");
            alert.setHeaderText("Remove Part Error");
            alert.setContentText("No Part Selected to be Removed.");
            alert.showAndWait();
        }
    }

    /**
     * Search the top part tableview to filter part records based on the searchbar text input
     * @param keyEvent when a keyword phrase is input into the searchbar
     */
    public void onPartSearched(KeyEvent keyEvent){
        String partName = partSearchTextField.getText(); //assign search textfield input to variable partName
        ObservableList<Part> parts = lookupPart(partName); //return matched parts and assign them to a parts list based on a string input
        if (parts.size() == 0){ //if no parts were matched using the string input
            try {
                int partId = Integer.parseInt(partName); //convert the string input to an integer
                Part part = lookupPart(partId); //lookup a part match based on an integer ID
                if (part != null) //if part(s) are matched
                    parts.add(part); //add the part to he parts list
            }
            catch (NumberFormatException e){ //do nothing if a number format exception occurs
                //ignore
            }}
        partsTable.setItems(parts);
        //display error label if no parts detected in the table
        if (parts.size() == 0){
            partErrorLabel.setText("Search Returned No Results");
        } else{
            partErrorLabel.setText(null);
        }
    }

    /**
     * If the user cancels the creation of a new product, user is sent back to the mainscreen
     * @param actionEvent when the cancel button is selected
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file
     */
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        //keeps the stage and changes the screen from AddProduct screen to mainScreen
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow(); //get stage from action event's Node (button)
        Scene scene = new Scene(root,1050,425); //create new scene
        stage.setTitle("Main Scene"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    /**
     * Compile data input from the form to create a new product.
     * This new product will be added to the product Inventory.
     * User is redirected back to the MainScreen view.
     * Different alerts are displayed for the form not being filled out correctly.
     * @param actionEvent triggered when the save button is selected
     * @throws IOException when an IO error occurs
     */
    public void createNewProduct(ActionEvent actionEvent) throws IOException { //create new InHouse/Outsourced part object and add it to the mainscreen tableview
        int stock = 0;
        int min;
        int max = 0;
        double price;
        int id = Inventory.autoGenerateProductId(); //auto-generated id from the autoGeneratePartID method in the Inventory class
        String name = nameTextField.getText(); //retrieve string value from nameTextField, assign it to name variable
        if (name.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Input Error");
            alert.setHeaderText("Form Input Error");
            alert.setContentText("Name field cannot be blank.");
            alert.showAndWait();
        }else {
            try {
                stock = Integer.parseInt(invTextField.getText()); //retrieve value from invTextField, convert string to integer, assign it to stock variable
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Text Input Error");
                alert.setHeaderText("Text Input Error");
                alert.setContentText("Inv field empty or is not of type int.");
                alert.showAndWait();
            }
            try {
                max = Integer.parseInt(maxTextField.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Text Input Error");
                alert.setHeaderText("Text Input Error");
                alert.setContentText("Max field empty or is not of type int.");
                alert.showAndWait();
            }
            min = max + 1; //stops the program from saving the product if the min value is empty
            try {
                min = Integer.parseInt(minTextField.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Text Input Error");
                alert.setHeaderText("Text Input Error");
                alert.setContentText("Min field empty or is not of type int.");
                alert.showAndWait();
            }
            try {
                price = Double.parseDouble(priceTextField.getText()); //retrieve value from priceTextField, convert string to double, assign it to stock variable
                if (max > stock && stock > min) {
                    //take parts from bottom list and add them to the list of the newly created product and then add the product to the product tableview on the mainscreen
                    Product newProduct = new Product(id, name, price, stock, min, max); //assign a new instance of a Product to the variable newProduct --< 1) assign newProduct the basic values
                    newProduct.getAllAssociatedParts().addAll(productParts); //add all the parts in the productParts list to the instance of newProduct --> 2) assign newProduct the associated parts
                    Inventory.addProduct(newProduct); //add this newProduct to the product inventory list allProducts (all allProducts is used in the products tableview on the mainscreen) --> 3) send newProduct to the mainscreen list

                    // upon selecting save button, change from AddProduct screen to the MainScreen
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1050, 425);
                    stage.setTitle("Main Scene");
                    stage.setScene(scene);
                    stage.show();
                } else { //alert if the stock value is not between the max and min values, or if the max value is less than the min value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Form Input Error");
                    alert.setHeaderText("Form Input Error");
                    alert.setContentText("Please Ensure Max > Inv > Min.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Text Input Error");
                alert.setHeaderText("Text Input Error");
                alert.setContentText("Price field empty or is not of type double.");
                alert.showAndWait();
            }
        }
    }
}
