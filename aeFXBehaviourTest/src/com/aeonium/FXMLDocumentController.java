/*
 *  This code is released under Creative Commons Attribution 4.0 International
 *  (CC BY 4.0) license, http://creativecommons.org/licenses/by/4.0/legalcode .
 *  That means:
 * 
 *  You are free to:
 * 
 *      Share — copy and redistribute the material in any medium or format
 *      Adapt — remix, transform, and build upon the material
 *               for any purpose, even commercially.
 * 
 *      The licensor cannot revoke these freedoms as long as you follow the
 *      license terms.
 * 
 *  Under the following terms:
 * 
 *      Attribution — You must give appropriate credit, provide a link to the
 *      license, and indicate if changes were made. You may do so in any
 *      reasonable manner, but not in any way that suggests the licensor endorses
 *      you or your use.
 * 
 *  No additional restrictions — You may not apply legal terms or technological
 *  measures that legally restrict others from doing anything the license
 *  permits.
 * 
 *
 *  2015 Aeonium Software Systems, Robert Rohm.
 */
package com.aeonium;

import com.aeonium.behaviours.MyCustomAutocompleteBehaviour;
import com.aeonium.behaviours.MyCustomBehaviour;
import com.aeonium.javafx.behaviour.FXBehaviour;
import com.aeonium.javafx.behaviour.interaction.AutoCompleteBehaviour;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller for the demo UI - here, all the declarative wiring takes place.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXMLDocumentController implements Initializable {

  @FXML
  @FXBehaviour(behaviour = MyCustomBehaviour.class)
  private Label label;

  @FXML
  @FXBehaviour(behaviour = MyCustomBehaviour.class)
  private Button button;

  @FXML
//  @FXBehaviour(behaviour = GrabSystemOutBehaviour.class)
  private TextArea textArea;

  @FXML
  @FXBehaviour(behaviour = AutoCompleteBehaviour.class)
  private TextField tfAutoComplete1;

  @FXML
  @FXBehaviour(behaviour = AutoCompleteBehaviour.class)
  private TextField tfAutoComplete2;

  @FXML
  @FXBehaviour(behaviour = MyCustomAutocompleteBehaviour.class)
  private TextField tfAutoComplete3;

  @FXML
  private void handleButtonAction(ActionEvent event) {
    System.out.println("You clicked me!");
    label.setText("Hello World!");
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // Currently nothing to do.
  }

}
