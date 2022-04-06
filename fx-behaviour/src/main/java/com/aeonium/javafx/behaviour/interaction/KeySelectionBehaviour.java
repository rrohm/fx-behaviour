/*
 * Copyright (C) 2022 aeonium software systems UG (haftungsbeschränkt).
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
package com.aeonium.javafx.behaviour.interaction;

import com.aeonium.javafx.actions.exceptions.UnsupportedBehaviourTargetException;
import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

/**
 * Select values of a drop down box by key, stepping through the values if
 * several values start with the same initial letter.
 *
 * @author robert rohm
 */
public class KeySelectionBehaviour extends FXAbstractBehaviour {

  public KeySelectionBehaviour() {
    this.onKeyReleased = KeySelectionBehaviour::handle;
  }

  private static void handle(InputEvent event) {
    if (event instanceof KeyEvent) {
      KeyEvent keyEvent = (KeyEvent) event;
      System.err.println(keyEvent.getCharacter());     
      System.err.println(keyEvent.getText());     
      String character = keyEvent.getText().toLowerCase();
      if (character != null && !character.isBlank()) {
        Object source = event.getSource();
        if (source instanceof ChoiceBox) {
          ChoiceBox choiceBox = (ChoiceBox) source;
          StringConverter converter = choiceBox.getConverter();
          for (Object item : choiceBox.getItems()) {
            String toString = (converter != null) ? converter.toString(item).toLowerCase(): item.toString().toLowerCase();
            if (toString.startsWith(character)) {
              if (item.equals(choiceBox.getSelectionModel().getSelectedItem())
                      || choiceBox.getItems().indexOf(item) < choiceBox.getSelectionModel().getSelectedIndex()) {
                continue;
              }
              choiceBox.getSelectionModel().select(item);
              return;
            }
          }
        }
      }
      event.consume();
    }
  }
  
  @Override
  public void bind(Node node, Mode assignmentMode) {
    if (!(node instanceof ChoiceBox)) {
      throw new UnsupportedBehaviourTargetException(node, this, "Currently only javafx.scene.control.ChoiceBox targets are supported.");
    } else {
      super.bind(node, assignmentMode);
    }
  }
}
