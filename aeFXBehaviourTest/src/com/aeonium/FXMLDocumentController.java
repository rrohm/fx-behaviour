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
 *  2018 Aeonium Software Systems, Robert Rohm.
 */
package com.aeonium;

import com.aeonium.behaviours.MyCustomAutocompleteBehaviour;
import com.aeonium.behaviours.MyCustomBehaviour;
import com.aeonium.javafx.behaviour.FXBehaviour;
import com.aeonium.javafx.behaviour.FXBehaviours;
import com.aeonium.javafx.behaviour.effects.HoverScaleBehaviour;
import com.aeonium.javafx.behaviour.interaction.AutoCompleteBehaviour;
import com.aeonium.javafx.behaviour.interaction.ContextMenuBehaviour;
import com.aeonium.javafx.behaviour.stage.DragWindowBehaviour;
import com.aeonium.javafx.behaviour.stage.HideOnEscapeBehaviour;
import com.aeonium.javafx.behaviour.system.GrabSystemOutBehaviour;
import com.aeonium.javafx.behaviour.transition.FadeAndManageOnVisible;
import com.aeonium.javafx.behaviour.transition.FadeInOnVisible;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
  @FXBehaviour(behaviour = GrabSystemOutBehaviour.class)
  private TextArea textArea;

  @FXML
  @FXBehaviour(behaviour = AutoCompleteBehaviour.class)
  private TextField tfAutoComplete1;

  @FXML
  @FXBehaviour(behaviour = AutoCompleteBehaviour.class)
  private TextField tfAutoComplete2;

  @FXML
  @FXBehaviours({
    @FXBehaviour(behaviour = MyCustomAutocompleteBehaviour.class)
  })
  private TextField tfAutoComplete3;

  @FXML
  @FXBehaviour(behaviour = HideOnEscapeBehaviour.class)
  private Button btHideOnEscape;

  @FXML
  @FXBehaviour(behaviour = DragWindowBehaviour.class)
  private Label lbDragWindow;

  @FXML
  private CheckBox cbToggleVisibility0;
  @FXML
  private CheckBox cbToggleVisibility1;

  @FXML
  @FXBehaviour(behaviour = FadeInOnVisible.class)
  private Node nodeToggleVisibility0;
  @FXML
  @FXBehaviour(behaviour = FadeInOnVisible.class)
  private Node nodeToggleVisibility1;

  @FXML
  private CheckBox cbToggleVisibility2;

  @FXML
  @FXBehaviour(behaviour = FadeAndManageOnVisible.class)
  private Label nodeToggleVisibility2;
  
  @FXML
  @FXBehaviour(behaviour = ContextMenuBehaviour.class)
  private Button buttonWithContextMenu;

  @FXML
  @FXBehaviour(behaviour = HoverScaleBehaviour.class)
  private Label nodeHoverScale;
  @FXML
  @FXBehaviour(behaviour = HoverScaleBehaviour.class)
  private Label nodeHoverScale1;

  @FXML
  private void handleButtonAction(ActionEvent event) {
    System.out.println("You clicked me!");
    label.setText("Hello World!");
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    this.nodeToggleVisibility0.visibleProperty().bind(this.cbToggleVisibility0.selectedProperty());
//    this.nodeToggleVisibility2.visibleProperty().bind(this.cbToggleVisibility2.selectedProperty());

    this.cbToggleVisibility1.selectedProperty().addListener((observable, oldValue, newValue) -> {
      this.nodeToggleVisibility1.setVisible(newValue);
    });
    this.cbToggleVisibility2.selectedProperty().addListener((observable, oldValue, newValue) -> {
      this.nodeToggleVisibility2.setVisible(newValue);
    });

//    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5));
//    scaleTransition.setFromX(1);
//    scaleTransition.setFromY(1);
//    scaleTransition.setToX(1.2);
//    scaleTransition.setToY(1.2);
//    this.nodeHoverScale.setOnMouseEntered((event) -> {
//      scaleTransition.setNode((Node) event.getSource());
//      scaleTransition.setRate(1);
//      scaleTransition.play();
//    });
//    this.nodeHoverScale.setOnMouseExited((event) -> {
//      scaleTransition.setRate(-1);
//      scaleTransition.play();
//    });
  }

}
