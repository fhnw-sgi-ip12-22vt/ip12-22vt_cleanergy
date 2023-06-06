package com.cleanergy.view;

import com.cleanergy.components.SimpleButton;
import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.util.mvcbase.ViewMixin;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class EmulatorView extends VBox implements ViewMixin<GameMaster, GameMasterController> {

  private static ArrayList<Button> playerButtons = new ArrayList<>();

  public EmulatorView(GameMasterController controller) {
    init(controller);
  }

  @Override
  public void initializeSelf() {
    setPrefWidth(250);
  }

  @Override
  public void initializeParts() {
    playerButtons.add(new Button("1"));
    playerButtons.add(new Button("2"));
    playerButtons.add(new Button("3"));
    playerButtons.add(new Button("4"));
  }

  @Override
  public void setupUiToActionBindings(GameMasterController controller) {
  }

  @Override
  public void setupModelToUiBindings(GameMaster model, GameMasterController controller) {
    onChangeOf(model.state)
        .execute((oldValue, newValue) -> {
          for (int i = 0; i < 4; i++) {
            playerButtons.get(i).setOnAction(null);
          }
          switch (newValue) {
            case DICE:
              if (model.getPlayerIndex() < model.getPlayerCount()) {
                playerButtons.get(model.getPlayerIndex())
                    .setOnAction(event -> controller.rollDice());
              }
              break;
            case SONNENMINIGAME, QUIZ, QUIZBORDER:
              playerButtons.get(0).setOnAction(event -> controller.minigameUpdate(0));
              playerButtons.get(1).setOnAction(event -> controller.minigameUpdate(1));
              playerButtons.get(2).setOnAction(event -> controller.minigameUpdate(2));
              playerButtons.get(3).setOnAction(event -> controller.minigameUpdate(3));
              break;
            case STARTMENU:
              playerButtons.get(1).setOnAction(event -> controller.setPlayerCount(2));
              playerButtons.get(2).setOnAction(event -> controller.setPlayerCount(3));
              playerButtons.get(3).setOnAction(event -> controller.setPlayerCount(4));
              break;
            case TUTORIAL, BREAK, BREAKSOLO:
              playerButtons.get(0).setOnAction(event -> controller.minigameUpdate(0));
              playerButtons.get(1).setOnAction(event -> controller.minigameUpdate(1));
              playerButtons.get(2).setOnAction(event -> controller.minigameUpdate(2));
              playerButtons.get(3).setOnAction(event -> controller.minigameUpdate(3));
              break;
          }
        });
  }

  @Override
  public void init(GameMasterController controller) {
    ViewMixin.super.init(controller);
  }

  @Override
  public void layoutParts() {
    setPadding(new Insets(20));
    setSpacing(20);
    setAlignment(Pos.CENTER);
    getChildren().addAll(playerButtons);
  }

  @Override
  public void addStylesheetFiles(String... stylesheetFiles) {
    ViewMixin.super.addStylesheetFiles(stylesheetFiles);
  }

  @Override
  public void loadFonts(String... fonts) {
    ViewMixin.super.loadFonts(fonts);
  }
}
