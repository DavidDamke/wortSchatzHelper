package com.example;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {

       WortschatzHelperApp wha =  new WortschatzHelperApp();

       JPanel cardPanel = wha.getCardPanel(); 
       CardLayout cardLayout = wha.getCardLayout();
    
      new MainView(cardLayout,cardPanel); 

      new Settings(cardLayout, cardPanel);
     
    }

    
}