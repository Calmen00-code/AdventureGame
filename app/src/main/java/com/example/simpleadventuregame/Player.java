package com.example.simpleadventuregame;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simpleadventuregame.Equipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Player implements Serializable {
    public static final int NEUTRAL = 0;
    public static final int LOSE = -1;
    public static final int WIN = 1;

    private int rowLocation;
    private int colLocation;
    private int cash;
    private double health;
    private double equipmentMass;
    private int winStats;
    private List<Equipment> equipments;

    public Player(int row, int col, int cash, double health) {
        this.rowLocation = row;
        this.colLocation = col;
        this.cash = cash;
        this.health = health;
        equipmentMass = 0.0;
        winStats = NEUTRAL;
        equipments = new ArrayList<Equipment>();
    }

    public void addEquipment(Equipment eq) {
        equipments.add(eq);
    }

    public void removeEquipment(Equipment eq) { equipments.remove(eq); }

    public void setWin(int win) {
        this.winStats = win;
    }


    public void setRowLocation(int rowUnit) {
        this.rowLocation += rowUnit;
    }

    public void setColLocation(int colUnit) {
        this.colLocation += colUnit;
    }

    public void updateHealth() {
        this.health = Math.max(0.0, this.health - 5.0 - (equipmentMass / 2.0));
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void setEquipmentMass(double equipmentMass) {
        this.equipmentMass = equipmentMass;
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }

    public int getCash() {
        return cash;
    }

    public List<Equipment> getEquipment() {
        return equipments;
    }

    public double getEquipmentMass() {
        return equipmentMass;
    }

    public double getHealth() {
        return health;
    }

    public int getWin() {
        return winStats;
    }

    public boolean hasItem(String itemName) {
        for (Equipment eq: equipments) {
            if (eq.getDescription().equals(itemName)) {
                return true;
            }
        }
        return false;
    }
}
