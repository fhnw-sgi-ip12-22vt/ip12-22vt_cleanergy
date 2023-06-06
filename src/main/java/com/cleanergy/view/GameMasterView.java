package com.cleanergy.view;

import com.cleanergy.App;
import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.model.State;
import com.cleanergy.util.mvcbase.ViewMixin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class GameMasterView implements ViewMixin<GameMaster, GameMasterController> {

    @FXML
    AnchorPane root;

    Object view;

  public GameMasterView(GameMasterController controller) {
    init(controller);
  }

  @Override
  public void initializeSelf() {
    // load all fonts you need
  }

  @Override
  public void initializeParts() {
  }

  @Override
  public void init(GameMasterController controller) {
    ViewMixin.super.init(controller);
  }

  @Override
  public void layoutParts() {

  }

  @Override
  public void addStylesheetFiles(String... stylesheetFiles) {
    ViewMixin.super.addStylesheetFiles(stylesheetFiles);
  }

  @Override
  public void loadFonts(String... fonts) {
    ViewMixin.super.loadFonts(fonts);
  }

  @Override
  public List<String> getStylesheets() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setupModelToUiBindings(GameMaster gm, GameMasterController controller) {
    onChangeOf(gm.state)
        .execute((oldValue, newValue) -> {
          switch (newValue) {
            case DICE:
              if (!(view instanceof DiceView)) {
                loadNewView("Dice", controller);
              }
              break;
            case QUIZ:
              // edge case: if we have last player answering border question then we have minigame quiz we need a new instance
              // because after borderquestion its in state stopped and wont start a normal quiz again.
              if (!(view instanceof QuestionView) || oldValue == State.QUIZBORDER) {
                loadNewView("Quiz", controller);
              }
              break;
            case QUIZBORDER:
              if (!(view instanceof QuestionView)) {
                loadNewView("Quiz", controller);
              }
              break;
            case SONNENMINIGAME:
              if (!(view instanceof SunMinigameView)) {
                loadNewView("SunMinigame", controller);
              }
              break;
            case ANEMOMINIGAME:
              if (!(view instanceof AnemoMinigameView)) {
                loadNewView("AnemoMinigame", controller);
              }
              break;
            case STARTMENU:
              loadNewView("StartMenu", controller);
              break;
            case TUTORIAL:
              loadNewView("Tutorial", controller);
              break;
            case WINNER:
              loadNewView("Winner", controller);
              break;
            case BREAK:
              loadNewView("Break", controller);
              break;
            case BREAKSOLO:
              loadNewView("Break", controller);
              break;
            case END:
              loadNewView("End", controller);
              break;
          }
        });
  }

  @Override
  public void setupUiToActionBindings(GameMasterController controller) {
    ViewMixin.super.setupUiToActionBindings(controller);
  }

  private void loadNewView(String viewName, GameMasterController controller) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewName + ".fxml"));
      switch (viewName) {
        case "Dice":
          view = new DiceView(controller);
          break;
        case "AnemoMinigame":
          view = new AnemoMinigameView(controller);
          break;
        case "SunMinigame":
          view = new SunMinigameView(controller);
          break;
        case "StartMenu":
          view = new StartMenuView();
          break;
        case "Tutorial":
          view = new TutorialView(controller);
          break;
        case "Winner":
          view = new WinnerView(controller);
          break;
        case "Quiz":
          view = new QuestionView(controller);
          break;
        case "Break":
          view = new BreakView(controller);
          break;
        case "End":
          view = new EndView(controller);
          break;
      }
      fxmlLoader.setController(view);
      AnchorPane pane = fxmlLoader.load();
      root.getChildren().setAll(pane);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
