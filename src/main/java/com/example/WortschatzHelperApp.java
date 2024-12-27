package com.example;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class WortschatzHelperApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    public WortschatzHelperApp() {
        initializeFrame();
        initializeCardPanel();
        finalizeUI();
    }
    
    private void initializeFrame() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            UIManager.put("Button.font", new Font("SansSerif", Font.PLAIN, 14)); // Larger font
            UIManager.put("Button.margin", new Insets(10, 20, 10, 20));
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame = new JFrame("WortschatzHelper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
    }
    
    private void initializeCardPanel() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
    }
    private  void finalizeUI() {
        frame.add(cardPanel);
        frame.setVisible(true);
    }
    public JPanel getCardPanel(){
        return cardPanel;
    }
    public CardLayout getCardLayout(){
        return cardLayout;
    }

}
