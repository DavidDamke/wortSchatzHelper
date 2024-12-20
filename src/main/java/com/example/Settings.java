package com.example;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Settings {
    JPanel settings;
    JPanel navigationPanel;
    JButton settingsButton;
    JPanel inputPanel;

    CardLayout cardLayout;
    JPanel cardPanel;

    private ArrayList<String> wordList = new ArrayList<>();

    public Settings(CardLayout cardLayout, JPanel cardPanel){
        this.cardLayout = cardLayout; 
        this.cardPanel= cardPanel; 
        
        initSettings();        
        
        setUpButtons();
        setupButtonListeners();



        JLabel textLabel = new JLabel("Words: ");
        JTextField textField = new JTextField(20);

        JButton submitButton = new JButton("ADD");
        submitButton.addActionListener((actionEvent) -> {
            if (!textField.getText().isEmpty()) {
                String newWord = textField.getText();
                wordList.add(newWord);
                textLabel.setText(textLabel.getText() + " " + newWord);
                textField.setText(""); // Clear the text field after adding
            }
        });

        inputPanel.add(textLabel);
        inputPanel.add(textField);
        inputPanel.add(submitButton);

        settings.add(inputPanel, BorderLayout.CENTER);

        // Bottom panel for navigation
       addToCardPanel();
        
}
private void initSettings() {
    settings = new JPanel();
    settings.setLayout(new BorderLayout()); // Set layout for proper placement
    inputPanel = new JPanel();
}


private  void setUpButtons(){
    navigationPanel = new JPanel();

    settingsButton = new JButton("Go to MainView");
}

private  void setupButtonListeners(){
 
    settingsButton.addActionListener((actionEvent) -> cardLayout.show(cardPanel, "FirstPage"));
    navigationPanel.add(settingsButton);

    settings.add(navigationPanel, BorderLayout.SOUTH);
}
private  void addToCardPanel(){
    cardPanel.add(settings, "SecondPage");
}

    }
