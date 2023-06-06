package com.cleanergy.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.cleanergy.App;

public class Quiz {
    
    private static List<Question> questions = new ArrayList<>();
    private static int index = -1;
    private static Stack<Question> borderquestions = new Stack<>();

  public static Question getQuestion() {

    if (questions.isEmpty()) {
      fill();
    }
    index++;
    if (index >= questions.size()) {
      index = 0;
    }
    return questions.get(index);
  }

  private static void fill() {
        /*
        try (BufferedReader br = new BufferedReader(new FileReader(App.class.getResource("Questions.csv").toExternalForm()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                questions.add(new Question(values[0], values[1], values[2], values[3], values[4], Integer.parseInt(values[5]), values[6]));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        borderquestions.add(new Question("In welcher Himmelsrichtung sollten die Solarzellen ausgerichtet sein, damit sie am meisten Strom herstellen?",
         "Norden", "Osten", "Süden", "Westen", 2, "Info"));
        borderquestions.add(new Question("Warum werden Windräder auch im Meer gebaut?", "Es weht kräftiger und stetiger", "Auf dem Land verbrauchen sie Platz", "Es kostet weniger Geld", "Verübeln die Aussicht nicht so sehr", 0, "Test"));
        borderquestions.add(new Question("Welchen Vorteil hat Wasserenergie gegenüber andere erneuerbaren Energien?", "Es kostet weniger Geld", "Es ist weniger Wetterabhängig", "Es schadet die Umwelt weniger", "keinen", 1, "Test"));
        questions.add(new Question("Was ist keine erneurbare Energie?", "Kernkraft", "Bioenergie", "Erdwärme", "Wasserkraft", 0, "Test"));
        questions.add(new Question("Wann werden die Atomkraftwerke abgeschaltet?", "2070", "2030", "2050", "2065", 2, "Test"));
        questions.add(new Question("Wieviel Prozent der Schweizer Energie kommt von erneuerbaren Energien?", "50%", "15%", "90%", "70%", 3, "Test"));
        questions.add(new Question("Welche erneuerbare Energie produziert am meisten unseres grünen Stroms?", "Erdwärme", "Wasserkraft", "Bioenergie", "Windenergie", 1, "Test"));
        questions.add(new Question("Wo kann man Erdwärme-Pumpen aufbauen?", "Überall", "Nur in der Nähe von Vulkanen", "Nur in Tropen Gebieten", "Überall ausser in Sandböden", 0,"Test"));
    }

    public static Question getBorderquestion(int zone) {
        return borderquestions.get(zone);
    }

    public static void removeBorderquestion(){
        borderquestions.pop();
    }
}
