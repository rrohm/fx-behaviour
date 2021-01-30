/*
 * Copyright (C) 2020 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
package com.aeonium.javafx.behaviour.stage;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

/**
 * Make the root node listen on ESCAPE key hits, and hides the stage if ESCAPE
 * has been pressed (... if it was the primary stage, the application
 * terminates).
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class HideOnEscapeRootBehaviour extends FXAbstractBehaviour {

  private static final Logger LOG = Logger.getLogger(HideOnEscapeRootBehaviour.class.getName());

  private Node node;

  @Override
  public void bind(Node node, Mode assignmentMode) {
    super.bind(node.getScene().getRoot(), assignmentMode);

    this.node = node.getScene().getRoot();
  }

  public HideOnEscapeRootBehaviour() {
    this.assignmentMode = Mode.FILTER;

    this.onKeyPressed = (KeyEvent event) -> {
      if (event.getCode().equals(KeyCode.ESCAPE)) {
        LOG.finest("ESC pressed.");

        Window window = node.getScene().getWindow();

        if (window != null) {
          window.hide();
          event.consume();

        } else {
          LOG.fine("No window, nothing to hide.");
        }
      }
    };
  }
}
