package com.example;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class FileWriterUtils {
        private final String FILE_NAME = "wortSchatz.txt";
        private final String FILE_NAME_SELECTED = "selectedWortSchatz.txt";

        public void saveWordsToFile(JPanel checkBoxPanel) { //Hier Werden alle Wörter gespeichert vllt brauch ich immer nur das neue.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            
            for (Component component : checkBoxPanel.getComponents()) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                     writer.write(checkBox.getText());
                     writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveWordsToFile(ArrayList<String> wordList) { //Hier Werden alle Wörter gespeichert vllt brauch ich immer nur das neue.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_SELECTED))) {
            
            for (String word : wordList) {
                     writer.write(word);
                     writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveWordToFile(String word) { //Hier Werden alle Wörter gespeichert vllt brauch ich immer nur das neue.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_SELECTED))) {
                    writer.write(word);
                    writer.newLine();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWordsFromFile(JPanel checkBoxPanel) {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String word;
                while ((word = reader.readLine()) != null) {
                    checkBoxPanel.add(new JCheckBox(word));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void removeAllFromSelectedFile() { //Hier Werden alle Wörter gespeichert vllt brauch ich immer nur das neue.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_SELECTED))) {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeWordFromFile(String word, JPanel checkBoxPanel,String FileName) { //Hier Werden alle Wörter gespeichert vllt brauch ich immer nur das neue.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {
            writer.flush();
            for (Component component : checkBoxPanel.getComponents()) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    writer.write(checkBox.getText());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> loadSelectedWordsListFromFile() {
        File file = new File(FILE_NAME_SELECTED);
        ArrayList<String> wordList = new ArrayList<>();
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
        return wordList;
    }
}