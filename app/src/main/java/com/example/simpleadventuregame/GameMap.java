package com.example.simpleadventuregame;

import android.media.audiofx.DynamicsProcessing;

import java.io.Serializable;

public class GameMap implements Serializable {
    public static final int N_ROW = 3;
    public static final int N_COL = 3;
    private Area[][] grid = new Area[N_ROW][N_COL];

    public GameMap() {
        grid[0][0] = new Area("Alpha", true);
        grid[0][1] = new Area("Beta", false);
        grid[0][2] = new Area("Gamma",  true);
        grid[1][0] = new Area("Delta",  false);
        grid[1][1] = new Area("Epsilon",  true);
        grid[1][2] = new Area("Zeta",  false);
        grid[2][0] = new Area("Eta",  true);
        grid[2][1] = new Area("Theta",  false);
        grid[2][2] = new Area("Iota",  true);

        // setting three winning items in different area
        grid[0][0].addItem(new Equipment("JADE MONKEY", 10, 2.5));
        grid[0][1].addItem(new Equipment("ROADMAP", 0, 2.5));
        grid[1][1].addItem(new Equipment("MAGIC STONE", 10, 50));
        grid[2][0].addItem(new Equipment("ICE SCRAPPER", 10, 2.5));
        grid[2][2].addItem(new Food("BURGER", 10, 50));
        grid[0][2].addItem(new Food("CANDY", 10, -10));
    }

    public Area getArea(int row, int col) {
        return grid[row][col];
    }
}