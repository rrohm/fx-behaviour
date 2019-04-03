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
package com.aeonium.javafx.behaviour.stage;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import java.util.logging.Logger;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Make a node listen on drag events and moves the stage according to mouse
 * movements.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class DragWindowBehaviour extends FXAbstractBehaviour {

  private static final Logger LOG = Logger.getLogger(DragWindowBehaviour.class.getName());

  private Node node;
  private double x;
  private double y;
  private boolean dragging;
  private double startPosY;
  private double startPosX;
  private Cursor oldCursor;

  @Override
  public void bind(Node node, Mode assignmentMode) {
    super.bind(node, assignmentMode);

    this.node = node;
  }

  public DragWindowBehaviour() {
    this.assignmentMode = Mode.FILTER;

    // with hysteresis? not wanted here.
//    this.onDragDetected = (MouseEvent event) -> {
//      x = event.getScreenX();
//      y = event.getScreenY();
//      
//    };

    this.onMouseDragged = (MouseEvent event) -> {
      if (dragging) {
        node.getScene().getWindow().setX(startPosX + event.getScreenX() - x);
        node.getScene().getWindow().setY(startPosY + event.getScreenY() - y);
        event.consume();
      }
    };

    this.onMousePressed = (MouseEvent event) -> {
      x = event.getScreenX();
      y = event.getScreenY();
      startPosX = node.getScene().getWindow().getX();
      startPosY = node.getScene().getWindow().getY();
      dragging = true;
      
      oldCursor = node.getCursor();
      node.setCursor(Cursor.MOVE);
      event.consume();
    };

    this.onMouseReleased = (MouseEvent event) -> {
      dragging = false;
      node.setCursor(oldCursor);
      event.consume();
    };
  }
}
