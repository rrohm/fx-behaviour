/*
 * Copyright (C) 2019 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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

import com.aeonium.javafx.actions.annotations.AnnotationHandler;
import com.aeonium.javafx.actions.exceptions.NodeTypeRequiredException;
import com.aeonium.javafx.behaviour.annotations.FXEditMask;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class EditMaskHandler implements AnnotationHandler<FXEditMask> {

  @Override
  public void handle(Object controller, Field field, FXEditMask annotation) {
    try {
      Class<? extends EditMaskHandlerBase> type = annotation.type();
      EditMaskHandlerBase handler = type.newInstance();

      Object get = field.get(controller);
      if (get instanceof TextInputControl) {
        TextInputControl textInputControl = (TextInputControl) get;
        textInputControl.setText(annotation.mask());
      } else {
        throw new NodeTypeRequiredException("Annotation " + annotation.getClass().getName() + " requires a Node of type javafx.scene.control.TextInputControl or descendants. ", get.getClass());
      }
      handler.handle(controller, field, annotation);

    } catch (InstantiationException | IllegalAccessException ex) {
      Logger.getLogger(EditMaskHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
