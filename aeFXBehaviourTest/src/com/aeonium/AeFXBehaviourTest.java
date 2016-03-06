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

import com.aeonium.javafx.actions.FXActionManager;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class of the demo application - the only special point in this class is
 * the initialization of the aeFXBehaviour framework when the FXML UI gets
 * loaded.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class AeFXBehaviourTest extends Application {
  
  /**
   * The controller component of the aeFXActions/aeFXBehaviour frameworks, takes
   * care of initializing, injecting and managing all action and behaviour
   * classes - technically, it is a class that can serve as a JavaFX controller
   * factory.
   */
  private FXActionManager myControllerFactory;

  /**
   * JavaFX main method - here the FXML UI gets loaded with the FXActionManager
   * as a controller factory. At present, after that, the controller needs to
   * get initialized manually, but after this, we are done with the setup.
   *
   * @param stage
   * @throws Exception
   */
  @Override
  public void start(Stage stage) throws Exception {

    myControllerFactory = new FXActionManager();

    Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"), null, null, myControllerFactory);

    myControllerFactory.initActions();

    Scene scene = new Scene(root);

    stage.setTitle("aeFXBehaviour | Demo");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Old launcher entry point, needs to be kept for compatibility.
   *
   * @param args The command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Getter for the action manager, currently only for better testability.
   *
   * @return  The action manager. 
   */
  public FXActionManager getMyControllerFactory() {
    return myControllerFactory;
  }

}
