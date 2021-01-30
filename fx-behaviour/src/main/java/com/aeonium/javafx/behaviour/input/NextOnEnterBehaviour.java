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
package com.aeonium.javafx.behaviour.input;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import javafx.scene.Node;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class NextOnEnterBehaviour extends FXAbstractBehaviour {

  public NextOnEnterBehaviour() {
    this.onKeyPressed = (event) -> {
      System.out.println("--->>>>>>" + this.getClass().getName());
    };
  }

  @Override
  public void bind(Node node, Mode assignmentMode) {
    super.bind(node, assignmentMode); //To change body of generated methods, choose Tools | Templates.
  }
  
}
