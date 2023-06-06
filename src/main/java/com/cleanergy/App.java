package com.cleanergy;

import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.view.EmulatorView;
import com.cleanergy.view.GameMasterView;
import com.cleanergy.view.PUI;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {

    GameMaster gm = new GameMaster();
    GameMasterController controller = new GameMasterController(gm);
    //PUI pui = new PUI(controller, Pi4J.newAutoContext());
    //PUI pui = new PUI(controller, Pi4J.newContext());
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("GameMaster.fxml"));

    GameMasterView view = new GameMasterView(controller);
    fxmlLoader.setController(view);

    Parent root = (Parent) fxmlLoader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setFullScreen(true);
    stage.show();

    fxmlLoader = new FXMLLoader(App.class.getResource("Emulator.fxml"));

    //Emulator of PUI Input
    Scene emulatorScene = new Scene(new EmulatorView(controller));
    Stage secondaryStage = new Stage();
    secondaryStage.setTitle("PUI Emulator");
    secondaryStage.setScene(emulatorScene);
    secondaryStage.show();

  }

  public static void main(String[] args) {
    launch();
  }
}