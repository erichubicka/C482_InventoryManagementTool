package Controller;

/**
 *
 * @author Eric Hubicka
 */

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class will utilize the AddPart form which creates a new instance of a part to be added into the parts inventory
 */
public class AddPart implements Initializable {
    /**
     * Radiobutton for selecting an InHouse part
     */
    public RadioButton oRB1;
    /**
     * Radiobutton for selecting an Outsourced part
     */
    public RadioButton oRB2;
    /**
     * Declare the label, which can be either a Machine ID or a Company Name
     */
    public Label MachineIdCompanyName;
    /**
     * Part ID textfield
     */
    public TextField idTextField;
    /**
     * Part name textfield
     */
    public TextField nameTextField;
    /**
     * Part inventory textfield
     */
    public TextField invTextField;
    /**
     * Part price textfield
     */
    public TextField priceTextField;
    /**
     * Part max textfield
     */
    public TextField maxTextField;
    /**
     * Part min textfield
     */
    public TextField minTextField;
    /**
     * Part machineID/companyName textfield
     */
    public TextField machineIdCompanyNameTextField;

    /**
     * Used to initialize the controller with any code that needs to be implemented when the form is accessed.
     * It is not used here.
     * @param url passes the resources associated with the FXML file
     * @param resourceBundle manages location specific resources (strings, various data-types, etc)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ignore
    }

    /**
     * If the user cancels the creation of a new part, user is sent back to the mainscreen
     * @param actionEvent when the cancel button is selected
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file
     */
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        //keeps the stage and changes the screen from AddPart screen to mainScreen when cancel button selected
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow(); //get stage from action event's Node (button)
        Scene scene = new Scene(root,1050,425); //create new scene
        stage.setTitle("Main Scene"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    /**
     * When the InHouse radio button is selected, display the MachineID label
     * @param actionEvent when the InHouse radio button is selected
     */
    public void onORB1(ActionEvent actionEvent) {
        MachineIdCompanyName.setText("Machine ID");}
    /**
     * When the Outsourced radio button is selected, display the MachineCompanyName label
     * @param actionEvent when the Outsourced radio button is selected
     */
    public void onORB2(ActionEvent actionEvent) {
        MachineIdCompanyName.setText("Company Name");}

    /**
     * Compile data input from the form to create a new part.
     * This new part will be added to the part Inventory.
     * User is redirected back to the MainScreen view.
     * Different alerts are displayed for the form not being filled out correctly.
     * @param actionEvent triggered when the save button is selected
     * @throws IOException when an IO error occurs
     */
    public void createNewPart(ActionEvent actionEvent) throws IOException { //create new InHouse/Outsourced part object and add it to the mainscreen tableview
        String companyName;
        int stock = 0;
        int min;
        int max = 0;
        int machineId;
        double price;
        int id = Inventory.autoGeneratePartId(); //auto-generated id from the autoGeneratePartID method in the Inventory class
        String name = nameTextField.getText(); //retrieve string value from nameTextField, assign it to name variable
        if (name.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Input Error");
            alert.setHeaderText("Form Input Error");
            alert.setContentText("Name field cannot be blank.");
            alert.showAndWait();
        } else{
            try{
                stock = Integer.parseInt(invTextField.getText()); //retrieve value from invTextField, convert string to integer, assign it to stock variable
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Text Input Error");
                alert.setHeaderText("Text Input Error");
                alert.setContentText("Inv field empty or is not of type int.");
                alert.showAndWait();
            }
            try{
                max = Integer.parseInt(maxTextField.getText());
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Text Input Error");
                alert.setHeaderText("Text Input Error");
                alert.setContentText("Max field empty or is not of type int.");
                alert.showAndWait();
            }
            min = max + 1; //stops the program from saving the part if the min value is empty
            try{
                min = Integer.parseInt(minTextField.getText());
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Text Input Error");
                alert.setHeaderText("Text Input Error");
                alert.setContentText("Min field empty or is not of type int.");
                alert.showAndWait();
            }
            try{
                price = Double.parseDouble(priceTextField.getText()); //retrieve value from priceTextField, convert string to double, assign it to stock variable
                if (max > stock && stock > min){ //if the stock value is in between the max and min values, and the max is greater than min
                    //use either machineId or companyName depending on which radio button is selected then add a new part
                    if(oRB1.isSelected()){
                        try{
                            machineId = Integer.parseInt(machineIdCompanyNameTextField.getText());
                            //take the variables values from the AddPart form and append it as a record to the allParts ObservableList via the Inventory.addPart method
                            Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                            // upon selecting save button, change from AddPart screen to the MainScreen
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root,1050,425);
                            stage.setTitle("Main Scene");
                            stage.setScene(scene);
                            stage.show();
                        }catch(NumberFormatException e){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Text Input Error");
                            alert.setHeaderText("Text Input Error");
                            alert.setContentText("Machine ID field empty or is not of type int.");
                            alert.showAndWait();
                        }
                    } else {
                        companyName = machineIdCompanyNameTextField.getText();
                        if (companyName.isBlank()){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Form Input Error");
                            alert.setHeaderText("Form Input Error");
                            alert.setContentText("Company Name field cannot be blank.");
                            alert.showAndWait();
                        } else{
                            //take the variables values from the AddPart form and append it as a record to the allParts ObservableList via the Inventory.addPart method
                            Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                            // upon selecting save button, change from AddPart screen to the MainScreen
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root,1050,425);
                            stage.setTitle("Main Scene");
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                } else{ //alert if the stock value is not between the max and min values, or if the max value is less than the min value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Form Input Error");
                    alert.setHeaderText("Form Input Error");
                    alert.setContentText("Please Ensure Max > Inv > Min.");
                    alert.showAndWait();
                }
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Text Input Error");
                alert.setHeaderText("Text Input Error");
                alert.setContentText("Price/Cost field empty or is not of type double.");
                alert.showAndWait();
            }
        }
    }
}
