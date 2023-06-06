package com.cleanergy.view;

import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.util.mvcbase.ViewMixin;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.animation.*;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.fxml.FXML;

public class SunMinigameView implements ViewMixin<GameMaster, GameMasterController> {

    @FXML
    private Slider sldP1;
    @FXML
    private Slider sldP2;
    @FXML
    private Slider sldP3;
    @FXML
    private Slider sldP4;
    @FXML
    private ImageView ivAir;
    @FXML
    private ImageView ivSun;

    private ArrayList<Timeline> sliderAnimations = new ArrayList<>();
    private ArrayList<Slider> sliders = new ArrayList<>();
    private boolean stop = false;

    public SunMinigameView(GameMasterController controller) {
        init(controller);
    }

    @FXML
    private void initialize() {

    }

    public void start(int playerCount) {

        sliders.add(sldP1);
        sliders.add(sldP2);
        if (playerCount >= 3) {
            sliders.add(sldP3);
            sldP3.setVisible(true);
            ivSun.setVisible(true);
        }
        if (playerCount == 4) {
            sliders.add(sldP4);
            sldP4.setVisible(true);
            ivAir.setVisible(true);
        }

        for (int i = 0; i < playerCount; i++) {
            Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(600),
                    new KeyValue(sliders.get(i).valueProperty(), 100)));
            timeline.play();
            sliderAnimations.add(timeline);
        }
    }

    public boolean stop(int index) {
        sliderAnimations.get(index).stop();
        Predicate<Timeline> checkStopped = timeline -> timeline.getStatus() != Animation.Status.STOPPED;
        return sliderAnimations.stream().filter(checkStopped).collect(Collectors.toList()).isEmpty();
    }

    public ArrayList<Integer> getWinners() {
        ArrayList<Integer> winners = new ArrayList<>();
        for (int i = 0; i < sliders.size(); i++) {
            if (sliders.get(i).getValue() >= 35 && sliders.get(i).getValue() <= 65) {
                winners.add(i);
            }
        }
        return winners;
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
                            case SONNENMINIGAME:
                                start(gm.getPlayerCount());
                                break;
                        }
                    }
                });
        onChangeOf(gm.isPressed)
                .execute((oldValue, newValue) -> {
                    if (!stop) {
                        for (int i = 0; i < gm.getPlayerCount(); i++) {
                            if (oldValue[i] != newValue[i]) {
                                if (stop(i)) {
                                    PauseTransition pause = new PauseTransition(Duration.millis(200));
                                    pause.setOnFinished(event -> {
                                        stop = true;
                                        controller.minigameFinish(getWinners());
                                    });
                                    pause.play();
                                    break;
                                }
                            }
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
