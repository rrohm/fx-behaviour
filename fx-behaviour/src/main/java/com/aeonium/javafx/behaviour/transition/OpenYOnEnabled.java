/*
 * Copyright (C) 2021 aeonium software systems UG (haftungsbeschränkt).
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
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * Open and close a node in Y-direction (vertically), toggled by the <pre>Node#disabledProperty</pre>.
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class OpenYOnEnabled extends FXAbstractBehaviour {

  private static final Logger LOG = Logger.getLogger(OpenYOnEnabled.class.getName());

  private int duration = 200;
  private int delay = 0;
  private Transition transition;
  private boolean isInAction = false;
  private double originalH = -1;

  @Override
  public void bind(Node node, Mode assignmentMode) {
    super.bind(node, assignmentMode);
    if (node == null) {
      throw new NullPointerException("node for ScaleYOnVisible behaviour does not exist!");
    }

    this.originalH = ((Region) node).getPrefHeight();

    node.disabledProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> ov, Boolean oldDisable, Boolean newDisable) {
        if (handleChange(node, newDisable)) {
          return;
        }
      }
    });
    
    this.handleChange(node, node.isDisabled());
  }

  public boolean handleChange(Node node, Boolean newDisable) {
    ((Region) node).setMinHeight(1.0);
    try {
      if (isInAction) {
        return true;
      }
      try {
        isInAction = true;

        // FADE IN
        if (!newDisable) {
          stopTransition();
          if (!node.visibleProperty().isBound()) {
            node.setVisible(true);
          }
          if (!node.managedProperty().isBound()) {
            node.setManaged(true);
          }
          double height = ((Region) node).getHeight();
          ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration), node);
          scaleTransition.setDelay(Duration.millis(delay));
          scaleTransition.setFromY(0.0);
          scaleTransition.setToY(1.0);

          TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration), node);
          translateTransition.setDelay(Duration.millis(delay));
          translateTransition.setFromY(-height / 2);
          translateTransition.setToY(0);

          Timeline timeline = new Timeline();
          timeline.getKeyFrames().addAll(
                  new KeyFrame(Duration.ZERO, new KeyValue(((Region) node).prefHeightProperty(), 0)),
                  new KeyFrame(Duration.millis(duration), new KeyValue(((Region) node).prefHeightProperty(), originalH))
          );

          transition = new ParallelTransition(scaleTransition, translateTransition, timeline);
          transition.setOnFinished((t) -> {
            isInAction = false;
          });
          transition.play();

        } else {
          // FADE OUT
          stopTransition();

          if (!node.visibleProperty().isBound()) {
            isInAction = true;
            node.setVisible(true);
            isInAction = false;
          }
          if (!node.managedProperty().isBound()) {
            node.setManaged(true);
          }

          ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration), node);
          scaleTransition.setDelay(Duration.millis(delay));
          scaleTransition.setFromY(1);
          scaleTransition.setToY(0);
          scaleTransition.setOnFinished((event) -> {
          });
          double height = ((Region) node).getHeight();
          TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration), node);
          translateTransition.setDelay(Duration.millis(delay));
          translateTransition.setFromY(0);
          translateTransition.setToY(-height / 2);

          Timeline timeline = new Timeline();
          timeline.getKeyFrames().addAll(
                  new KeyFrame(Duration.ZERO, new KeyValue(((Region) node).prefHeightProperty(), originalH)),
                  new KeyFrame(Duration.millis(duration), new KeyValue(((Region) node).prefHeightProperty(), 0))
          );

          transition = new ParallelTransition(
                  scaleTransition,
                  //                    translateTransition,
                  timeline
          );
          transition.setOnFinished((t) -> {
            if (!node.visibleProperty().isBound()) {
              isInAction = true;
              node.setVisible(false);
            }
            if (!node.managedProperty().isBound()) {
              node.setManaged(false);
            }
            isInAction = false;
          });
          transition.play();
        }
      } finally {
        isInAction = false;
      }
    } catch (Exception e) {
      LOG.throwing(OpenYOnEnabled.this.getClass().getName(), "visibilityListener", e);
    }
    return false;
  }

  private void stopTransition() {
    if (this.transition != null) {
      this.transition.stop();
      this.transition = null;
    }
  }

}
