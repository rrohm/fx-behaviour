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
package com.aeonium.javafx.behaviour.transition;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author robert
 */
public class FadeAndScaleOnDisable  extends FXAbstractBehaviour {
  
  private int duration = 2000;
  private int delay = 0;
  private boolean isSequential;
  private FadeTransition fadeTransition;
  private boolean isInAction = false;
  private boolean isFadingOut = false;
  private boolean isFadingIn = false;

  @Override
  public void bind(Node node, Mode assignmentMode) {
    super.bind(node, assignmentMode);
    if (node == null) {
      throw new NullPointerException("node for FadeInOnVisible behaviour does not exist!");
    }
    
    node.disabledProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean newValue) -> {
      System.out.println("0");
      if (isInAction) {
        System.out.println("-");
        return;
      }
      
      if (newValue) {
        System.out.println("1");
        stopTransition();
        node.setManaged(true);
        node.getParent().requestLayout();
        fadeTransition = new FadeTransition(Duration.millis(duration), node);
        fadeTransition.setDelay(Duration.millis(delay));
        fadeTransition.setFromValue(node.getOpacity());
        fadeTransition.setToValue(1.0);
        fadeTransition.setOnFinished((event) -> {
          System.out.println("1.x");
        });
        fadeTransition.play();
        
      } else {
        System.out.println("2");
        stopTransition();
        if (!node.visibleProperty().isBound()) {
          System.out.println("2.1");
          isInAction = true;
          node.setVisible(true);
          isInAction = false;
        }
        
        fadeTransition = new FadeTransition(Duration.millis(duration), node);
        fadeTransition.setDelay(Duration.millis(delay));
        fadeTransition.setFromValue(node.getOpacity());
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((event) -> {
          System.out.println("2.x");
          if (!node.visibleProperty().isBound()) {
            System.out.println("2.x.2");
            isInAction = true;
            node.setVisible(false);
            isInAction = false;
            node.setManaged(false);
            node.getParent().requestLayout();
          }
          isFadingOut = false;
        });
        isFadingOut = true;
        fadeTransition.play();
      }
    });
  }
  
  private void stopTransition() {
    if (this.fadeTransition != null) {
      this.fadeTransition.stop();
      this.fadeTransition = null;
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
