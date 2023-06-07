package com.cleanergy.model;

import java.util.ArrayList;
import com.cleanergy.util.mvcbase.ObservableValue;

public class GameMaster {

  private ArrayList<Player> players = new ArrayList<Player>();
  // we have an easy way to track which of the 4 zones are unlocked here.
  private boolean[] zoneUnlocked = new boolean[4] ;
  public static final int MAXFIELD = 27;
  public static final int ZONEBOUND = 7;
  private int round = 0;
  private int playerCount;
  private int playerIndex = 0;
  private ArrayList<Integer> winners = new ArrayList<>();
  public final ObservableValue<State> state = new ObservableValue<>(State.STARTMENU);
  public final ObservableValue<Boolean[]> isPressed = new ObservableValue<>(
      new Boolean[] { false, false, false, false });
  public final ObservableValue<Integer[]> playerResults = new ObservableValue<>(new Integer[4]);
  private State lastPlayedMinigame = State.ANEMOMINIGAME;

  public void setPlayers(int playerCount) {
    this.playerCount = playerCount;
    for (int i = 0; i < playerCount; i++) {
      players.add(new Player());
    }
  }

  // set zoneunlocked to true for the specific zone index
  public void setZoneUnlocked(int zoneIndex) {
    zoneUnlocked[zoneIndex] = true;
  }

  public void addRound() {
    round++;
  }

  public boolean checkPlayerSkip(int index) {
    Player player = players.get(index);
    if (player.isSkip()) {
      player.setSkip(false);
      return true;
    }
    return false;
  }

  public void playerRoll(int index, int roll) {
    players.get(index).moveField(roll);
  }

  public void rollUntilBorder(int diceRoll) {
    Player player = players.get(getPlayerIndex());
    int wantedField = player.getField() + diceRoll;
    // set the field to last field of the current zone. -1 because we have index field and not the human readable position
    player.setField(((player.getZone() + 1) * 7) - 1);
    player.setFieldAfterQuestion(wantedField);
  }

  // check player position if he needs to answer borderquiz
  public State checkPlayerPosition(int index) {
    Player player = players.get(index);

    if (player.getField() / ZONEBOUND > player.getZone()) {
      // if player is in the final zone
      if (player.getZone() == 3) {
        return State.FINALQUIZ;
      } else {
        player.newZone();
        return State.QUIZ;
      }
    }
    return State.DICE;
  }

  // check if a player has finished the game with the given diceroll
  public Boolean playerFinished(int diceRoll) {
    Boolean finished = false;
    if (getCurrentPlayer().getField() + diceRoll >= MAXFIELD) {
      finished = true;
    }
    return finished;
  }

  // check if next zone is unlocked.
  public Boolean nextZoneUnlocked() {

    Player player = players.get(getPlayerIndex());
    return zoneUnlocked[player.getZone() + 1];
  }

  // returns true if diceroll would cross a zone
  public Boolean crossingZone(int diceRoll) {
    Player player = players.get(getPlayerIndex());
    return (player.getField() + diceRoll) / ZONEBOUND > player.getZone();
  }

  public State selectMiniGame() {
    switch (round % 2) {
      case 0:
        return State.QUIZ;
      case 1:
        // this is switching of the minigames depending on which one was the last one.
        if (lastPlayedMinigame == State.SONNENMINIGAME) {
          this.lastPlayedMinigame = State.ANEMOMINIGAME;
          return State.ANEMOMINIGAME;
        } else {
          this.lastPlayedMinigame = State.SONNENMINIGAME;
          return State.SONNENMINIGAME;
        }
    }
    return null;
  }

  public void setPlayerSkip(boolean skip) {
    players.get(playerIndex).setSkip(skip);
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public int getPlayerIndex() {
    return playerIndex;
  }

  public Player getCurrentPlayer() {
    return players.get(getPlayerIndex());
  }

  public void setPlayerIndex(int playerIndex) {
    this.playerIndex = playerIndex;
  }

  public int getPlayerCount() {
    return playerCount;
  }

  public int getRound() {
    return round;
  }

  public ArrayList<Integer> getWinners() {
    return winners;
  }

  public void setWinners(ArrayList<Integer> winners) {
    for (int i : winners) {
      players.get(i).setAdvantage(true);
    }
    this.winners = winners;
  }

  public void removeWinners() {
    if (winners != null) {
      for (int i : winners) {
        players.get(i).setAdvantage(false);
      }
      winners = null;
    }
  }

}
