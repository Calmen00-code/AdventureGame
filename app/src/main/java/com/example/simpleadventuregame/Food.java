package com.example.simpleadventuregame;

import com.example.simpleadventuregame.Item;

import java.io.Serializable;

public class Food extends Item implements Serializable {
    private double health;

    public Food(String inDescription, int inVal,
                double inHealth) {
        super(inDescription, inVal);
        this.health = inHealth;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }
}
