package com.cleanergy.view;

import com.cleanergy.App;
import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.model.minigames.Anemometer;
import com.cleanergy.util.mvcbase.ViewMixin;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.fxml.FXML;

public class AnemoMinigameView implements ViewMixin<GameMaster, GameMasterController> {

  @FXML
  private Label lblTimer;

  @FXML
  private ImageView playerImage;

  @FXML
  private ImageView anemoImage;

  @FXML
  private Label readyLabel;

  @FXML
  private Label descriptor;

  private Timeline timer;
  private int time = 15;
  // first player is winner if the second one does not get faster. If both have 0 the first one wins!
  private int leadingPlayerIndex = 0;
  private double highestSpeed = 0;
  private ArrayList<Integer> winners = new ArrayList<Integer>();

  public AnemoMinigameView(GameMasterController controller) {
    init(controller);
  }

  @FXML
  private void initialize() {
    anemoImage.setImage(
        new Image(App.class.getResource("images/anemometer/large_anemo.png").toExternalForm()));
  }

  // this starts the whole cycle of the minigame including the pause at the start for every player
  public void start(GameMasterController controller, GameMaster gamemaster) {
    changePlayer(gamemaster.getPlayerIndex());
    // setup timer at start.
    this.time = 15;
    lblTimer.setText("");
    descriptor.setText("Machen Sie sich bereit in das Windrad zu pusten!");
    readyLabel.setText("Achtung, fertig...");

    // starting with a pause to get the player ready and then change the text to "LOOOOOOS!" and add timer.

    PauseTransition pause = new PauseTransition(Duration.seconds(5));

    // after the pause we run this.
    pause.setOnFinished(event -> {
      timer = new Timeline();
      // why do we need plus 2? i dont get it. but it works perfectly.
      timer.setCycleCount(time + 2);
      timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
          eventTimer -> {
            descriptor.setText("LOOOOOOOOOOOOOS!");
            descriptor.setTextAlignment(TextAlignment.CENTER);
            lblTimer.setText(Integer.toString(time));
            if (time > 0) {
              time--;
            }

            // measure the speed every second
            double currentSpeed = Anemometer.measureAnemoSpeed();
            readyLabel.setText(currentSpeed + " m/s");
            if (currentSpeed > highestSpeed) {
              leadingPlayerIndex = gamemaster.getPlayerIndex();
              highestSpeed = currentSpeed;
            }
          }));

      // recursion that it will run for every player
      timer.setOnFinished(eventTimer -> {
        if (gamemaster.getPlayerIndex() < gamemaster.getPlayerCount() - 1) {
          controller.finishAnemoRoll();
          this.time = 15;
          this.start(controller, gamemaster);
          // TODO: pause transition before next start to show the highestspeed achieved?
        } else {
          PauseTransition pauseEnding = new PauseTransition(Duration.seconds(4));
          pauseEnding.setOnFinished(eventFinished -> {
            winners.add(leadingPlayerIndex);
            controller.minigameFinish(winners);
          });
          pauseEnding.play();
        }
      });
      timer.play();
    });
    pause.play();
  }


  @Override
  public void initializeSelf() {
    ViewMixin.super.initializeSelf();
  }

  public void changePlayer(int index) {
    switch (index) {
      case 0:
        playerImage.setImage(new Image(App.class.getResource("images/water.PNG").toExternalForm()));
        //anchorPane.setStyle("-fx-background-color: #dbf8fe");
        break;
      case 1:
        playerImage.setImage(new Image(App.class.getResource("images/earth.PNG").toExternalForm()));
        //anchorPane.setStyle("-fx-background-color: #ebffe4");
        break;
      case 2:
        playerImage.setImage(new Image(App.class.getResource("images/sun.PNG").toExternalForm()));
        //anchorPane.setStyle("-fx-background-color: #ffefe0");
        break;
      case 3:
        playerImage.setImage(new Image(App.class.getResource("images/air.PNG").toExternalForm()));
        //anchorPane.setStyle("-fx-background-color: #f1f1f3");
        break;
    }
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
          switch (newValue) {
            case ANEMOMINIGAME:
              // start the game for the first player
              // we come with a wrong index we come with the last player as index but it should  be first which is current
              gm.setPlayerIndex(0);
              this.start(controller, gm);
              break;
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
