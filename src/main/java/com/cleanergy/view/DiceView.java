package com.cleanergy.view;

import com.cleanergy.App;
import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.Dice;
import com.cleanergy.model.GameMaster;
import com.cleanergy.util.mvcbase.ViewMixin;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.List;

public class DiceView implements ViewMixin<GameMaster, GameMasterController> {

  @FXML
  private Label playerTitle;
  @FXML
  private ImageView ivPlayer;
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private ImageView ivDice;
  @FXML
  private ImageView ivDiceAdvantage;
  @FXML
  private Label lblField;

  private int diceRoll = 0;
  private int diceRollAdvantage = 0;
  Timeline diceRollAnimation;
  Timeline diceRollAnimationAdvantage;
  private Dice dice = new Dice();
  private boolean stop = false;

  public DiceView(GameMasterController controller) {
    init(controller);
  }

  public void changePlayer(int index) {
    String player = "";
    switch (index) {
      case 0:
        player = "water";
        break;
      case 1:
        player = "earth";
        break;
      case 2:
        player = "sun";
        break;
      case 3:
        player = "air";
        break;
    }

    anchorPane.getStyleClass().clear();
    anchorPane.getStyleClass().add(player);
    ivPlayer.setImage(new Image(App.class.getResource("images/"+player+".PNG").toExternalForm()));

    ivDiceAdvantage.setImage(null);
  }

  public void diceAnimation(boolean advantage) {
    Rotate rotate = new Rotate();
    rotate.setAngle(60);
    rotate.setPivotX(ivDice.getX() + ivDice.getFitWidth() / 2);
    rotate.setPivotY(ivDice.getY() + ivDice.getFitHeight() / 2);

    diceRollAnimation = new Timeline();
    diceRollAnimation.setCycleCount(Timeline.INDEFINITE);
    diceRollAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(250),
        event -> {
          diceRoll = dice.rollDice();
          ivDice.setImage(
              new Image(
                  App.class.getResource("images/dice/" + diceRoll + ".png").toExternalForm()));
          ivDice.getTransforms().add(rotate);
        }));
    diceRollAnimation.play();
    if (advantage) {
      ivDiceAdvantage.setVisible(true);
      Rotate rotateAdvantage = new Rotate();
      rotateAdvantage.setAngle(60);
      rotateAdvantage.setPivotX(ivDice.getX() + ivDice.getFitWidth() / 2);
      rotateAdvantage.setPivotY(ivDice.getY() + ivDice.getFitHeight() / 2);

      diceRollAnimationAdvantage = new Timeline();
      diceRollAnimationAdvantage.setCycleCount(Timeline.INDEFINITE);
      diceRollAnimationAdvantage.getKeyFrames().add(new KeyFrame(Duration.millis(250),
          event -> {
            diceRollAdvantage = dice.rollDice();
            ivDiceAdvantage.setImage(
                new Image(App.class.getResource("images/dice/" + diceRollAdvantage + ".png")
                    .toExternalForm()));
            ivDiceAdvantage.getTransforms().add(rotate);
          }));
      diceRollAnimationAdvantage.play();
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
              case DICE:
                if (gm.getPlayerIndex() < gm.getPlayerCount()) {
                  changePlayer(gm.getPlayerIndex());
                  lblField.setVisible(false);
                  ivDice.setVisible(true);
                  ivDiceAdvantage.setVisible(false);
                  diceAnimation(gm.getPlayers().get(gm.getPlayerIndex()).isAdvantage());
                }
                break;
              case ROLL:
                diceRollAnimation.stop();
                if (gm.getPlayers().get(gm.getPlayerIndex()).isAdvantage()) {
                  diceRollAnimationAdvantage.stop();
                  if (diceRollAdvantage > diceRoll) {
                    diceRoll = diceRollAdvantage;
                    ivDice.setVisible(false);
                  } else {
                    ivDiceAdvantage.setVisible(false);
                  }
                  // diceRoll = diceRollAdvantage > diceRoll ? diceRollAdvantage : diceRoll;
                }
                lblField.setText("Bewegen Sie sich um " + diceRoll + " Felder");
                lblField.setVisible(true);

                PauseTransition pause = new PauseTransition(Duration.seconds(4));
                pause.setOnFinished(event -> {
                  controller.finishRoll(diceRoll);
                });
                pause.play();
                stop = gm.getPlayerIndex() == gm.getPlayerCount() - 1 ? true : false;
                // we need to add this since we need to create new diceview with different start after the quiz and old one should not linger
                if (!gm.nextZoneUnlocked() && gm.crossingZone(diceRoll)) {
                  // TODO: show something that tells him he needs to answer the quiz because he got over to a new zone.
                  stop = true;
                }
                break;
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
