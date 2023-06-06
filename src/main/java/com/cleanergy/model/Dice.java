package com.cleanergy.model;

import java.util.Random;

public class Dice {

  private final int MIN = 1;
  private final int MAX = 6;

  public int rollDice() {
    Random random = new Random();
    return random.nextInt(MAX + 1 - MIN) + MIN;
  }
}
