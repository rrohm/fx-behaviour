/*
 * Copyright (C) 2015 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
package com.aeonium.javafx.behaviour.samples;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import javafx.event.ActionEvent;
import javafx.event.Event;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class TestBehaviour extends FXAbstractBehaviour {

  public TestBehaviour() {
    this.onDragDetected = TestBehaviour::handle;
    this.onDragDone = TestBehaviour::handle;
    this.onDragDropped = TestBehaviour::handle;
    this.onDragEntered = TestBehaviour::handle;
    this.onDragExited = TestBehaviour::handle;
    this.onDragOver = TestBehaviour::handle;
    this.onKeyPressed = TestBehaviour::handle;
    this.onKeyReleased = TestBehaviour::handle;
    this.onKeyTyped = TestBehaviour::handle;
    this.onMouseClicked = TestBehaviour::handle;
    this.onMouseDragEntered = TestBehaviour::handle;
    this.onMouseDragExited = TestBehaviour::handle;
    this.onMouseDragOver = TestBehaviour::handle;
    this.onMouseDragReleased = TestBehaviour::handle;
    this.onMouseDragged = TestBehaviour::handle;
    this.onMouseEntered = TestBehaviour::handle;
    this.onMouseExited = TestBehaviour::handle;
    this.onMouseMoved = TestBehaviour::handle;
    this.onMousePressed = TestBehaviour::handle;
    this.onMouseReleased = TestBehaviour::handle;
  }
  
  private static void handle(Event event){
    System.out.printf("TestBehaviour: %s on %s", event.getEventType().getName(), event.getTarget().toString());
  }
  
}
