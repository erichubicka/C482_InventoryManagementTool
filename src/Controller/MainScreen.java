package Controller;

/**
 *
 * @author Eric Hubicka
 */

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import static Model.Inventory.lookupProduct;

/**
 * This class will utilize the MainScreen form which acts as the homepage for the entire application.
 * From here, users can access AddPart, AddProduct, ModifyPart, and ModifyProduct screens.
 * Users may also delete products/parts from the tableviews.
 */
public class MainScreen implements Initializable {
    /**
     * Tableview for the left parts table
     */
    public TableView partsTable;
    /**
     * Tableview for the right products table
     */
    public TableView productTable;
    /**
     * Part ID column in the left parts table
     */
    public TableColumn partIDCol;
    /**
     * Part name column in the left parts table
     */
    public TableColumn partNameCol;
    /**
     * Part inventory column in the left parts table
     */
    public TableColumn partInventoryCol;
    /**
     * Part price column in the left parts table
     */
    public TableColumn partPriceCol;
    /**
     * Product ID column in the right products table
     */
    public TableColumn productIDCol;
    /**
     * Product name column in the right products table
     */
    public TableColumn productNameCol;
    /**
     * Product inventory column in the right products table
     */
    public TableColumn productInventoryCol;
    /**
     * Product price column in the right products table
     */
    public TableColumn productPriceCol;
    /**
     * Search bar to search parts in the left parts table
     */
    public TextField partSearchTextField;
    /**
     * Search bar to search products in the right products table
     */
    public TextField productSearchTextField;
    /**
     * Error label for the left parts table
     */
    public Label partErrorLabel;
    /**
     * Error label for the right products table
     */
    public Label productErrorLabel;

    /**
     * Used to initialize the controller with any code that needs to be implemented when this screen is accessed.
     * In this controller it initializes the inventory with any associated parts.
     * Both tables columns are initialized to accept specific data.
     * @param url passes the resources associated with the FXML file
     * @param resourceBundle manages location specific resources (strings, various data-types, etc)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts()); //partsTable tableview will be able to work with allParts ObservableList
        //let columns of partsTable tableview know where they will be getting their data from
        //these are like calling a for loop on the getter's of the objects and populating them in the tableview
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));//get id of the part object, populate the cell in the id column of the tableview
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * When exit button pressed, exit the application main screen window
     * @param event when the exit button is pressed
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

//        -----------------------PART-----------------------

    /**
     * When the add button is selected, send the user to the AddPart screen
     * @param actionEvent when the add button is selected
     * @throws IOException  most likely if FXMLLoader.load() can't find the .fxml file
     */
    public void toAddPartScreen(ActionEvent actionEvent) throws IOException {
        //keeps the stage and changes the screen from MainScreen to AddPart Screen
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml")); //load anchor pane of next screen
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
        Scene scene = new Scene(root, 525, 425); //create new scene
        stage.setTitle("Add Part Scene"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    /**
     * When the modify button is selected, send the user to the ModifyPart screen.
     * Note that an error will occur if no part is selected in the part tableview prior to pressing the modify button.
     * RUNTIME ERROR: A NullPointerException was produced in the Run console.
     * This exception would occur when the modify button was selected without first selected a part to modify.
     * Perhaps this was missed by myself in the project requirements but I didn't see anything about needing to handle this.
     * It was annoying so I add the catch statement to add an alert when this exception occurs.
     * That way the user can know immediately why the program failed to display the ModifyPart screen.
     * @param actionEvent when the modify button is selected
     * @throws IOException  most likely if FXMLLoader.load() can't find the .fxml file
     */
    public void toModifyPartScreen(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyPart.fxml"));
            loader.load();

            ModifyPart modifyPart = loader.getController();
            modifyPart.sendPart((Part) partsTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e){ //alert if no part is selected upon pressing the modify button
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Error");
            alert.setHeaderText("Modify Error");
            alert.setContentText("Please Select a Product Before Modifying.");
            alert.showAndWait();
        }
    }

    /**
     * Search the left part tableview to filter part records based on the searchbar text input
     * @param keyEvent when a keyword phrase is input into the searchbar
     */
    public void onPartSearched(KeyEvent keyEvent) {
        String partName = partSearchTextField.getText(); //assign textfield input to variable partName
        ObservableList<Part> parts = lookupPart(partName); //return matched parts (or error) and assign it to a parts list
        if (parts.size() == 0) {
            try {
                int partId = Integer.parseInt(partName);
                Part part = lookupPart(partId);
                if (part != null)
                    parts.add(part);
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        partsTable.setItems(parts);
        //display error label if no parts detected in the table
        if (parts.size() == 0) {
            partErrorLabel.setText("Search Returned No Results");
        } else {
            partErrorLabel.setText(null);
        }
    }

    /**
     * When the delete button associated with the part tableview is selected, remove that part from the tableview
     * @param actionEvent when the delete button is selected
     */
    public void onPartDeleted(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem(); //get the selected part and assign it to variable selectedPart
        if (selectedPart != null) { //if a part was indeed selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //ask the user to confirm if they would like to delete the part
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Part Deletion Confirmation");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (((Optional<?>) result).get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            } else {
                //ignore
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR); //alert if no part was selected before pressing the delete button
            alert.setTitle("Delete Error");
            alert.setHeaderText("Delete Error");
            alert.setContentText("Please Select a Part Before Deleting.");
            alert.showAndWait();
        }
    }

//        -----------------------PRODUCT-----------------------

    /**
     * When the add button is selected, send the user to the AddProduct screen
     * @param actionEvent when the add button is selected
     * @throws IOException  most likely if FXMLLoader.load() can't find the .fxml file
     */
    public void toAddProductScreen(ActionEvent actionEvent) throws IOException {
        //keeps the stage and changes the screen from MainScreen to Product Screen
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml")); //load anchor pane of next screen
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
        Scene scene = new Scene(root, 1100, 700); //create new scene
        stage.setTitle("Add Product Scene"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    /**
     * When the modify button is selected, send the user to the ModifyProduct screen.
     * Note that an error will occur if no product is selected in the product tableview prior to pressing the modify button.
     * @param actionEvent when the modify button is selected
     * @throws IOException  most likely if FXMLLoader.load() can't find the .fxml file
     */
    public void toModifyProductScreen(ActionEvent actionEvent) throws IOException {
        try{
            Product product = (Product) productTable.getSelectionModel().getSelectedItem(); //grab the selected product record from the mainscreen productTable

            //load the ModifyProduct.fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProduct.fxml"));
            loader.load();

            ModifyProduct controller = loader.getController(); //get the controller of the ModifyProduct screen
            controller.sendProduct(product); //send the selected instance of a product to the ModifyProduct controller

            //display the ModifyProduct form
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e){ //alert if no product is selected upon pressing the modify button
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Error");
            alert.setHeaderText("Modify Error");
            alert.setContentText("Please Select a Product Before Modifying.");
            alert.showAndWait();
        }
    }

    /**
     * Search the right products tableview to filter product records based on the searchbar text input
     * @param keyEvent when a keyword phrase is input into the searchbar
     */
    public void onProductSearched(KeyEvent keyEvent) {
        String productName = productSearchTextField.getText();
        ObservableList<Product> products = lookupProduct(productName);
        if (products.size() == 0) {
            try {
                int productId = Integer.parseInt(productName);
                Product product = lookupProduct(productId);
                if (product != null)
                    products.add(product);
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        productTable.setItems(products);
        //display error label if no products detected in the table
        if (products.size() == 0) {
            productErrorLabel.setText("Search Returned No Results");
        } else {
            productErrorLabel.setText(null);
        }
    }

    /**
     * When the delete button associated with the product tableview is selected, remove that product from the tableview
     * @param actionEvent when the delete button is selected
     */
    public void onProductDeleted(ActionEvent actionEvent) {
        try {
            Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
            //if the selected part has associated parts, it cannot be deleted
            if (selectedProduct.getAllAssociatedParts().size() == 0) {
                if (selectedProduct != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Product Deletion Confirmation");
                    alert.setContentText("Are you sure you want to delete this product?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (((Optional<?>) result).get() == ButtonType.OK) {
                        Inventory.deleteProduct(selectedProduct);
                    }
                }
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Product Error");
                alert.setHeaderText("Delete Product Error");
                alert.setContentText("Cannot Delete a Product Containing Associated Parts.");
                alert.showAndWait();}
        } catch(NullPointerException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Delete Error");
                    alert.setHeaderText("Delete Error");
                    alert.setContentText("Please Select a Product Before Deleting.");
                    alert.showAndWait();
                }
            }
    }