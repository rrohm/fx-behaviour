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
package com.aeonium.javafx.behaviour.interaction;

import com.aeonium.javafx.behaviour.FXAbstractBehaviour;
import com.aeonium.javafx.actions.exceptions.UnsupportedBehaviourTargetException;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * Base class for autocomplete behaviour for text fields - derive your own
 * behaviour classes in order to provide the custom autocomplete list data.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class AutoCompleteBehaviour extends FXAbstractBehaviour {

  /**
   * The popup node - it contains the list view component and is maintained only
   * once, thus, also derived classes must reuse this instance.
   */
  public static Popup popup = null;
  /**
   * The list view component for the autocomplete popup. As like the popup, also
   * the listview is maintained only once for the whole framework. All derived
   * autocomplete behaviours should reuse this instance.
   */
  public static final ListView<String> listView = new ListView<>();
  /**
   * The filtered list, serving as view model for the list view.
   */

  /**
   * The filtered data list is maintained per behaviour instance.
   */
  protected FilteredList<String> filteredList;
  /**
   * The current textfield that the behaviour methods operate on - it, too, is
   * maintained per instance.
   */
  protected TextField textfield;
  private static TextField currentTextfield;

  /**
   * Whether to replace the current textfield content with the selected text
   * from the list (default behaviour) or whether to append the selected text to
   * the content of the text field.
   */
  public static CompletionMode mode = CompletionMode.APPEND;

  /**
   * Create a new behaviour instance - at present, there may be multiple
   * instances of one behaviour class, so it must be taken care of initializing
   * the instanze properties while not modifying static class properties.
   */
  public AutoCompleteBehaviour() {
    this.onKeyReleased = this::handle;

    this.createPopup();
  }

  /*
   * Static initialization, currently only listview ID.
   */
  static {
    listView.setId("lvAutoComplete");;
  }

  /**
   * Bind the beahviour instance to a node - currently only TextField and
   * descendants are supported, because this behaviour listens on the text
   * property.
   *
   * <strong>Attention!</strong> At present, this method asumes that the text
   * property is only manipulated from the FX thread. Normally, this should be
   * the case, anyway. This might fail, if any code fires key events from
   * outside the FX thread.
   *
   * @param node The node to bind the behaviour to
   * @param assignmentMode The assignement mode, i.e., whether to add event
   * listeners or to replace existing event listeners.
   * @throws UnsupportedBehaviourTargetException If the method is applied to
   * nodes other than javafx.scene.control.TextField or descendants.
   */
  @Override
  public void bind(Node node, Mode assignmentMode) {
    if (!(node instanceof TextField)) {
      throw new UnsupportedBehaviourTargetException(node, this, "Currently only javafx.scene.control.TextField targets are supported.");
    } else {
      super.bind(node, assignmentMode);

      this.textfield = (TextField) node;
      this.textfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, final String newValue) -> {
//        System.out.println("newValue " + newValue);

        // if no text: show all and return.
        if (newValue == null) {
          filteredList.setPredicate((String t) -> true);//            return true;
          return;
        }

        // if text: set filter according to mode
        if (mode.equals(CompletionMode.APPEND)) {
          int lastSeparator = getLastSeparatorPos(newValue) + 1;

//          System.out.println("lastSeparator " + lastSeparator);
          String substring = newValue.substring(lastSeparator).trim();
//          System.out.println("substring=" + substring);

          filteredList.setPredicate((String t) -> {
            if (t == null) {
              return false;
            }
            return t.toLowerCase().startsWith(substring.toLowerCase());
          });
        } else {

          filteredList.setPredicate((String t) -> {
            if (t == null) {
              return false;
            }
            return t.toLowerCase().startsWith(newValue.toLowerCase());
          });
        }

        // common behaviour: if empty, hide!
        if (filteredList.size() == 0) {
          popup.hide();
        }
      });
    }
  }

  /**
   * Helper method, get the last position of the recognized separators (blank,
   * comma and dot), or return -1 if not found.
   *
   * @param t The text string to be examined.
   * @return The last position of recognized separators, or -1 if none of them
   * found.
   */
  protected static int getLastSeparatorPos(final String t) {
    int lastBlank = t.lastIndexOf(" ");
    int lastComma = t.lastIndexOf(",");
    int lastDot = t.lastIndexOf(".");
//    System.out.printf("last blank %d comma %d dot %d \n", lastBlank, lastComma, lastDot);
    int lastSeparator = getMax(lastBlank, lastComma, lastDot);
    return lastSeparator;
  }

  /**
   * Override this method to define your custom lookup data list, but keep in
   * mind: the data list instance is used as a base list for the filtered list
   * in the popup, thus it must not change later at runtime. If you need to
   * implement a dynamic data list, better create event handlers that modify the
   * list instance created in this method.
   *
   * @return The lookup data list.
   */
  protected ObservableList<String> getData() {
    ObservableList<String> dataList = FXCollections.observableArrayList(
            "anon.", "Anton", "beta", "Berta", "Bochum", "Caesar", "Carl", "Käse", "uy", "Au", "Querele", "Quark");
    return dataList;
  }

  /**
   * Build the popup control with the list view, and implement the following
   * behaviour:
   * <ol>
   * <li>On ESCAPE, hide th popup.</li>
   * <li>On Enter, apply the selection.</li>
   * <li>On doubleclick on a selected item, apply the selection.</li>
   * <li>On doubleclick in empty space, do not change the content and hide the
   * popup.</li>
   * </ol>
   *
   */
  private void createPopup() {
    if (popup == null) {
      popup = new Popup();

      listView.setFixedCellSize(22);
      listView.setMinHeight(listView.getFixedCellSize() * 3);
      listView.setPrefHeight(listView.getFixedCellSize() * 7);

      listView.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
          popup.hide();
        }
        if (event.getCode().equals(KeyCode.ENTER)) {
          applySelection();
        }
      });
      listView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
        if (event.getClickCount() == 2) {
          if (listView.getSelectionModel().getSelectedItem() != null) {
            applySelection();
          } else {
            popup.hide();
          }
        }
      });

      listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
      });

      popup.getContent().add(listView);
      popup.setAutoFix(true);
      popup.setAutoHide(true);
    }

    this.filteredList = this.getData().sorted().filtered((String t) -> true);

  }

  /**
   * Apply the selected item, i.e., according to the completion mode either
   * append it to the current text (retaining the already typed characters) or
   * replace the current text with the selected item.
   */
  protected void applySelection() {
    popup.hide();

    if (listView.getSelectionModel().isEmpty()) {
      filteredList.setPredicate((String t) -> true);
      return;
    }

    if (mode.equals(CompletionMode.APPEND)) {
      final String newItem = listView.getSelectionModel().selectedItemProperty().get();

      String text = currentTextfield.getText();

      if (text == null || text.trim().equals("")) {
        text = newItem;
      } else {
        int lastSeparator = getLastSeparatorPos(text) + 1;
        text = text.substring(0, lastSeparator).concat(newItem);
      }

      currentTextfield.setText(text);
      currentTextfield.positionCaret(currentTextfield.getText().length());

    } else {
      currentTextfield.setText(listView.getSelectionModel().selectedItemProperty().get());
      currentTextfield.positionCaret(currentTextfield.getText().length());
    }
    filteredList.setPredicate((String t) -> true);
  }

  /**
   * Show the popup, if it is not already showing, and if the filtered list
   * contains at least one item.
   *
   * @param textfield The textfield that this method currently operates on.
   * Since the behaviour logic is provided by static methods, this method
   * eventually must take care for initialising static data accordingly to the
   * current textfield.
   */
  private void showPopup(TextField textfield) {
    if (popup.isShowing()) {
      return;
    }
    if (filteredList.size() == 0) {
//      System.out.println("showPopup: filteredList.size() == 0");
      return;
    }

    currentTextfield = this.textfield;

    listView.setItems(this.filteredList);
    popup.setWidth(currentTextfield.getWidth());
    listView.setPrefWidth(currentTextfield.getWidth());

    final Window window = currentTextfield.getScene().getWindow();
    if (Platform.isFxApplicationThread()) {
      listView.getSelectionModel().clearSelection();
      popup.show(window,
              window.getX() + currentTextfield.localToScene(0, 0).getX() + currentTextfield.getScene().getX(),
              window.getY() + currentTextfield.localToScene(0, 0).getY() + currentTextfield.getScene().getY() + currentTextfield.getHeight());
    } else {
      Platform.runLater(() -> {
        listView.getSelectionModel().clearSelection();
        popup.show(window,
                window.getX() + currentTextfield.localToScene(0, 0).getX() + currentTextfield.getScene().getX(),
                window.getY() + currentTextfield.localToScene(0, 0).getY() + currentTextfield.getScene().getY() + currentTextfield.getHeight()
        );
      });
    }
  }

  /**
   * Initial key event handler, filters out unwanted key events, but then
   * delegates to the
   * {@link AutoCompleteBehaviour#showPopup(javafx.scene.control.TextField)}
   * method.
   *
   * @param event The current key event.
   */
  private void handle(KeyEvent event) {
    if (event.getTarget() instanceof TextField) {

      if (event.getCode().equals(KeyCode.ESCAPE)
              || event.getCode().equals(KeyCode.ENTER)) {
        return;
      }

      showPopup(this.textfield);

    }
  }

  /**
   * Get the maximum out of several int values, returns 0 if no values given.
   *
   * @param positions The int values.
   * @return The maximum or 0 if no values given.
   */
  private static int getMax(int... positions) {
    if (positions.length == 0) {
      throw new NullPointerException("No numbers provided");
    }

    int max = Integer.MIN_VALUE;
    for (int pos : positions) {
      if (pos > max) {
        max = pos;
      }
    }
    return max;
  }

  /**
   * Mode constants for the autocomplete behaviour: whether to replace the
   * current content of the text field or whether to append the selected text
   * from the list view.
   */
  public static enum CompletionMode {

    /**
     * Default mode, replace the current text with the selected text.
     */
    REPLACE,
    /**
     * Append the selected text from the list view to the current text of the
     * text field.
     */
    APPEND
  }
}
