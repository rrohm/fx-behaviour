/*
 * Copyright (C) 2020 Aeonium Software Systems.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.aeonium.javafx.behaviour.interaction;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class ToggleAlwaysOneBehaviour extends FXAbstractBehaviour {

  public ToggleAlwaysOneBehaviour() {
    this.onKeyPressed = ToggleAlwaysOneBehaviour::handle;
    this.onKeyReleased = ToggleAlwaysOneBehaviour::handle;
    this.onKeyTyped = ToggleAlwaysOneBehaviour::handle;
    this.assignmentMode = Mode.FILTER;
  }

  private static void handle(KeyEvent event) {
    
    ToggleButton bt = (ToggleButton) event.getSource();
    if (bt.equals(bt.getToggleGroup().getSelectedToggle())) {
      if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
        event.consume();
      }
      if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
        event.consume();
      }
    }
  }
}
