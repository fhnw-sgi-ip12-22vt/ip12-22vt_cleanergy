package com.cleanergy.view;

import com.cleanergy.components.SimpleButton;
import com.cleanergy.components.helpers.PIN;
import com.cleanergy.controller.GameMasterController;
import com.cleanergy.model.GameMaster;
import com.cleanergy.model.State;
import com.cleanergy.util.mvcbase.PuiBase;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfigBuilder;
import com.pi4j.io.gpio.digital.PullResistance;

import java.util.ArrayList;

public class PUI extends PuiBase<GameMaster, GameMasterController> {

  private static ArrayList<SimpleButton> playerButtons = new ArrayList<>();
  private final int[] playerPin = new int[]{21, 20, 15, 16};

  public PUI(GameMasterController controller, Context pi4J) {
    super(controller, pi4J);
  }

  public ArrayList<SimpleButton> getPlayerButtons() {
    return playerButtons;
  }

  /*
  public static int listenButton(int index, State state) {
      ButtonController bc = null;
      switch(state){
          case DICE:
              bc = new DiceController();
              break;
      }
      return bc.press(playerButtons.get(index));
  }

  public int listenAllButtons(){
      StartMenuController sc = new StartMenuController();
      return sc.press(playerButtons);
  }
  */
  @Override
  public void initializeParts() {
    // TODO Auto-generated method stub
    playerButtons.add(new SimpleButton(pi4J, PIN.D17, false));
    playerButtons.add(new SimpleButton(pi4J, PIN.D22, false));
    //playerButtons.add(new SimpleButton(pi4J, PIN.D22, false));
    //playerButtons.add(new SimpleButton(pi4J, PIN.D23, false));
  }

  @Override
  public void setupUiToActionBindings(GameMasterController controller) {
    playerButtons.get(1).onDown(() -> controller.setPlayerCount(2));
    playerButtons.get(2).onDown(() -> controller.setPlayerCount(3));
    playerButtons.get(3).onDown(() -> controller.setPlayerCount(4));
  }

  @Override
  public void setupModelToUiBindings(GameMaster model, GameMasterController controller) {
    onChangeOf(model.state)
        .execute((oldValue, newValue) -> {
          for (int i = 0; i < 4; i++) {
            playerButtons.get(i).onDown(null);
          }
          switch (newValue) {
            case DICE:
              if (model.getPlayerIndex() < model.getPlayerCount()) {
                playerButtons.get(model.getPlayerIndex()).onDown(() -> controller.rollDice());
              }
              break;
            case SONNENMINIGAME, QUIZ, QUIZBORDER:
              playerButtons.get(0).onDown(() -> controller.minigameUpdate(0));
              playerButtons.get(1).onDown(() -> controller.minigameUpdate(1));
              playerButtons.get(2).onDown(() -> controller.minigameUpdate(2));
              playerButtons.get(3).onDown(() -> controller.minigameUpdate(3));
              break;
            case STARTMENU:
              playerButtons.get(1).onDown(() -> controller.setPlayerCount(2));
              playerButtons.get(2).onDown(() -> controller.setPlayerCount(3));
              playerButtons.get(3).onDown(() -> controller.setPlayerCount(4));
              break;
            case TUTORIAL, BREAK, BREAKSOLO:
              playerButtons.get(0).onDown(() -> controller.minigameUpdate(0));
              playerButtons.get(1).onDown(() -> controller.minigameUpdate(1));
              playerButtons.get(2).onDown(() -> controller.minigameUpdate(2));
              playerButtons.get(3).onDown(() -> controller.minigameUpdate(3));
              break;
          }
        });
  } 
}
