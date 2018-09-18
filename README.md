fx-behaviour
============

The fx-behaviour library is a utility for declaratively defining the behaviour of you JavaFX GUI. Why should you want to do this?
+ There are lots of standard tasks in user interface development: make elements draggable, make controls hide or close a dialog, make elements respond to mouse and keyboard, ...
+ We do not want redundant implementations
+ There is a better way than imperative wiring

= example
Behaviour can be attached to a node simply by annotating it in the controller class. This might look like: 
'''
@FXML
  @FXBehaviour(behaviour = MyCustomBehaviour.class)
  private Label label;

  @FXML
  @FXBehaviour(behaviour = MyCustomBehaviour.class)
  private Button button;

  @FXML
  @FXBehaviour(behaviour = GrabSystemOutBehaviour.class)
  private TextArea textArea;

  @FXML
  @FXBehaviour(behaviour = AutoCompleteBehaviour.class)
  private TextField tfAutoComplete1;

  @FXML
  @FXBehaviour(behaviour = AutoCompleteBehaviour.class)
  private TextField tfAutoComplete2;

  @FXML
  @FXBehaviours({
    @FXBehaviour(behaviour = MyCustomAutocompleteBehaviour.class)
  })
  private TextField tfAutoComplete3;

  @FXML
  @FXBehaviour(behaviour = HideOnEscapeBehaviour.class)
  private Button btHideOnEscape;

  @FXML
  @FXBehaviour(behaviour = DragWindowBehaviour.class)
  private Label lbDragWindow;

  @FXML
  private CheckBox cbToggleVisibility0;
  @FXML
  private CheckBox cbToggleVisibility1;

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
  
  @FXML
  @FXBehaviour(behaviour = ContextMenuBehaviour.class)
  private Button buttonWithContextMenu;

  @FXML
  @FXBehaviour(behaviour = HoverScaleBehaviour.class)
  private Label nodeHoverScale;
  @FXML
  @FXBehaviour(behaviour = HoverScaleBehaviour.class)
  private Label nodeHoverScale1;
  '''

This library is an extension to the fx-actions library. The fx-actions library and the fx-behaviour library will be always developed parallel, so you will always find matching versions in the github repositories.
