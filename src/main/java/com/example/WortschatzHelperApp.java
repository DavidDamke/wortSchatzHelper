package com.example;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WortschatzHelperApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private ArrayList<String> wordList = new ArrayList<>();
    
    public WortschatzHelperApp() {
        initializeFrame();
        initializeCardPanel();
        setupMainView();
        setupSettingsView();
        finalizeUI();
    }
    
    private void initializeFrame() {
        frame = new JFrame("WortschatzHelper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
    }
    
    private void initializeCardPanel() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
    }
    private void finalizeUI() {
        frame.add(cardPanel);
        frame.setVisible(true);
    }
    
    private void setupMainView() {
        JPanel mainView = new JPanel();
        mainView.setLayout(new BorderLayout()); // Set layout for proper placement

        // Main label displaying the word
        JLabel textLabel = new JLabel(getRandomWord());
        textLabel.setFont(new Font("Arial", Font.BOLD, 100));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        mainView.add(textLabel, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();

        // Button to switch to settings
        JButton mainViewButton = new JButton("Go to Settings");
        mainViewButton.addActionListener((actionEvent) -> cardLayout.show(cardPanel, "SecondPage"));
        buttonPanel.add(mainViewButton);

        // Button to get the next word
        JButton nextWordButton = new JButton("Next");
        nextWordButton.addActionListener((actionEvent) -> textLabel.setText(getRandomWord()));
        buttonPanel.add(nextWordButton);

        mainView.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom

        // Add mainView to the card panel
        cardPanel.add(mainView, "FirstPage");
    }

    private void setupSettingsView() {
        JPanel settings = new JPanel();
        settings.setLayout(new BorderLayout()); // Set layout for proper placement

        // Top panel for input
        JPanel inputPanel = new JPanel();

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
        JPanel navigationPanel = new JPanel();

        JButton settingsButton = new JButton("Go to MainView");
        settingsButton.addActionListener((actionEvent) -> cardLayout.show(cardPanel, "FirstPage"));
        navigationPanel.add(settingsButton);

        settings.add(navigationPanel, BorderLayout.SOUTH);

        // Add settings to the card panel
        cardPanel.add(settings, "SecondPage");
    }


    private String getRandomWord() {
        if (wordList.isEmpty()) {
            return "No Words Available";
        }
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
    }
}
