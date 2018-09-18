/*
 * Copyright (C) 2018 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This is an example for implementing a custom behaviour: JavaFX context menus
 * currently do not show up if users press the context menu key - this class
 * adds this behaviour to a node. If the node has a context menu, the it may be
 * triggered with the context menu key, now.
 *
 * In general, a behaviour class needs extend FXAbstractBehaviour and define the
 * behaviour as event handler methods that get triggered by the appropriate
 * event.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class ContextMenuBehaviour extends FXAbstractBehaviour {

  public ContextMenuBehaviour() {
    this.onKeyPressed = ContextMenuBehaviour::handle;
  }

  private static void handle(KeyEvent event) {
    final Object source = event.getSource();

    if (KeyCode.CONTEXT_MENU.equals(event.getCode())) {
      if (source instanceof Control) {
        Control control = (Control) source;

        final ContextMenu contextMenu = control.getContextMenu();
        if (contextMenu != null) {
          int y = 0;

          if (control instanceof TreeTableView) {
            TreeTableView treeTableView = (TreeTableView) control;
            int selectedIndex = treeTableView.getSelectionModel().getSelectedIndex();

            y = (int) (selectedIndex * treeTableView.getFixedCellSize());

            Parent parent = control.getParent();

            if (parent instanceof ScrollPane) {
              ScrollPane scrollPane = (ScrollPane) parent;
              scrollPane.getVvalue();
            }

            contextMenu.show(control, 0, y);
          } else {

            contextMenu.show(control, Side.BOTTOM, 0, y);
          }
        }
      }
    }
  }

}
