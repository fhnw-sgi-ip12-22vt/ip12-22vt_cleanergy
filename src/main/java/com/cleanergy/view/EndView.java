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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class EndView implements ViewMixin<GameMaster, GameMasterController> {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView ivPlayer;
    @FXML
    private Label lblWinnerDescription;

    public EndView(GameMasterController controller) {
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

    private void generateImageView(String url) {
        ivPlayer.setImage(new Image(App.class.getResource(url).toExternalForm()));
    }

    private void setupWinner(int winner) {
        switch (winner) {
            case 0:
                generateImageView("images/water.PNG");
                root.getStyleClass().add("water");
                lblWinnerDescription.setText("Wasserkraft hat gewonnen");
                break;
            case 1:
                generateImageView("images/earth.PNG");
                root.getStyleClass().add("earth");
                lblWinnerDescription.setText("Erdenergie hat gewonnen");
                break;
            case 2:
                generateImageView("images/sun.PNG");
                root.getStyleClass().add("sun");
                lblWinnerDescription.setText("Sonnenenergie hat gewonnen");
                break;
            case 3:
                generateImageView("images/air.PNG");
                root.getStyleClass().add("air");
                lblWinnerDescription.setText("Windkraft hat gewonnen");
                break;
        }

    }

    @Override
    public void setupModelToUiBindings(GameMaster gm, GameMasterController controller) {
        onChangeOf(gm.state)
                .execute((oldValue, newValue) -> {
                    switch (newValue) {
                        case END:
                            setupWinner(gm.getWinners().get(0));
                            break;
                    }
                });
    }
}
