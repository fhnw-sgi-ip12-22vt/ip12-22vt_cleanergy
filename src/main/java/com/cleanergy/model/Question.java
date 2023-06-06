package com.cleanergy.model;

public class Question {

  private String question;
  private String anwser1;
  private String anwser2;
  private String anwser3;
  private String anwser4;
  private int correct;
  private String information;

  public Question(String question, String anwser1, String anwser2, String anwser3, String anwser4,
      int correct,
      String information) {
    this.question = question;
    this.anwser1 = anwser1;
    this.anwser2 = anwser2;
    this.anwser3 = anwser3;
    this.anwser4 = anwser4;
    this.correct = correct;
    this.information = information;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnwser1() {
    return anwser1;
  }

  public void setAnwser1(String anwser1) {
    this.anwser1 = anwser1;
  }

  public String getAnwser2() {
    return anwser2;
  }

  public void setAnwser2(String anwser2) {
    this.anwser2 = anwser2;
  }

  public String getAnwser3() {
    return anwser3;
  }

  public void setAnwser3(String anwser3) {
    this.anwser3 = anwser3;
  }

  public String getAnwser4() {
    return anwser4;
  }

  public void setAnwser4(String anwser4) {
    this.anwser4 = anwser4;
  }

  public int getCorrect() {
    return correct;
  }

  public void setCorrect(int correct) {
    this.correct = correct;
  }

  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }


}
