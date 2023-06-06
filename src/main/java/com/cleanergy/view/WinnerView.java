package com.cleanergy.view;

import java.util.ArrayList;
import java.util.List;

import com.cleanergy.App;
import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.util.mvcbase.ViewMixin;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class WinnerView implements ViewMixin<GameMaster, GameMasterController> {

    @FXML
    private AnchorPane root;
    @FXML
    private HBox hbWinner;
    @FXML
    private ImageView iv;
    @FXML
    private Label lblFail;
    @FXML
    private Label lblReminder;

    public WinnerView(GameMasterController controller) {
        init(controller);
    }

    @FXML
    private void initialize() {

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
    public void layoutParts() {
        // TODO Auto-generated method stub

    }

    @Override
    public List<String> getStylesheets() {
        // TODO Auto-generated method stub
        return null;
    }

    private ImageView generateImageView(String url) {
        ImageView iv = new ImageView(
                App.class.getResource(url).toExternalForm());
        iv.setFitWidth(200);
        iv.setFitHeight(200);
        return iv;
    }

    private void setupWinners(ArrayList<Integer> winners) {
        for (int i : winners) {
            switch (i) {
                case 0:
                    hbWinner.getChildren().add(generateImageView("images/water.PNG"));
                    break;
                case 1:
                    hbWinner.getChildren().add(generateImageView("images/earth.PNG"));
                    break;
                case 2:
                    hbWinner.getChildren().add(generateImageView("images/sun.PNG"));
                    break;
                case 3:
                    hbWinner.getChildren().add(generateImageView("images/air.PNG"));
                    break;
            }
        }
        lblReminder.setVisible(true);
    }

    @Override
    public void setupModelToUiBindings(GameMaster gm, GameMasterController controller) {
        onChangeOf(gm.state)
                .execute((oldValue, newValue) -> {
                    switch (newValue) {
                        case WINNER:
                            if (gm.getWinners().size() > 0) {
                                setupWinners(gm.getWinners());
                            } else {
                                lblFail.setVisible(true);
                            }
                            PauseTransition pause = new PauseTransition(Duration.seconds(3));
                            pause.setOnFinished(event -> {
                                controller.newRound();
                            });
                            pause.play();
                            break;
                    }
                });
    }
}
