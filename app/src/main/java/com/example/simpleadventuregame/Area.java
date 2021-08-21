package com.example.simpleadventuregame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Area implements Serializable {
    private String description;
    private boolean town;
    private List<Item> items;

    public Area(String inDescription, boolean inTown) {
        this.description = inDescription;
        this.town = inTown;
        items = new ArrayList<Item>();
    }

    public void addItem(Item newItem) {
        items.add(newItem);
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean getTown() {
        return this.town;
    }

    public String getDescription() {
        return description;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}
