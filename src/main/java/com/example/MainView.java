package com.example;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainView {
    JPanel mainView;
    JLabel textLabel;
    JPanel buttonPanel;
   
    JPanel cardPanel;
    CardLayout cardLayout;

    FileWriterUtils fileWriterUtils = new FileWriterUtils();

    private ArrayList<String> wordList = new ArrayList<>();


    public MainView(CardLayout cardLayout,JPanel cardPanel){
                this.cardLayout = cardLayout; 
                this.cardPanel = cardPanel;

                initMainView();

                displayWord();
                setUpButtons();
                addToCardPanel();
            }


    private  void setUpButtons(){
        buttonPanel = new JPanel();

        // Button to switch to settings
        JButton mainViewButton = new JButton("Settings");
       // FontIcon settingsIcon = FontIcon.of(MaterialDesignC.COG, 16, Color.BLACK);

        //mainViewButton.setIcon(settingsIcon);
        JButton nextWordButton = new JButton("Next");

        JButton gutButton = new JButton("GUT");
        JButton schlechtButton = new JButton("SCHLECHT");

       
        gutButton.addActionListener(actionEvent -> {
            int prio = fileWriterUtils.getPriority(textLabel.getText());

            if (prio == 3 || prio == 2) {
                fileWriterUtils.updateWordPrio(textLabel.getText(), prio -=1);  
            }
         
            textLabel.setText(getRandomWord());
        });
        schlechtButton.addActionListener(actionEvent -> {
            int prio = fileWriterUtils.getPriority(textLabel.getText());

            if (prio <3) {
                fileWriterUtils.updateWordPrio(textLabel.getText(), prio += 1);
                
            }
          
            textLabel.setText(getRandomWord());
        });
        
        mainViewButton.addActionListener((actionEvent) -> cardLayout.show(cardPanel, "SecondPage"));
        
        // Button to get the next word
        nextWordButton.addActionListener((actionEvent) -> textLabel.setText(getRandomWord()));

        buttonPanel.add(mainViewButton);
        buttonPanel.add(nextWordButton);
        buttonPanel.add(gutButton);
        buttonPanel.add(schlechtButton);

        mainView.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom

    }
    
    
    private void initMainView(){
        mainView = new JPanel();
        mainView.setLayout(new BorderLayout());
    }
    private void displayWord(){
        textLabel = new JLabel(getRandomWord());
        textLabel.setFont(new Font("Arial", Font.BOLD, 100));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        mainView.add(textLabel, BorderLayout.CENTER);
    }
    private void addToCardPanel(){
        cardPanel.add(mainView, "FirstPage");
    }

   private String getRandomWord() {
    wordList = new FileWriterUtils().getSelectedWords();
    

    System.out.println(wordList);
    if (wordList.isEmpty()) {
        return "Keine Wörter verfügbar";
    }
    System.out.println("After");

    // Create a weighted list based on priorities
    ArrayList<String> weightedWords = new ArrayList<>();

    for (String word : wordList) {
        int priority = fileWriterUtils.getPriority(word);

        System.out.println(priority);
        // Add the word to the weighted list based on its priority
        for (int i = 0; i < priority; i++) {
            weightedWords.add(word);
        }
    }
    if (!weightedWords.isEmpty()) {
        Random random = new Random();
        return weightedWords.get(random.nextInt(weightedWords.size()));
    }
    return "Keine Wörter verfügbar";
    // Select a random word from the weighted list
}

}
