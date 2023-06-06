package com.cleanergy.model.minigames;

import java.util.Scanner;

public class Estimate {

  final String[] Estimation1 = {"Wie hoch ist die höchste Staumauer der Schweiz?", "285"};
  final String[] Estimation2 = {"Wie viele Wasserkraftwerke gibt es in der Schweiz?", "638"};
  final String[] Estimation3 = {"Wie hoch ist ein durchschnittliches Windrad?", "90"};
  final String[] Estimation4 = {
      "Bis wann soll die Energieversorgung in der Schweiz nur noch mit erneuerbaren Energien stattfinden?",
      "2050"};
  private String question;
  private String answer;
  private static int CountingRounds;
  final static int Round = 1;
  private static int points = 0; // we can use points for user in this game
  public static String userAnswer;

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    setCountingRounds(Round);
    Estimate estimate = new Estimate();
    System.out.println(estimate.getQuestion());
    System.out.print("Input your unswer: "); // user inputs an answer
    userAnswer = in.nextLine();
    System.out.println("The right answer is " + estimate.getAnswer());
    if (userAnswer.equals(estimate.getAnswer())) {
      System.out.println("Your uswer is right");
      estimate.addPoints();
    } else {
      System.out.println("Your answer us false");
    }
    in.close();
  }

  public Estimate() {
    //Define Question based on round
    if (getCountingRounds() == 1) {
      this.question = Estimation1[0];
    } else if (getCountingRounds() == 2) {
      this.question = Estimation2[0];
    } else if (getCountingRounds() == 3) {
      this.question = Estimation3[0];
    } else if (getCountingRounds() == 4) {
      this.question = Estimation4[0];
    }

    //Define Answer based on round
    if (getCountingRounds() == 1) {
      this.answer = Estimation1[1];
    } else if (getCountingRounds() == 2) {
      this.answer = Estimation2[1];
    } else if (getCountingRounds() == 3) {
      this.answer = Estimation3[1];
    } else if (getCountingRounds() == 4) {
      this.answer = Estimation4[1];
    }
  }

  public String getAnswer() {
    return this.answer;
  }

  public String getQuestion() {
    return this.question;
  }

  public static void setCountingRounds(int round) {
    CountingRounds += round;
  }

  public static int getCountingRounds() {
    return CountingRounds;
  }

  // this function adds user's points
  public void addPoints() {
    points += 1;
  }

  // this function returns user's points
  public int getPoints() {
    return this.points;
  }
}
