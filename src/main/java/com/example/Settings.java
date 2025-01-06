package com.example;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class Settings {
    JPanel settings;
    JPanel navigationPanel;
    JButton settingsButton;
    JPanel checkBoxPanel;


    JPanel toolBar;
    CardLayout cardLayout;
    JPanel cardPanel;

    FileWriterUtils fileWriterUtils = new FileWriterUtils();

     DefaultListModel<String> lowPriorityModel;
     DefaultListModel<String> mediumPriorityModel;
     DefaultListModel<String> highPriorityModel;

     public Settings(CardLayout cardLayout, JPanel cardPanel) {
         this.cardLayout = cardLayout;
         this.cardPanel = cardPanel;

         initSettings();

         setNavigation();

         setUpToolBar();

         //initSetupCheckBoxes();
         testTree();
         setUpPriorityLists();
         addToCardPanel();
     }
     

    private void initSettings() {
        settings = new JPanel();
        settings.setLayout(new BorderLayout()); // Set layout for proper placement
        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS)); // Vertical layout for checkboxes
    }
     private void testTree() {
         DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
         DefaultMutableTreeNode folder1 = new DefaultMutableTreeNode("folder1");
         folder1.add(new DefaultMutableTreeNode(new JCheckBox("Test").getText()));

         DefaultMutableTreeNode folder2 = new DefaultMutableTreeNode("folder2");
         DefaultMutableTreeNode folder3 = new DefaultMutableTreeNode("folder3");
         root.add(folder1);
         root.add(folder2);
         root.add(folder3);
         

         DefaultTreeModel treeModel = new DefaultTreeModel(root);
         JTree tree = new JTree(treeModel);
         tree.setEditable(true);
         tree.setRootVisible(false);

         JPanel test = new JPanel(new BorderLayout());
         test.setPreferredSize(new Dimension(200, 200));
         test.add(new JScrollPane(tree));
         settings.add(test,BorderLayout.WEST);
        

     }
    private void initSetupCheckBoxes() {
        JSONArray wordsArray = fileWriterUtils.getWords();
        JScrollPane checkBoxJScrollPanel = new JScrollPane(checkBoxPanel);
        checkBoxJScrollPanel.setBorder(BorderFactory.createTitledBorder("Wörter"));
        checkBoxJScrollPanel.setPreferredSize(new Dimension(200, 400));

        for (int i = 0; i < wordsArray.length(); i++) {
            JSONObject word = wordsArray.getJSONObject(i);

            JCheckBox checkBox = new JCheckBox(word.getString("Name"));
            checkBox.setSelected(word.getBoolean("isSelected"));

            checkBoxPanel.add(checkBox);
        }
        settings.add(checkBoxJScrollPanel, BorderLayout.WEST);
    }

    private void refreshPriorityList() {
        //TODO Darf nur Adden wenn die nicht Selected sind. 

        lowPriorityModel.clear();
        mediumPriorityModel.clear();
        highPriorityModel.clear();
        updatePriorityModels();
        updateCheckBoxes();
        
    }
    private void setUpPriorityLists() {
        JPanel prioPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        lowPriorityModel = new DefaultListModel<>();
        mediumPriorityModel = new DefaultListModel<>();
        highPriorityModel = new DefaultListModel<>();

        updatePriorityModels();
        

        JScrollPane lowPriorityScrollPane = createPriorityScrollPane("Low Priority", lowPriorityModel,1);
        JScrollPane mediumPriorityScrollPane = createPriorityScrollPane("Medium Priority", mediumPriorityModel,2);
        JScrollPane highPriorityScrollPane = createPriorityScrollPane("High Priority", highPriorityModel,3);

        prioPanel.add(highPriorityScrollPane);
        prioPanel.add(mediumPriorityScrollPane);
        prioPanel.add(lowPriorityScrollPane);
        settings.add(prioPanel, BorderLayout.CENTER);
    }

    private void updatePriorityModels() {
        JSONArray wordsArray = fileWriterUtils.getSelectedWordsASJSON();

        for (int i = 0; i < wordsArray.length(); i++) {
            JSONObject word = wordsArray.getJSONObject(i);
            int prio = word.getInt("priority");
            String name = word.getString("Name");
            
            updateModels(name,prio);
        }
    }

    private void updateModels(String name, int prio) {
        switch (prio) {
            case 1:
                lowPriorityModel.addElement(name);;
                break;
            case 2:
            
            mediumPriorityModel.addElement(name);;
            break;
            case 3:
            
            highPriorityModel.addElement(name);;
            break;   
            default:
                break;
        }
    }

    private JScrollPane createPriorityScrollPane(String title, DefaultListModel<String> model, int priority) {
        JList<String> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setDragEnabled(true);
        list.setTransferHandler(new ListTransferHandler(priority, fileWriterUtils));
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createTitledBorder(title));
        return scrollPane;
    }
    
    private void setUpToolBar() {
        toolBar = new JPanel();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        
          
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);

        JTextField textField = new JTextField(20);
        
        JButton submitButton = new JButton("ADD");
        
        JButton selectWords = new JButton("Select");
        JButton selectAll = new JButton("All");
        JButton refreshButton = new JButton("Refresh");
        
        ArrayList<String> selectedWordList = new ArrayList<>();

        deleteButton.addActionListener((actionEvent) -> delete());
        submitButton.addActionListener((actionEvent) -> submit(textField)); 
        selectWords.addActionListener((actionEvent) -> selectWords(selectedWordList));
        selectAll.addActionListener(actionEvent -> selectAll(selectAll));
        refreshButton.addActionListener(actionEvent -> refreshPriorityList());
        
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitButton.doClick();
                }
            }
        });

        toolBar.add(deleteButton);
        toolBar.add(textField);
        toolBar.add(submitButton);
        toolBar.add(selectWords);
        toolBar.add(selectAll);
        toolBar.add(refreshButton);
        settings.add(toolBar,BorderLayout.NORTH);

    }

    private void delete() {
        //Todo auch aus der Prioliste raus
        ArrayList<JCheckBox> toRemove = new ArrayList<>();
        
        for (int i = 0; i < checkBoxPanel.getComponentCount(); i++) {
            if (checkBoxPanel.getComponent(i) instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) checkBoxPanel.getComponent(i);

                if (checkBox.isSelected()) {
                    toRemove.add(checkBox);
                    fileWriterUtils.deleteWord(checkBox.getText());
                }
            }
        }
        for (JCheckBox checkBox : toRemove) {
            checkBoxPanel.remove(checkBox);
        }

        updateCheckBoxes();
    }

    private void submit(JTextField textField) {
        if (!textField.getText().isEmpty() && !hasWord(textField.getText())) {

            JCheckBox checkBox = new JCheckBox(textField.getText());
            checkBoxPanel.add(checkBox);

            updateCheckBoxes();
            fileWriterUtils.saveWordandValue(checkBox.getText(), checkBox.isSelected(),3);
            
        }
        textField.setText("");
    }

    private boolean hasWord(String name) {
            for (Component checkBoxcomp : checkBoxPanel.getComponents()) {
        JCheckBox box = (JCheckBox) checkBoxcomp;
        if(box.getText().equals(name)){
            return true;
        }

            }
            return false;
    }

    private void selectWords(ArrayList<String> selectedWordList) {
        
        for (int i = 0; i < checkBoxPanel.getComponentCount(); i++) {
            if (checkBoxPanel.getComponent(i) instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) checkBoxPanel.getComponent(i);

                
                if (checkBox.isSelected() && !highPriorityModel.contains(checkBox.getText())&& !lowPriorityModel.contains(checkBox.getText())&& !mediumPriorityModel.contains(checkBox.getText())) {
                    highPriorityModel.addElement(checkBox.getText());
                    fileWriterUtils.updateWordValue(checkBox.getText(), checkBox.isSelected(),3);
                } else if (!checkBox.isSelected()){
                    lowPriorityModel.removeElement(checkBox.getText());
                    highPriorityModel.removeElement(checkBox.getText());
                    mediumPriorityModel.removeElement(checkBox.getText());
                }

            }
        }
        selectedWordList.clear();
    }

    private void selectAll(JButton selectAll) {
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
    }


    private void setNavigation() {
        navigationPanel = new JPanel();

        settingsButton = new JButton("Go to MainView");
        settingsButton.addActionListener((actionEvent) -> cardLayout.show(cardPanel, "FirstPage"));
        navigationPanel.add(settingsButton);
        settings.add(navigationPanel, BorderLayout.SOUTH);
    }



    private void addToCardPanel() {
        cardPanel.add(settings, "SecondPage");
    }

    private void updateCheckBoxes() {
        checkBoxPanel.revalidate(); // Refresh the UI
        checkBoxPanel.repaint();
    }
}
