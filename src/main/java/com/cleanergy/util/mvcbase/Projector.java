package com.cleanergy.util.mvcbase;

import java.util.Objects;

/**
 * Projector is the common interface for both, GUI and PUI.
 * <p>
 * See Dierk Koenig's conference talk:
 * https://jaxenter.de/effiziente-oberflaechen-mit-dem-projektor-pattern-42119
 */
interface Projector<M, C extends ControllerBase<M>> {

  /**
   * needs to be called inside the constructor of your UI-part
   */
  default void init(C controller) {
    Objects.requireNonNull(controller);
    initializeSelf();
    initializeParts();
    setupUiToActionBindings(controller);
    setupModelToUiBindings(controller.getModel(), controller);
  }

  /**
   * Everything that needs to be done to initialize the UI-part itself.
   * <p>
   * For GUIs loading stylesheet-files or additional fonts are typical examples.
   */
  default void initializeSelf() {
  }

  /**
   * completely initialize all necessary UI-elements (like buttons, text-fields, etc. on GUI or
   * distance sensors on PUI )
   */
  void initializeParts();


  /**
   * Triggering some action on Controller if the user interacts with the UI.
   * <p>
   * There's no need to have access to model for this task.
   * <p>
   * All EventHandlers will call a single method on the Controller.
   * <p>
   * If you are about to call more than one method, you should introduce a new method on
   * Controller.
   */
  default void setupUiToActionBindings(C controller) {
  }

  /**
   * Whenever an 'ObservableValue' in 'model' changes, the UI must be updated.
   * <p>
   * There's no need to have access to controller for this task.
   * <p>
   * Register all necessary observers here.
   */
  default void setupModelToUiBindings(M model, C controller) {
  }
}
