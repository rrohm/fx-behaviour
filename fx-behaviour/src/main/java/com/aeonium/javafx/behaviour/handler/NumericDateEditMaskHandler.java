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
package com.aeonium.javafx.behaviour.handler;

import com.aeonium.javafx.actions.exceptions.TextInputControlRequiredException;
import com.aeonium.javafx.behaviour.annotations.FXEditMask;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Default annotation handler for the {@link FXEditMask} annotation.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class NumericDateEditMaskHandler extends EditMaskHandlerBase {

  private static final Logger LOG = Logger.getLogger(NumericDateEditMaskHandler.class.getName());

  @Override
  public void handle(Object controller, Field field, FXEditMask annotation) {
    final String mask = annotation.mask();
    if (mask.isEmpty()) {
      LOG.log(Level.SEVERE, "Field {0} is annotated with @{1}, but with an emtpy edit mask?",
              new Object[]{field.getName(), FXEditMask.class.getSimpleName()});
      return;
    }
    final DateFormat dateFormat;
    try {
      dateFormat = new SimpleDateFormat(mask);
    } catch (IllegalArgumentException e) {
      LOG.log(Level.SEVERE, "Illegal date pattern: {0} on field {1}", new Object[]{mask, field.getName()});
      return;
    }

    try {
      Object control = field.get(controller);
      if (control instanceof TextInputControl) {
        TextInputControl textInputControl = (TextInputControl) control;

        textInputControl.addEventFilter(KeyEvent.ANY, new DateMaskInputFilter(dateFormat, mask, this));
      } else {
        throw new TextInputControlRequiredException(field);
      }

    } catch (IllegalArgumentException | IllegalAccessException ex) {
      Logger.getLogger(NumericDateEditMaskHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
  }


}
