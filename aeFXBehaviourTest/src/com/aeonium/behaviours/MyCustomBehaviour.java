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
package com.aeonium.behaviours;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import com.aeonium.javafx.behaviour.FXAbstractBehaviour.Mode;
import javafx.scene.input.MouseEvent;

/**
 * A basic example for a custom behaviour implementation - have a look for
 * getting started.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class MyCustomBehaviour extends FXAbstractBehaviour {

  /**
   * Default Constructor - here define the assignment mode (whether handler
   * methods of this behaviour should be added to existing handlers of a node
   * that the behaviour will be bound to, or whether they sould replace existing
   * handlers) and the handler methods.
   */
  public MyCustomBehaviour() {
    this.assignmentMode = Mode.ASSIGN;

    this.onMouseEntered = (MouseEvent t) -> {
      System.out.println("MyCustomBehaviour.onMouseEntered " + t);
    };
  }

}
