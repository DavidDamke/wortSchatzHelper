package com.example;

import java.awt.Checkbox;

public class Word {
    Checkbox box; 
    String wordName; 

    public Word(Checkbox box, String wordName){
        this.box = box;
        this.wordName= wordName;

    }

    public Checkbox getBox() {
        return box;
    }

    public String getWordName() {
        return wordName;
    }

    public void setBox(Checkbox box) {
        this.box = box;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }
    
}
