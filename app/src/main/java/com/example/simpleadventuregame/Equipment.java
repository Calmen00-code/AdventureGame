package com.example.simpleadventuregame;

import java.io.Serializable;

public class Equipment extends Item implements Serializable {
    private double mass;

    public Equipment(String inDescription, int inVal,
                     double inMass) {
        super(inDescription, inVal);
        this.mass = inMass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }
}
