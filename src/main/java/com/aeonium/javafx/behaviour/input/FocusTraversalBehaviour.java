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
package com.aeonium.javafx.behaviour.input;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This behaviour adds a key listener that lets you traverse input controls of a
 * common parent by hitting the ENTER key.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FocusTraversalBehaviour extends FXAbstractBehaviour {

  public FocusTraversalBehaviour() {
//    this.assignmentMode = Mode.ADD;
//  
//    this.onKeyReleased = (event) -> {
//      System.out.println("--->>>>>>" + this.getClass().getName());
//    };
//    
    
  }
  

  @Override
  public void bind(Node node, Mode assignmentMode) {
    super.bind(node, assignmentMode); 

    if (node instanceof Parent) {
      Parent parent = (Parent) node;
      
      final List<Node> nodeList = new ArrayList<>();
      this.getFlatChildList(node, nodeList);
      
      for (Node node1 : nodeList) {
        if (isApplicableNodeType(node1)) {
          
          node1.addEventHandler(KeyEvent.KEY_RELEASED, (event) -> {
          
            if (event.getCode().equals(KeyCode.ENTER)) {
              int indexOf = nodeList.indexOf(node1);
              for (Node node2 : nodeList) {
                if (!node2.isVisible()) {
                  continue;
                }
                if (node2.isDisabled()) {
                  continue;
                }
                if (!isApplicableNodeType(node2)) {
                  continue;
                }
                if (nodeList.indexOf(node2) > indexOf) {
                  node2.requestFocus();
                  break;
                }
              }
              event.consume();
            }
          });
        }
      }
    } else {
      throw new UnsupportedOperationException(this.getClass().getName() + " is only appplicable to Parent nodes or child classes of Parent.");
    }
  }

  private static boolean isApplicableNodeType(Node node2) {
    return (node2 instanceof TextInputControl) || (node2 instanceof ButtonBase);
  }
  
  private void getFlatChildList(Node node, List<Node> list){
    if (node instanceof Parent) {
      Parent parent = (Parent) node;
      for (Node n : parent.getChildrenUnmodifiable()) {
        list.add(n);
        getFlatChildList(n, list);
      }
    }
  }
}
