/*
 * Copyright (C) 2017 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This behaviour provides copy behaviour for nodes that do not support this 
 * out-of-the-box. 
 * 
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class CopyToClipboardBehaviour extends FXAbstractBehaviour {

  private Node node;

  public CopyToClipboardBehaviour() {
    this.onKeyReleased = (KeyEvent event) -> {
      if (event.isControlDown() && event.getCode().equals(KeyCode.C)) {
        ClipboardContent content = new ClipboardContent();
        StringBuilder textContent = new StringBuilder();
        
        if (this.node instanceof ListView) {
          ListView listView = (ListView) this.node;
          int n = 0;
          for (Object selectedItem : listView.getSelectionModel().getSelectedItems()) {
            if (n > 0) {
              textContent.append("\n");
            }
            textContent.append(selectedItem.toString());
            n++;
          }
        } else if (this.node instanceof TableView) {
          TableView tableView = (TableView) this.node;
          int n = 0;
          for (Object selectedItem : tableView.getSelectionModel().getSelectedItems()) {
            if (n > 0) {
              textContent.append("\n");
            }
            textContent.append(selectedItem.toString());
            n++;
          }
        } else if (this.node instanceof TreeView) {
          TreeView treeView = (TreeView) this.node;
          int n = 0;
          for (Object selectedItem : treeView.getSelectionModel().getSelectedItems()) {
            if (n > 0) {
              textContent.append("\n");
            }
            textContent.append(selectedItem.toString());
            n++;
          }
        }
        
        
        content.putString(textContent.toString());
        Clipboard.getSystemClipboard().setContent(content);
      }
    };
  }
  
  @Override
  public void bind(Node node, Mode assignmentMode) {
    super.bind(node, assignmentMode); 
    this.node = node;
  }
}
