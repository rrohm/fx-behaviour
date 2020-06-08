/*
 * Copyright (C) 2020 Robert Rohm &lt;r.rohm@aeonium-systems.de&gt;.
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
package com.aeonium.javafx.behaviour.handler;

import com.aeonium.javafx.actions.FXActionManager;
import com.aeonium.javafx.actions.annotations.AnnotationHandler;
import com.aeonium.javafx.behaviour.annotations.FXFadeInOnVisible;
import com.aeonium.javafx.behaviour.transition.FadeInOnVisible;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;

/**
 * The default annotation handler for the FXFadeInOnVisible annotation.
 *
 * @author Robert Rohm &lt;r.rohm@aeonium-systems.de&gt;
 */
public class FadeInOnVisibleHandler implements AnnotationHandler<FXFadeInOnVisible> {

  private FXActionManager manager;

  public FadeInOnVisibleHandler() {
  }

  public FadeInOnVisibleHandler(FXActionManager manager) {
    this.manager = manager;
  }

  /**
   * Bind the behaviour to the node references by the field of the given
   * controller, therefore a new instance of the behaviour class is created.
   *
   * @param controller The controller object
   * @param field The field of the controller
   * @param annotation The behaviour annotation
   */
  @Override
  public void handle(Object controller, Field field, FXFadeInOnVisible annotation) {
    try {

      FXFadeInOnVisible fxBehaviour = field.getAnnotation(FXFadeInOnVisible.class);

      FadeInOnVisible behaviour = new FadeInOnVisible();
      behaviour.setDelay(fxBehaviour.delay());
      behaviour.setDuration(fxBehaviour.duration());

      Object control = field.get(controller);
      behaviour.bind((Node) control, behaviour.getAssignmentMode());

    } catch (IllegalArgumentException | IllegalAccessException ex) {
      Logger.getLogger(FadeInOnVisibleHandler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
    }
  }
}
