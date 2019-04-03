fx-behaviour
============

The fx-behaviour library is a utility for JavaFX 11 that allows for declaratively defining the behaviour of JavaFX GUIs. Why should you want to do this?
+ There are lots of standard tasks in user interface development: make elements draggable, make controls hide or close a dialog, make elements respond to mouse and keyboard, ...
+ We do not want redundant implementations
+ There is a better way than imperative wiring

= example
Behaviour can be attached to a node simply by annotating it in the controller class. This might look like: 
```java

/**
* In general, the library lets you implement any behaviour and apply it to a node like this:
*/
@FXML
@FXBehaviour(behaviour = MyCustomBehaviour.class)
private Label label;

/**
* Nodes may be labels, buttons or anything that can become interactive, i.e.,
* that reacts to mouse events.
*/
@FXML
@FXBehaviour(behaviour = MyCustomBehaviour.class)
private Button button;


/**
* This text area grabs ans displays all console output: 
*/
@FXML
@FXBehaviour(behaviour = GrabSystemOutBehaviour.class)
private TextArea textArea;


/**
* Also autocompletion may be implemented and added to a node very easily:
*/
@FXML
@FXBehaviour(behaviour = AutoCompleteBehaviour.class)
private TextField tfAutoComplete1;

@FXML
@FXBehaviour(behaviour = AutoCompleteBehaviour.class)
private TextField tfAutoComplete2;

/**
* Multiple behaviours may be applied to a node. Thus, you may compose it's behaviour...
*/
@FXML
@FXBehaviours({
@FXBehaviour(behaviour = MyCustomAutocompleteBehaviour.class)
})
private TextField tfAutoComplete3;

/**
* Use this one to hide dialogs or stages on escape ...
*/
@FXML
@FXBehaviour(behaviour = HideOnEscapeBehaviour.class)
private Button btHideOnEscape;

/**
* This one lets you drag the whole stage like a handle:
*/
@FXML
@FXBehaviour(behaviour = DragWindowBehaviour.class)
private Label lbDragWindow;

/**
* Some example on managing visibility ...
*/
@FXML
@FXBehaviour(behaviour = FadeInOnVisible.class)
private Node nodeToggleVisibility0;
@FXML
@FXBehaviour(behaviour = FadeInOnVisible.class)
private Node nodeToggleVisibility1;

@FXML
private CheckBox cbToggleVisibility2;

@FXML
@FXBehaviour(behaviour = FadeAndManageOnVisible.class)
private Label nodeToggleVisibility2;
  
/**
* Normally, a context menu cannot be toggled with the context menu key. 
* This behaviour adds keyboard support to your context menus:
*/
@FXML
@FXBehaviour(behaviour = ContextMenuBehaviour.class)
private Button buttonWithContextMenu;

/**
* This label an the following one have a visual effect applied to them.
*/
@FXML
@FXBehaviour(behaviour = HoverScaleBehaviour.class)
private Label nodeHoverScale;
@FXML
@FXBehaviour(behaviour = HoverScaleBehaviour.class)
private Label nodeHoverScale1;
```

This library is an extension to the fx-actions library. The fx-actions library and the fx-behaviour library will be always developed parallel, so you will always find matching versions in the github repositories.
