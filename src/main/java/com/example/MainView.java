package com.example;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainView {
    JPanel mainView;
    JLabel textLabel;
    JPanel buttonPanel;
    JButton mainViewButton;
    JButton nextWordButton;

    JPanel cardPanel;
    CardLayout cardLayout; 

    private ArrayList<String> wordList = new ArrayList<>();
    private final String FILE_NAME = "wortSchatz.txt";


    public MainView(CardLayout cardLayout,JPanel cardPanel){
                this.cardLayout = cardLayout; 
                this.cardPanel = cardPanel;

                initMainView();

                // Main label displaying the word
                displayWord();
                // Panel for buttons
                setUpButtons();
                // Add mainView to the card panel
                setupButtonListeners();
                addToCardPanel();
            }


    private  void setUpButtons(){
        buttonPanel = new JPanel();

        // Button to switch to settings
        mainViewButton = new JButton("Go to Settings");
        nextWordButton = new JButton("Next");

    }
    
    private void setupButtonListeners(){
      
        mainViewButton.addActionListener((actionEvent) -> cardLayout.show(cardPanel, "SecondPage"));
        buttonPanel.add(mainViewButton);

        // Button to get the next word
        nextWordButton.addActionListener((actionEvent) -> textLabel.setText(getRandomWord()));
        buttonPanel.add(nextWordButton);

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
        loadWordsFromFile();
        if (wordList.isEmpty()) {
            return "No Words Available";
        }
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
    }

    private void loadWordsFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String word;
                while ((word = reader.readLine()) != null) {
                    wordList.add(word);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
