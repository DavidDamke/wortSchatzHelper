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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import netscape.javascript.JSObject;

public class FileWriterUtils {
        private final String FILE_NAME_JSON = "Test.json";
        private JSONArray jsonArray;


        public FileWriterUtils() {
            this.jsonArray = getWords();
        }
    
    
        public void saveWordandValue(String name, boolean value, int priority) {
            
            if (!hasWord(name)) {
    
                JSONObject jSONObject = new JSONObject();
    
                jSONObject.put("Name", name);
                jSONObject.put("isSelected", value);
                jSONObject.put("priority", priority);
    
                jsonArray.put(jSONObject);
                saveWordsToJSONFile();
            }

        }

        private void saveWordsToJSONFile() {
              try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_JSON))) {

                writer.write(jsonArray.toString());
                //writer.newLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
        
    }
        public boolean updateWordValue(String name, boolean newValue, int newPriority) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.getString("Name").equals(name)) {
                jsonObject.put("isSelected", newValue); // Update the value
                jsonObject.put("priority", newPriority); // Update the value
                saveWordsToJSONFile(); // Save changes to file
                return true; // Update was successful
            }
        }
        return false; // Word not found
    }
        public boolean updateWordPrio(String name, int newPriority) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("Name").equals(name)) {
                jsonObject.put("priority", newPriority); // Update the value
                saveWordsToJSONFile(); // Save changes to file
                return true; // Update was successful
            }
        }
        return false; // Word not found
    }

    public boolean hasWord(String name) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.getString("Name").equals(name)) {
                return true;
            }

        }
        return false;
    }

    public int getPriority(String name) {
        jsonArray = getWords();
         for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("Name").equals(name)) {
                return jsonObject.getInt("priority");
            }
        }
        return 0; // Word not found
    }
    
    public boolean deleteWord(String name) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.getString("Name").equals(name)) {
                jsonArray.remove(i);
                saveWordsToJSONFile(); // Save changes to file
                return true; // Update was successful
            }
        }
        return false; // Word not found
    }

    public ArrayList<String> getSelectedWords() {
        JSONArray selectedWordsArray = getWords();
        ArrayList<String> selectedWords = new ArrayList<>();
        
        for (int i = 0; i < selectedWordsArray.length(); i++) {
            if (selectedWordsArray.getJSONObject(i).getBoolean("isSelected")) {
                selectedWords.add(selectedWordsArray.getJSONObject(i).getString("Name"));
            }
        }
        return selectedWords;
    }
    
    
    public JSONArray getWords() {
        File file = new File(FILE_NAME_JSON);
  
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder content = new StringBuilder();
                    String line; 
                                  

                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                    return new JSONArray(content.toString());
            
                } catch (IOException e) {
                    e.printStackTrace();
                    return new JSONArray();
                }
            } else {
                return new JSONArray();
            }
    }
}