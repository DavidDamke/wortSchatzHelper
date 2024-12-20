package com.example;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
        frame = new JFrame("WortschatzHelper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
    }
    
    private void initializeCardPanel() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
    }
    public void finalizeUI() {
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
