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

import static com.aeonium.javafx.AssertFX.*;
import com.aeonium.javafx.FXHelper;
import com.aeonium.javafx.actions.FXActionManager;
import com.aeonium.javafx.behaviour.interaction.AutoCompleteBehaviour;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXMLDocumentControllerTest {

  private static Parent root;
  private static Scene scene;
  private static Stage stage;

  public FXMLDocumentControllerTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    Thread t = new Thread("JavaFX Init Thread") {
      public void run() {
        Application.launch(MyDummyApp.class, new String[0]);
      }
    };
    t.setDaemon(true);
    t.start();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXMLDocumentControllerTest.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @AfterClass
  public static void tearDownClass() throws InterruptedException {
    Thread.sleep(3000);
  }

  @Before
  public void setUp() {

    Platform.runLater(() -> {

      try {
        stage = new Stage();
        FXActionManager myControllerFactory = new FXActionManager();

        root = FXMLLoader.load(AeFXBehaviourTest.class.getResource("FXMLDocument.fxml"), null, null, myControllerFactory);

        myControllerFactory.initActions();

        scene = new Scene(root);

        stage.setTitle("aeFXBehaviour | Demo");
        stage.setScene(scene);
        stage.show();
      } catch (IOException ex) {
        Logger.getLogger(FXMLDocumentControllerTest.class.getName()).log(Level.SEVERE, null, ex);
      }

    });

    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXMLDocumentControllerTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @After
  public void tearDown() {
    FXHelper.invokeOnFXThread(() -> {
      stage.hide();
      stage.setScene(null);
//      scene.setRoot(null);
    });

    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXMLDocumentControllerTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Popup shows, if only one char is typed.
   */
  @Test
  public void testPopupShowingOn2Textfields() throws IOException, InterruptedException {
    TabPane tabPane = (TabPane) root.lookup("#tabPane");
    assertNotNull("tabAutocomplete not null", tabPane);

    FXHelper.selectTab(tabPane, "tabAutocomplete");
    assertSelected(tabPane, "tabAutocomplete");

    assertNotShowing(AutoCompleteBehaviour.popup);

    TextField tfAutocomplete1 = (TextField) root.lookup("#tfAutoComplete1");
    assertNotNull("tfAutocomplete1 not null", tfAutocomplete1);

    FXHelper.typeKey(tfAutocomplete1, "a");

    assertShowing(AutoCompleteBehaviour.popup);
    
    TextField tfAutocomplete2 = (TextField) root.lookup("#tfAutoComplete2");
    assertNotNull("tfAutocomplete2 not null", tfAutocomplete2);

    FXHelper.typeKey(tfAutocomplete2, "a");

    assertShowing(AutoCompleteBehaviour.popup);
    Thread.sleep(100);
  }

  @Test
  public void testPopupShowingAt1Char() throws IOException, InterruptedException {
    TabPane tabPane = (TabPane) root.lookup("#tabPane");
    assertNotNull("tabAutocomplete not null", tabPane);

    FXHelper.selectTab(tabPane, "tabAutocomplete");
    assertSelected(tabPane, "tabAutocomplete");

    assertNotShowing(AutoCompleteBehaviour.popup);

    TextField tfAutocomplete1 = (TextField) root.lookup("#tfAutoComplete1");
    assertNotNull("tfAutocomplete1 not null", tfAutocomplete1);

    FXHelper.typeKey(tfAutocomplete1, "a");

    assertShowing(AutoCompleteBehaviour.popup);
    Thread.sleep(100);
  }

  @Test
  public void testPopupShowingAt2Chars() throws IOException, InterruptedException {

    assertNotNull("root no null", root);
    assertNotNull("stage no null", stage);

    TabPane tabPane = (TabPane) root.lookup("#tabPane");
    assertNotNull("tabAutocomplete not null", tabPane);

    FXHelper.selectTab(tabPane, "tabAutocomplete");
    assertSelected(tabPane, "tabAutocomplete");

    TextField tfAutocomplete1 = (TextField) root.lookup("#tfAutoComplete1");
    assertNotNull("tfAutocomplete1 not null", tfAutocomplete1);

    FXHelper.typeKey(tfAutocomplete1, "an");

    assertShowing(AutoCompleteBehaviour.popup);
    Thread.sleep(1000);

  }

}
