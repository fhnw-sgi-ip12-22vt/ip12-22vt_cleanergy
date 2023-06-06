package com.cleanergy.view;

import com.cleanergy.App;
import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.model.Question;
import com.cleanergy.model.Quiz;
import com.cleanergy.model.State;
import com.cleanergy.util.mvcbase.ViewMixin;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionView implements ViewMixin<GameMaster, GameMasterController> {

  @FXML
  private ImageView ivWater1;
  @FXML
  private ImageView ivEarth1;
  @FXML
  private ImageView ivSun1;
  @FXML
  private ImageView ivAir1;
  @FXML
  private ImageView ivWater2;
  @FXML
  private ImageView ivEarth2;
  @FXML
  private ImageView ivSun2;
  @FXML
  private ImageView ivAir2;
  @FXML
  private ImageView ivWater3;
  @FXML
  private ImageView ivEarth3;
  @FXML
  private ImageView ivSun3;
  @FXML
  private ImageView ivAir3;
  @FXML
  private ImageView ivWater4;
  @FXML
  private ImageView ivEarth4;
  @FXML
  private ImageView ivSun4;
  @FXML
  private ImageView ivAir4;
  @FXML
  private Label lblQuestion;
  @FXML
  private Label lblInformation;
  @FXML
  private Label lblTimer;
  @FXML
  private Button btnAnswer1;
  @FXML
  private Button btnAnswer2;
  @FXML
  private Button btnAnswer3;
  @FXML
  private Button btnAnswer4;

  private int[] playerPosition = new int[]{0, 0, 0, 0};
  private Question question;
  private boolean stop = false;
  private List<ImageView[]> images;
  private Timeline timer;
  private int time = 19;

  public QuestionView(GameMasterController controller) {
    init(controller);
  }

  @Override
  public void initializeSelf() {
    ViewMixin.super.initializeSelf();
  }

  @Override
  public void initializeParts() {

  }

  // current player image if we neeed a single image by playerindex
  public ImageView[] getPlayerImage(int index) {
    ImageView[] imageForPlayer = new ImageView[4];
    switch (index) {
      case 0:
        imageForPlayer[0] = ivWater1;
        imageForPlayer[1] = ivWater2;
        imageForPlayer[2] = ivWater3;
        imageForPlayer[3] = ivWater4;
        break;
      case 1:
        imageForPlayer[0] = ivEarth1;
        imageForPlayer[1] = ivEarth2;
        imageForPlayer[2] = ivEarth3;
        imageForPlayer[3] = ivEarth4;
        break;
      case 2:
        imageForPlayer[0] = ivSun1;
        imageForPlayer[1] = ivSun2;
        imageForPlayer[2] = ivSun3;
        imageForPlayer[3] = ivSun4;
        break;
      case 3:
        imageForPlayer[0] = ivAir1;
        imageForPlayer[1] = ivAir2;
        imageForPlayer[2] = ivAir3;
        imageForPlayer[3] = ivAir4;
        break;
    }
    return imageForPlayer;
  }

  private void start() {
    lblQuestion.setText(question.getQuestion());
    btnAnswer1.setText(question.getAnwser1());
    btnAnswer2.setText(question.getAnwser2());
    btnAnswer3.setText(question.getAnwser3());
    btnAnswer4.setText(question.getAnwser4());
    // TODO: WE need to set the informations as well of the questions
    //lblInformation.setText(question.getInformation());
    
    timer = new Timeline();
    timer.setCycleCount(20);
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
        event -> {
          lblTimer.setText(Integer.toString(time));
          time--;
        }));
    timer.play();
  }

  private void draw(int playerCount) {
    for (int i = 0; i < playerCount; i++) {
      if (playerPosition[i] == 0) {
        images.get(i)[3].setVisible(false);
      } else {
        images.get(i)[playerPosition[i] - 1].setVisible(false);
      }
      images.get(i)[playerPosition[i]].setVisible(true);
    }
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
              case QUIZ:
                question = Quiz.getQuestion();
                ImageView[] water = new ImageView[]{ivWater1, ivWater2, ivWater3, ivWater4};
                ImageView[] earth = new ImageView[]{ivEarth1, ivEarth2, ivEarth3, ivEarth4};
                ImageView[] sun = new ImageView[]{ivSun1, ivSun2, ivSun3, ivSun4};
                ImageView[] air = new ImageView[]{ivAir1, ivAir2, ivAir3, ivAir4};

                images = List.of(water, earth, sun, air);

                start();

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> {
                  controller.minigameFinish(images.stream()
                      .filter(i -> i[question.getCorrect()].isVisible())
                      .map(i -> images.indexOf(i))
                      .collect(Collectors.toCollection(ArrayList::new)));
                });

                timer.setOnFinished(event -> {
                  stop = true;
                  List.of(btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4).get(question.getCorrect())
                      .setStyle("-fx-background-color:green");
                  pause.play();
                });
                break;
              case QUIZBORDER:
                question = Quiz.getBorderquestion(gm.getCurrentPlayer().getZone());
                ImageView[] playerImage = getPlayerImage(gm.getPlayerIndex());
                List<ImageView[]> playerImageList = Collections.singletonList(playerImage);
                images = playerImageList;

                start();

                PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
                pause2.setOnFinished(event -> {
                  // if the playerposition is correct he had it right.
                    controller.finishBorderQuiz(question.getCorrect() == playerPosition[0]);
                  // TODO give feedback if he is correct or not and if he should move
                });

                timer.setOnFinished(event -> {
                  stop = true;
                  List.of(btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4).get(question.getCorrect())
                      .setStyle("-fx-background-color:green");
                  
                  pause2.play();
                });
              break;
            }
          }
        });
    onChangeOf(gm.isPressed)
        .execute((oldValue, newValue) -> {
          if (!stop) {
            if (gm.state.getValue() == State.QUIZ) {
              for (int i = 0; i < 4; i++) {
                if (oldValue[i] != newValue[i]) {
                  playerPosition[i]++;
                  if (playerPosition[i] == 4) {
                    playerPosition[i] = 0;
                  }
                }
              }
              draw(gm.getPlayerCount());
            } else {
              // if its not in quiz its in the special quiz for one player if traversing the zone.
              int i = gm.getPlayerIndex();
              if (oldValue[i] != newValue[i]) {
                playerPosition[0]++;
                if (playerPosition[0] == 4) {
                  playerPosition[0] = 0;
                }
              }
              draw(1);
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
