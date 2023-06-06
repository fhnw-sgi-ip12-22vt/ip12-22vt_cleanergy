package com.cleanergy.model;

public class Player {

  private int field = 0;
  private int fieldAfterBorderQuestion;
  private int zone = 0;
  private boolean skip = false;
  private boolean advantage = false;
  private String playerName = "Player 1";


  public String getName() {
    return playerName;
  }

  public void setName(String playerName) {
    this.playerName = playerName;
  }

  public int getZone() {
    return zone;
  }

  public int getField() {
    return field;
  }

  public void setField(int field) {
    this.field = field;
  }

  // this holds the field value we want to be after question
  public void setFieldAfterQuestion(int field) {
    this.fieldAfterBorderQuestion = field;
  }

  public int getFieldAfterQuestion() {
    return this.fieldAfterBorderQuestion;
  }

  public boolean isSkip() {
    return skip;
  }

  public void setSkip(boolean skip) {
    this.skip = skip;
  }

  public void moveField(int fieldcount) {
    field += fieldcount;
  }

  public void newZone() {
    zone++;
  }

  // relouded equals() function for junit test to work right
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    return that != null && that.getClass() == getClass()
        && ((Player) that).field == field
        && ((Player) that).zone == zone
        && ((Player) that).skip == skip
        && ((Player) that).playerName == playerName;
  }

  public boolean isAdvantage() {
    return advantage;
  }

  public void setAdvantage(boolean advantage) {
    this.advantage = advantage;
  }

}
