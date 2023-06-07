package com.cleanergy.view;

import java.util.List;

import com.cleanergy.App;
import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.model.State;
import com.cleanergy.util.mvcbase.ViewMixin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BreakView implements ViewMixin<GameMaster, GameMasterController> {
    @FXML
    private ImageView ivSun;
    @FXML
    private ImageView ivAir;
    @FXML
    private ImageView ivWater;
    @FXML
    private ImageView ivEarth;
    @FXML
    private ImageView ivOkSun;
    @FXML
    private ImageView ivOkAir;
    @FXML
    private ImageView ivOkEarth;
    @FXML
    private ImageView ivOkWater;
    @FXML
    private Label lblMinigame;
    @FXML
    private Label lblDescription;
    @FXML
    private ImageView ivMinigameAnimation;
    @FXML
    private ImageView ivButtonAnimation;

    private ImageView[] ok;
    private boolean stop = false;
    private boolean soloBreak = false;
    private int soloindex = 0;

    public BreakView(GameMasterController controller) {
        init(controller);
    }

    @FXML
    private void initialize() {
        ivButtonAnimation.setImage(new Image(App.class.getResource("images/animationButton.gif").toExternalForm(),true));
    }

    public ImageView getOkPlayerImage(int index) {
        ImageView imageForPlayer = null;
        switch (index) {
            case 0:
                imageForPlayer = ivOkWater;
                break;
            case 1:
                imageForPlayer = ivOkEarth;
                break;
            case 2:
                imageForPlayer = ivOkSun;
                break;
            case 3:
                imageForPlayer = ivOkAir;
                break;
        }
        return imageForPlayer;
    }

    public ImageView getPlayerImage(int index) {
        ImageView imageForPlayer = null;
        switch (index) {
            case 0:
                imageForPlayer = ivWater;
                break;
            case 1:
                imageForPlayer = ivEarth;
                break;
            case 2:
                imageForPlayer = ivSun;
                break;
            case 3:
                imageForPlayer = ivAir;
                break;
        }
        return imageForPlayer;
    }

    private void start(int playerCount, State state) {
        ok = new ImageView[] { ivOkWater, ivOkEarth };

        if (soloBreak) {
            ivWater.setVisible(false);
            ivEarth.setVisible(false);
            getPlayerImage(soloindex).setVisible(true);
            ok = new ImageView[] { getOkPlayerImage(soloindex) };
        }

        if (playerCount >= 3) {
            ivSun.setVisible(true);
            ok = new ImageView[] { ivOkWater, ivOkEarth, ivOkSun };
        }
        if (playerCount == 4) {
            ivAir.setVisible(true);
            ok = new ImageView[] { ivOkWater, ivOkEarth, ivOkSun, ivOkAir };
        }

        switch (state) {
            case SONNENMINIGAME:
                lblMinigame.setText("Sonnen Minispiel");
                lblDescription.setText(
                        "Drücken Sie ihren Knopf um die Sonne anzuhalten. \n Man gewinnt, wenn die Sonne im grünen Bereich landet.");
                ivMinigameAnimation.setImage(new Image(App.class.getResourceAsStream("images/animationSun.gif")));
                break;
            case QUIZ:
                lblMinigame.setText("Quiz");
                lblDescription.setText(
                        "Drücken Sie ihren Knopf um ihre Antwort zu verändern. \n Man gewinnt, wenn das Symbol auf der richtigen Antwort steht bevor der Timer endet.");
                ivMinigameAnimation.setImage(new Image(App.class.getResourceAsStream("images/animationQuiz.gif")));
                break;
            case QUIZBORDER:
                lblMinigame.setText("Entsperrung der Zone");
                lblDescription.setText(
                    "Sie kommen mit dem Wurf als erster über eine neue Zone!\n" + 
                    "Wenn du die folgende Frage richtig beantworten kannst du über die Zone laufen,\n" + 
                    "falls nicht kommst du nur zum letzten Feld vor dem Fluss.");
                ivMinigameAnimation.setImage(new Image(App.class.getResourceAsStream("images/animationQuiz.gif")));
                break;
            case ANEMOMINIGAME:
                lblMinigame.setText("Windrad Minispiel");
                lblDescription.setText(
                    "Nach kurzer Anlaufzeit\n" +
                    "musst du so stark in das Windrad hineinblasen wie du kannst!\n" + 
                    "Du kriegst 15 Sekunden.");
                break;
                // TODO animation for anemometer?
                //ivMinigameAnimation.setImage(new Image(App.class.getResourceAsStream("images/animationQuiz.gif")));
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
                            case BREAK:
                                start(gm.getPlayerCount(), controller.getBreakState());
                                break;
                            case BREAKSOLO:
                                soloindex = gm.getPlayerIndex();
                                soloBreak = true;
                                start(1, controller.getBreakState());
                                break;
                        }
                    }
                });
        onChangeOf(gm.isPressed)
                .execute((oldValue, newValue) -> {
                    if (!stop) {
                        // if solo we want to handle things differently
                        switch(gm.state.getValue()) {
                            case BREAK:
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
                                    controller.endBreak();
                                }
                                break;
                            case BREAKSOLO: 
                                if (newValue[gm.getPlayerIndex()]) {
                                    ok[0].setVisible(newValue[gm.getPlayerIndex()]);
                                }

                                if (ok[0].isVisible()) {
                                    stop = true;
                                    controller.endBreak();
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
