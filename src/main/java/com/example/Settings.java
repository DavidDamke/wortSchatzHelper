package com.example;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Settings {
    JPanel settings;
    JPanel navigationPanel;
    JButton settingsButton;
    JPanel inputPanel;
    JLabel textLabel;
    JPanel checkBoxPanel;

    CardLayout cardLayout;
    JPanel cardPanel;

    private ArrayList<JCheckBox> wordList = new ArrayList<>();
    private final String FILE_NAME = "wortSchatz.txt";

    public Settings(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        initSettings();

        setUpButtons();
        setupButtonListeners();

        setupThings();

        loadWordsFromFile();
        addCheckboxesToPanel(); // Ensure checkboxes from file are added to the panel

        addToCardPanel();
    }
    
    private void initSettings() {
        settings = new JPanel();
        settings.setLayout(new BorderLayout()); // Set layout for proper placement
        inputPanel = new JPanel();
        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS)); // Vertical layout for checkboxes
    }

    private void setupThings() {
        textLabel = new JLabel("Words: ");
        JTextField textField = new JTextField(20);

        JButton submitButton = new JButton("ADD");
        submitButton.addActionListener((actionEvent) -> {
            if (!textField.getText().isEmpty()) {
                JCheckBox newWord = new JCheckBox(textField.getText());

                wordList.add(newWord);
                checkBoxPanel.add(newWord); 
                checkBoxPanel.revalidate(); 
                checkBoxPanel.repaint();

                saveWordsToFile();
                textField.setText("");
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitButton.doClick();
                }
            }
        });

        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener((actionEvent) -> {

            // Remove selected checkboxes from the list
            wordList.removeIf(JCheckBox::isSelected);

            updateCheckBoxes(); // Refresh the UI to reflect changes
        });



        inputPanel.add(deleteButton);
        inputPanel.add(textField);
        inputPanel.add(submitButton);

        settings.add(inputPanel, BorderLayout.NORTH);
        settings.add(checkBoxPanel, BorderLayout.CENTER); // Ensure checkboxes are in the center
    }


    private void setUpButtons() {
        navigationPanel = new JPanel();

        settingsButton = new JButton("Go to MainView");
    }

    private void setupButtonListeners() {
        settingsButton.addActionListener((actionEvent) -> cardLayout.show(cardPanel, "FirstPage"));
        navigationPanel.add(settingsButton);

        settings.add(navigationPanel, BorderLayout.SOUTH);
    }

    private void addToCardPanel() {
        cardPanel.add(settings, "SecondPage");
    }

    private void addCheckboxesToPanel() {
        // Add all checkboxes from the wordList to the checkBoxPanel
        for (JCheckBox checkBox : wordList) {
            checkBoxPanel.add(checkBox);
            checkBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println(checkBox.getText() + " is selected");
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    System.out.println(checkBox.getText() + " is deselected");
                }
            });
        }
        checkBoxPanel.revalidate(); // Refresh the UI
        checkBoxPanel.repaint();
    }
    private void updateCheckBoxes(){
        for (JCheckBox checkBox : wordList) {
            checkBoxPanel.add(checkBox);
        }
    }

    private void saveWordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (JCheckBox word : wordList) {
                writer.write(word.getText());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadWordsFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String word;
                while ((word = reader.readLine()) != null) {
                    wordList.add(new JCheckBox(word));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
