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
package com.aeonium.javafx.behaviour.handler;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
class DateMaskInputFilter implements EventHandler<KeyEvent> {

  private static final Logger LOG = Logger.getLogger(DateMaskInputFilter.class.getName());
  
  private final DateFormat dateFormat;
  private final String mask;
  private final NumericDateEditMaskHandler filter;

  public DateMaskInputFilter(DateFormat dateFormat, String mask, final NumericDateEditMaskHandler filter) {
    this.filter = filter;
    this.dateFormat = dateFormat;
    this.mask = mask;
  }

  @Override
  public void handle(KeyEvent event) {
    final TextInputControl t = (TextInputControl) event.getTarget();
    // swallow delete events:
    if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)) {
      event.consume();
      return;
    }
    // skip ENTER events:
    if (event.getCode().equals(KeyCode.ENTER)) {
      return;
    }
    if (event.getCode().equals(KeyCode.UP)) {
      if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
        try {
          int anchor = Math.min(t.getAnchor(), t.getCaretPosition());
          Date date = dateFormat.parse(t.getText());
          date = rollDateUp(mask, t, date, 1);
          t.setText(dateFormat.format(date));
          t.selectRange(anchor, anchor);
          event.consume();
        } catch (ParseException ex) {
          LOG.log(Level.SEVERE, null, ex);
        }
      }
      event.consume();
    }
    if (event.getCode().equals(KeyCode.DOWN)) {
      if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
        try {
          int anchor = Math.min(t.getAnchor(), t.getCaretPosition());
          Date date = dateFormat.parse(t.getText());
          date = rollDateUp(mask, t, date, -1);
          t.setText(dateFormat.format(date));
          t.selectRange(anchor, anchor);
          event.consume();
        } catch (ParseException ex) {
          LOG.log(Level.SEVERE, null, ex);
        }
      }
      event.consume();
    }
    if (KeyEvent.KEY_TYPED.equals(event.getEventType())) {
      // swallow non-numeric input:
      if (!Character.isDigit(event.getCharacter().charAt(0))) {
        event.consume();
      } else {
        int anchor = Math.min(t.getAnchor(), t.getCaretPosition());
        if (isReplacableChar(mask.charAt(anchor))) {
          t.setText(t.getText().substring(0, anchor) + event.getCharacter() + t.getText().substring(anchor + 1, t.getText().length()));
          if (anchor < mask.length() - 1) {
            if (isReplacableChar(mask.charAt(anchor + 1))) {
              int pos = Math.min(t.getText().length(), anchor + 1);
              t.selectRange(pos, pos);
            } else {
              int pos = Math.min(t.getText().length(), anchor + 2);
              t.selectRange(pos, pos);
            }
          } else {
            int pos = mask.length() - 1;
            t.selectRange(pos, pos);
          }
        } else {
          t.selectRange(anchor + 1, anchor + 1);
        }
        event.consume();
      }
    }
  }

  private boolean isReplacableChar(char c) {
    return c == '_' || c == 'd' || c == 'M' || c == 'y' || c == 'h' || c == 'H' || c == 'm' || c == 'i';
  }

  private Date rollDateUp(String mask, TextInputControl t, Date date, int step) {
    final int anchor = Math.min(t.getAnchor(), t.getCaretPosition());
    final int dateUnit;
    char charAt = mask.charAt(anchor);
    switch (charAt) {
      case 'd':
        dateUnit = Calendar.DATE;
        break;
      case 'M':
        dateUnit = Calendar.MONTH;
        break;
      case 'y':
        dateUnit = Calendar.YEAR;
        break;
      case 'h':
      case 'H':
        dateUnit = Calendar.HOUR_OF_DAY;
        break;
      case 'm':
        dateUnit = Calendar.MINUTE;
        break;
      case 's':
        dateUnit = Calendar.SECOND;
        break;
      default:
        if (anchor > 0) {
          switch (mask.charAt(anchor) - 1) {
            case 'd':
              dateUnit = Calendar.DATE;
              break;
            case 'M':
              dateUnit = Calendar.MONTH;
              break;
            case 'y':
              dateUnit = Calendar.YEAR;
              break;
            case 'h':
            case 'H':
              dateUnit = Calendar.HOUR_OF_DAY;
              break;
            case 'm':
              dateUnit = Calendar.MINUTE;
              break;
            case 's':
              dateUnit = Calendar.SECOND;
              break;
            default:
              throw new AssertionError();
          }
        } else {
          switch (mask.charAt(anchor) + 1) {
            case 'd':
              dateUnit = Calendar.DATE;
              break;
            case 'M':
              dateUnit = Calendar.MONTH;
              break;
            case 'y':
              dateUnit = Calendar.YEAR;
              break;
            case 'h':
            case 'H':
              dateUnit = Calendar.HOUR_OF_DAY;
              break;
            case 'm':
              dateUnit = Calendar.MINUTE;
              break;
            case 's':
              dateUnit = Calendar.SECOND;
              break;
            default:
              throw new AssertionError();
          }
        }
    }
    final Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(dateUnit, step);
    return cal.getTime();
  }
  
}
