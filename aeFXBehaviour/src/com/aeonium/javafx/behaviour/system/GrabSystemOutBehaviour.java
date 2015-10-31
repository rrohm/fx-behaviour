/*
 * Copyright (C) 2015 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
package com.aeonium.javafx.behaviour.system;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import com.aeonium.javafx.behaviour.UnsupportedBehaviourTargetException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

/**
 * For use with a TextArea node: lets you grab System.out and System.err output
 * and redirect it to this control.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class GrabSystemOutBehaviour extends FXAbstractBehaviour {

  /**
   * Keep references on the original print stream.
   */
  private PrintStream oldOut = System.out;
  /**
   * Keep references on the original print stream.
   */
  private PrintStream oldErr = System.err;

  /**
   * Custom binding mechanism, since for this behaviour no event listeners are
   * needed.
   *
   * @param node The node to bind the behaviour to.
   * @param assignmentMode Not used by this class.
   */
  @Override
  public void bind(Node node, Mode assignmentMode) {
    if (node instanceof TextArea) {
      TextArea textArea = (TextArea) node;

      final PrintStream textAreaPrintStream = new PrintStream(new OutputStream() {

        @Override
        public void write(int b) throws IOException {
          char c = (char) b;
          textArea.appendText(Character.toString(c));
        }
      });

      System.setOut(textAreaPrintStream);
      System.setErr(textAreaPrintStream);

    } else {
      throw new UnsupportedBehaviourTargetException(node, this, "SystemOutGrabber only suppors TextArea. ");
    }
  }

}
