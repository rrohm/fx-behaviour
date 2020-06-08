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
package com.aeonium.javafx.behaviour.effects;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * A simple example for behavioural animation effects. The behaviour can be
 * applied to multiple nodes. You do not need a field for an explicit reference
 * to the target nodes.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class HoverScaleBehaviour extends FXAbstractBehaviour {

  public HoverScaleBehaviour() {

    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3));
    scaleTransition.setFromX(1);
    scaleTransition.setFromY(1);
    scaleTransition.setToX(1.2);
    scaleTransition.setToY(1.2);

    this.onMouseEntered = (event) -> {
      scaleTransition.setNode((Node) event.getSource());
      scaleTransition.setRate(1);
      scaleTransition.play();
    };

    this.onMouseExited = (event) -> {
      scaleTransition.setRate(-1);
      scaleTransition.play();
    };
  }
}
