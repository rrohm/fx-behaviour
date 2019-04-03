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

import com.aeonium.javafx.actions.exceptions.TextInputControlRequiredException;
import com.aeonium.javafx.behaviour.annotations.FXEditMask;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Default annotation handler for the {@link FXEditMask} annotation.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class NumberEditMaskHandler extends EditMaskHandlerBase {

  private static final Logger LOG = Logger.getLogger(NumberEditMaskHandler.class.getName());

  @Override
  public void handle(Object controller, Field field, FXEditMask annotation) {
    if (annotation.mask().isEmpty()) {
      LOG.log(Level.SEVERE, "Field {0} is annotated with @{1}, but with an emtpy edit mask?",
              new Object[]{field.getName(), FXEditMask.class.getSimpleName()});
      return;
    }
    try {
      Object control = field.get(controller);
      if (control instanceof TextInputControl) {
        TextInputControl textInputControl = (TextInputControl) control;

        textInputControl.addEventFilter(KeyEvent.ANY, (event) -> {
          // swallow delete events: 
          if (event.getCode().equals(KeyCode.BACK_SPACE)
                  || event.getCode().equals(KeyCode.DELETE)) {
            event.consume();
            return;
          }
          // skip ENTER events: 
          if (event.getCode().equals(KeyCode.ENTER)) {
            return;
          }

          if (KeyEvent.KEY_TYPED.equals(event.getEventType())) {
            // swallow non-numeric input:
            if (!Character.isDigit(event.getCharacter().charAt(0))) {
              event.consume();
            } else {
              TextInputControl t = (TextInputControl) event.getTarget();
              int anchor = Math.min(t.getAnchor(), t.getCaretPosition());
              if (annotation.mask().charAt(anchor) == '_') {
                t.setText(t.getText().substring(0, anchor) + event.getCharacter() + t.getText().substring(anchor + 1, t.getText().length()));

                if (anchor < annotation.mask().length() - 1) {
                  if (annotation.mask().charAt(anchor + 1) == '_') {
                    int pos = Math.min(t.getText().length(), anchor + 1);
                    t.selectRange(pos, pos);
                  } else {
                    int pos = Math.min(t.getText().length(), anchor + 2);
                    t.selectRange(pos, pos);
                  }
                } else {
                  int pos = annotation.mask().length() - 1;
                  t.selectRange(pos, pos);
                }
              } else {
                t.selectRange(anchor + 1, anchor + 1);
              }
              event.consume();
            }
          }

        });
      } else {
        throw new TextInputControlRequiredException(field);
      }

    } catch (IllegalArgumentException | IllegalAccessException ex) {
      Logger.getLogger(NumberEditMaskHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
