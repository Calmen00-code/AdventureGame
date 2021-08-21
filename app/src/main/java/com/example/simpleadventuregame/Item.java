package com.example.simpleadventuregame;

import java.io.Serializable;

public abstract class Item implements Serializable {
    private String description;
    private int value;

    public Item(String inDescription, int inVal) {
        this.description = inDescription;
        this.value = inVal;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }
}
