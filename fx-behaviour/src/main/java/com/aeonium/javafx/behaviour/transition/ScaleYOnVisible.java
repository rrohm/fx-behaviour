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
package com.aeonium.javafx.behaviour.transition;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class ScaleYOnVisible extends FXAbstractBehaviour {

  private static final Logger LOG = Logger.getLogger(ScaleYOnVisible.class.getName());

  private int duration = 100;
  private int delay = 0;
  private boolean isSequential;
  private ScaleTransition scaleTransition;
  private boolean isInAction = false;

  @Override
  public void bind(Node node, Mode assignmentMode) {
    super.bind(node, assignmentMode);
    if (node == null) {
      throw new NullPointerException("node for ScaleYOnVisible behaviour does not exist!");
    }

    if (node.visibleProperty().isBound()) {
      LOG.log(Level.INFO, "visibleProperty of node is bound, fading out will not be visible. You may want to use a property listener instead of bindings:{0}", node.toString());
    }
    node.visibleProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
      if (isInAction) {
        return;
      }
      
      try {
        isInAction = true;
        if (newValue) {
          stopTransition();
          if (!node.visibleProperty().isBound()) {
            node.setVisible(true);
          }
          scaleTransition = new ScaleTransition(Duration.millis(duration), node);
          scaleTransition.setDelay(Duration.millis(delay));
          scaleTransition.setFromY(0.0);
          scaleTransition.setToY(1.0);
          scaleTransition.play();
        } else {
          stopTransition();
          
          if (!node.visibleProperty().isBound()) {
            node.setVisible(true);
          }
          scaleTransition = new ScaleTransition(Duration.millis(duration), node);
          scaleTransition.setDelay(Duration.millis(delay));
          scaleTransition.setFromY(1);
          scaleTransition.setToY(0);
          scaleTransition.setOnFinished((event) -> {
            if (!node.visibleProperty().isBound()) {
//              node.setVisible(false);
            }
          });
          scaleTransition.play();
        }
      } finally {
        isInAction = false;
      }
    });
  }

  private void stopTransition() {
    if (this.scaleTransition != null) {
      this.scaleTransition.stop();
      this.scaleTransition = null;
    }
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getDelay() {
    return delay;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }

  public boolean isIsSequential() {
    return isSequential;
  }

  public void setIsSequential(boolean isSequential) {
    this.isSequential = isSequential;
  }

}
