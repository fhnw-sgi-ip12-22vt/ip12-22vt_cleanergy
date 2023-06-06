package com.cleanergy.view;

import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.util.mvcbase.ViewMixin;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;

public class TutorialView implements ViewMixin<GameMaster, GameMasterController> {

  @FXML
  private Pane paneSun;
  @FXML
  private Pane paneAir;
  @FXML
  private ImageView ivSun;
  @FXML
  private ImageView ivAir;
  @FXML
  private ImageView ivOkSun;
  @FXML
  private ImageView ivOkAir;
  @FXML
  private ImageView ivOkEarth;
  @FXML
  private ImageView ivOkWater;

  private ImageView[] ok;
  private boolean stop = false;

  public TutorialView(GameMasterController controller) {
    init(controller);
  }

  private void start(int playerCount) {
    ok = new ImageView[]{ivOkWater, ivOkEarth};
    if (playerCount >= 3) {
      paneSun.setVisible(true);
      ivSun.setVisible(true);
      ok = new ImageView[]{ivOkWater, ivOkEarth, ivOkSun};
    }
    if (playerCount == 4) {
      paneAir.setVisible(true);
      ivAir.setVisible(true);
      ok = new ImageView[]{ivOkWater, ivOkEarth, ivOkSun, ivOkAir};
    }
  }

  @Override
  public void initializeSelf() {
    ViewMixin.super.initializeSelf();
  }

  @Override
  public void initializeParts() {
  }

  @Override
  public void setupUiToActionBindings(GameMasterController controller) {
    ViewMixin.super.setupUiToActionBindings(controller);
  }

  @Override
  public void setupModelToUiBindings(GameMaster gm, GameMasterController controller) {
    onChangeOf(gm.state)
        .execute((oldValue, newValue) -> {
          if (!stop) {
            switch (newValue) {
              case TUTORIAL:
                start(gm.getPlayerCount());
                break;
            }
          }
        });
    onChangeOf(gm.isPressed)
        .execute((oldValue, newValue) -> {
          if (!stop) {
            for (int i = 0; i < gm.getPlayerCount(); i++) {
              if (newValue[i]) {
                ok[i].setVisible(newValue[i]);
              }
            }
            int counter = 0;
            for (ImageView i : ok) {
              counter += i.isVisible() ? 1 : 0;
            }
            if (counter == gm.getPlayerCount()) {
              stop = true;
              controller.endTutorial();
            }
          }
        });
  }

  @Override
  public void layoutParts() {

  }

  @Override
  public List<String> getStylesheets() {
    return null;
  }
}
