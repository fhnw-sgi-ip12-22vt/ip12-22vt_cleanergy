package com.cleanergy.controller;

import com.cleanergy.model.Dice;
import com.cleanergy.model.GameMaster;
import com.cleanergy.model.Player;
import com.cleanergy.model.State;
import com.cleanergy.model.minigames.Anemometer;
import com.cleanergy.util.mvcbase.ControllerBase;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMasterController extends ControllerBase<GameMaster> {

  Dice dice = new Dice();
  State breakState;

  public GameMasterController(GameMaster model) {
    super(model);
    // initialize the anemometer for the minigame.
    Anemometer.init();
  }

  // start method to get into the tutorial the first state of the game
  public void setPlayerCount(int playerCount) {
    model.setPlayers(playerCount);
    setValue(model.state, State.TUTORIAL);
  }

  public void rollDice() {
    setValue(model.state, State.ROLL);
  }

  public void finishAnemoRoll() {
    model.setPlayerIndex(model.getPlayerIndex() + 1);
  }

  public void finishRoll(int diceRoll) {
    State state = State.DICE;

    // if player is finished with the current roll we end the game.
    if (model.playerFinished(diceRoll)) {
      ArrayList<Integer> winners = new ArrayList<Integer>();
      winners.add(model.getPlayerIndex());
      model.setWinners(winners);
      setValue(model.state, State.END);
      return;
    }
    // if we cross and the next zone is unlocked we cross over normally and move a zone up on the player.
    if (model.crossingZone(diceRoll) && model.nextZoneUnlocked()) {
      model.playerRoll(model.getPlayerIndex(), diceRoll);
      model.getCurrentPlayer().newZone();
      model.setPlayerIndex(model.getPlayerIndex() + 1);
    } else if (!model.crossingZone(diceRoll)) {
      // not crossing any zone so we move normally.
      model.playerRoll(model.getPlayerIndex(), diceRoll);
      model.setPlayerIndex(model.getPlayerIndex() + 1);
    } else {
      // here the nextzone is locked. so we roll until border and start quizborder
      model.rollUntilBorder(diceRoll);
      // instantiate a break gets set at the end
      breakState = State.QUIZBORDER;
      state = State.BREAKSOLO;
    }

    if (model.getPlayerIndex() == model.getPlayerCount()) {
      // plays minigame: quiz, anemo or sun. 
      playMinigame();
    } else {
      setValue(model.isPressed, new Boolean[] { false, false, false, false });
      setValue(model.state, state);
    }
  }

  public void newRound() {
    model.setPlayerIndex(0);
    model.addRound();
    setValue(model.state, State.DICE);
  }

  public void quizFinish(boolean success) {
    model.setPlayerSkip(!success);
    model.setPlayerIndex(model.getPlayerIndex() + 1);
    setValue(model.state, State.DICE);
  }

  public void minigameFinish(ArrayList<Integer> winner) {
    model.setWinners(winner);
    setValue(model.state, State.WINNER);
  }

  public void minigameUpdate(int index) {
    Boolean[] isPressed = new Boolean[4];
    for (int i = 0; i < 4; i++) {
      isPressed[i] = model.isPressed.getValue()[i];
    }
    isPressed[index] = isPressed[index] ? false : true;
    setValue(model.isPressed, isPressed);
  }

  // specifically for finnishing the border quiz.
  public void finishBorderQuiz(Boolean won) {
    Player currentPlayer = model.getCurrentPlayer();
    if (won) {
      currentPlayer.setField(model.getCurrentPlayer().getFieldAfterQuestion());
      currentPlayer.newZone();
      model.setZoneUnlocked(currentPlayer.getZone());
    } 
    model.setPlayerIndex(model.getPlayerIndex()+1);

    // if we are the last player to try the border we actually want to run a minigame and not the dice again.
    if (model.getPlayerIndex() == model.getPlayerCount()) {
      // plays minigame: quiz, anemo or sun. 
      playMinigame();
    } else {
      setValue(model.state, State.DICE);
    }
  }

  // starting the minigame
  public void playMinigame() {
    model.setPlayerIndex(0);
    model.removeWinners();
    breakState = model.selectMiniGame();
    setValue(model.isPressed, new Boolean[] { false, false, false, false });
    setValue(model.state, State.BREAK);
  }

  public void endTutorial() {
    setValue(model.state, State.DICE);
  }

  public void endBreak() {
    setValue(model.state, breakState);
  }

  public State getBreakState() {
    return breakState;
  }

}
