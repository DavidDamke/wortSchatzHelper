package com.example;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WortschatzHelperApp {
 private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private ArrayList<String> wordList = new ArrayList<String>();


    public WortschatzHelperApp(){
        initializeFrame();
        initializeCardPanel();
        setupMainView();
        setupSettingsView();
        finalizeUI();
    }


    private void initializeFrame() {
        frame = new JFrame("WortschatzHelper"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(1000,1000);
    }
    
    private void initializeCardPanel() {
        cardLayout = new CardLayout(); 
        cardPanel = new JPanel(cardLayout);
    }
    
    private void setupMainView() {
        JPanel mainView= new JPanel(); 
        JButton mainViewButton = new JButton("Go to Settings");
        JButton nextWordButton = new JButton("Next");
        JLabel textLabel = new JLabel(); 
        

        textLabel.setText(getRandomWord());
        nextWordButton.addActionListener((actionEvent) -> {
            textLabel.setText(getRandomWord());        
        });
        

        mainViewButton.addActionListener( (actionEvent) -> { cardLayout.show(cardPanel,"SecondPage");    });

        mainView.add(textLabel);
        mainView.add(mainViewButton, BorderLayout.SOUTH);
        mainView.add(nextWordButton, BorderLayout.SOUTH);

        cardPanel.add(mainView,"FirstPage");
    }
    
    private void setupSettingsView() {
        JPanel settings= new JPanel();
        JButton settingsButton = new JButton("Go to MainView");
        settings.add(settingsButton,BorderLayout.SOUTH);

        

        JLabel textLabel = new JLabel();
        JTextField textField = new JTextField(20);

        JButton submitButton = new JButton("ADD"); 

        submitButton.addActionListener((actionEvent) -> {
                System.out.println(textField.getText());

                textLabel.setText(textLabel.getText() + " " + textField.getText());    
                wordList.add(textField.getText());
                textField.setText("");

             });
        settings.add(submitButton);
        settings.add(textField);
        settings.add(textLabel);
        cardPanel.add(settings,"SecondPage");

        settingsButton.addActionListener( (actionEvent) -> { cardLayout.show(cardPanel,"FirstPage");    });
}
    

private void finalizeUI() {
    frame.add(cardPanel);
    frame.setVisible(true);
}

private String getRandomWord(){
    if(wordList.isEmpty()){
        return "";
    }
    Random random = new Random();

    int randomNumber = random.nextInt(wordList.size());

    String randomWord = wordList.get(randomNumber);
    return  randomWord; 
}


}
