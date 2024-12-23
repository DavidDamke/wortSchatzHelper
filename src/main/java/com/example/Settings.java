package com.example;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

public class Settings {
    JPanel settings;
    JPanel navigationPanel;
    JButton settingsButton;
    JPanel inputPanel;
    JLabel textLabel;
    JPanel checkBoxPanel;

    CardLayout cardLayout;
    JPanel cardPanel;

    FileWriterUtils fileWriterUtils = new FileWriterUtils();

    public Settings(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        initSettings();

        setUpButtons();
        setupButtonListeners();

        setupDelete();

        setUpSubmit();
        setupSelectWords();

        setUpSelectAll();
        initSetupCheckBoxes();

        addToCardPanel();
    }

    private void initSettings() {
        settings = new JPanel();
        settings.setLayout(new BorderLayout()); // Set layout for proper placement
        inputPanel = new JPanel();
        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS)); // Vertical layout for checkboxes
    }

    private void initSetupCheckBoxes() {
        JSONArray wordsArray = fileWriterUtils.getWords();

        for (int i = 0; i < wordsArray.length(); i++) {
            JSONObject word = wordsArray.getJSONObject(i);

            JCheckBox checkBox = new JCheckBox(word.getString("Name"));
            checkBox.setSelected(word.getBoolean("isSelected"));

            checkBoxPanel.add(checkBox);
        }
    }

    private void setUpSubmit() {

        textLabel = new JLabel("Words: ");
        JTextField textField = new JTextField(20);

        JButton submitButton = new JButton("ADD");

        submitButton.addActionListener((actionEvent) -> {
            if (!textField.getText().isEmpty()) {

                JCheckBox checkBox = new JCheckBox(textField.getText());
                checkBoxPanel.add(checkBox);

                updateCheckBoxes();
                fileWriterUtils.saveWordandValue(checkBox.getText(), checkBox.isSelected());
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
        inputPanel.add(textField);
        inputPanel.add(submitButton);

    }

    private void setupSelectWords() {
        JButton selectWords = new JButton("Select");

        ArrayList<String> selectedWordList = new ArrayList<>();
        selectWords.addActionListener((actionEvent) -> {

            for (int i = 0; i < checkBoxPanel.getComponentCount(); i++) {
                if (checkBoxPanel.getComponent(i) instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) checkBoxPanel.getComponent(i);

                    fileWriterUtils.updateWordValue(checkBox.getText(), checkBox.isSelected());

                }
            }
            selectedWordList.clear();
        });

        inputPanel.add(selectWords);
    }

    private void setupDelete() {

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);

        deleteButton.addActionListener((actionEvent) -> {
            for (int i = 0; i < checkBoxPanel.getComponentCount(); i++) {
                if (checkBoxPanel.getComponent(i) instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) checkBoxPanel.getComponent(i);

                    if (checkBox.isSelected()) {
                        checkBoxPanel.remove(i);
                        fileWriterUtils.deleteWord(checkBox.getText());

                    }
                }
            }
            updateCheckBoxes();
        });

        inputPanel.add(deleteButton);

    }

    private void setUpSelectAll() {
        JButton selectAll = new JButton("All");

        selectAll.addActionListener(actionEvent -> {

            for (int i = 0; i < checkBoxPanel.getComponentCount(); i++) {
                if (checkBoxPanel.getComponent(i) instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) checkBoxPanel.getComponent(i);
                    if (selectAll.getText().equals("All")) {
                        checkBox.setSelected(true);
                    } else {
                        checkBox.setSelected(false);
                    }

                }
            }
            if (selectAll.getText().equals("All")) {
                selectAll.setText("None");
            } else {
                selectAll.setText("All");
            }
        });
        inputPanel.add(selectAll);
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
        settings.add(inputPanel, BorderLayout.NORTH);
        settings.add(checkBoxPanel, BorderLayout.CENTER); // Ensure checkboxes are in the center
        cardPanel.add(settings, "SecondPage");
    }

    private void updateCheckBoxes() {
        checkBoxPanel.revalidate(); // Refresh the UI
        checkBoxPanel.repaint();
    }
}
