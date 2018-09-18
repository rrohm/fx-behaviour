/*
 * Copyright (C) 2018 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import com.aeonium.javafx.behaviour.UnsupportedBehaviourTargetException;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * Adjust the height of text areas to the content length. 
 * 
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class AutoHeightBehaviour extends FXAbstractBehaviour {

  private TextArea textArea;
  private static final Text text = new Text();

  @Override
  public void bind(Node node, Mode assignmentMode) {
    if (!(node instanceof TextArea)) {
      throw new UnsupportedBehaviourTargetException(node, this, "Currently only javafx.scene.control.TextArea targets are supported.");
    } else {
      super.bind(node, assignmentMode);
      final TextArea ta = (TextArea) node;

      this.textArea = ta;
      this.textArea.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

        Platform.runLater(() -> {
          text.setFont(ta.getFont());
          text.setWrappingWidth(ta.getWidth());
          text.setText("".concat((ta.getText() != null) ? ta.getText() : "").concat("\ng"));

          ta.setPrefHeight(text.getLayoutBounds().getHeight() + 10);
        });

      });
    }
  }

}
