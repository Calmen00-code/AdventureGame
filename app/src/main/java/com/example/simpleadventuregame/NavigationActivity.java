package com.example.simpleadventuregame;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;

public class NavigationActivity extends AppCompatActivity implements Serializable {

    public static final int REQUEST_CODE_PLAY = 1;
    public static final int UPDATE_INDEX_UNIT = 1;
    public static final String TOWN_MSG = "Welcome to Town";
    public static final String JUNGLE_MSG = "This is Jungle";
    Player player;
    private GameMap map;
    private Button northButton, southButton, westButton, eastButton;
    private Button restartButton, optionButton;
    private TextView gameStatsView, cashView, equipmentMassView, healthView;
    private TextView locationView, areaDescView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        northButton = (Button) findViewById(R.id.northButton);
        southButton = (Button) findViewById(R.id.southButton);
        westButton = (Button) findViewById(R.id.westButton);
        eastButton = (Button) findViewById(R.id.eastButton);
        optionButton = (Button) findViewById(R.id.optionButton);
        restartButton = (Button) findViewById(R.id.restartButton);
        gameStatsView = (TextView) findViewById(R.id.winLoseStatus);
        cashView = (TextView) findViewById(R.id.cashView);
        equipmentMassView = (TextView) findViewById(R.id.equipmentMassView);
        healthView = (TextView) findViewById(R.id.healthView);
        locationView = (TextView) findViewById(R.id.locationView);
        areaDescView = (TextView) findViewById(R.id.areaDescView);

        player = new Player(0, 0, 1000, 100.0);
        map = new GameMap();

        // setting initial value of the player stats
        String cashStr = "$" + player.getCash();
        String hpStr = "HP: " + player.getHealth();
        String massStr = player.getEquipmentMass() + "g";
        cashView.setText(cashStr);
        healthView.setText(hpStr);
        equipmentMassView.setText(massStr);

        updateAttribute();
        northButton.setOnClickListener(v -> {
            if (player.getWin() == Player.WIN || player.getWin() == Player.LOSE) {
                String retMsg = getStatsMsg(player.getWin());
                Toast.makeText(NavigationActivity.this,
                        retMsg, Toast.LENGTH_SHORT).show();
            } else {
                if (player.getRowLocation() < GameMap.N_ROW - 1) {
                    player.setRowLocation(UPDATE_INDEX_UNIT);
                    System.out.println(player.getRowLocation());
                    player.updateHealth();
                    updateAttribute();
                } else {
                    Toast.makeText(NavigationActivity.this,
                            "Row exceeded limit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        southButton.setOnClickListener(v -> {
            if (player.getWin() == Player.WIN || player.getWin() == Player.LOSE) {
                String retMsg = getStatsMsg(player.getWin());
                Toast.makeText(NavigationActivity.this,
                        retMsg, Toast.LENGTH_SHORT).show();
            } else {
                if (player.getRowLocation() > 0) {
                    player.setRowLocation(-UPDATE_INDEX_UNIT);
                    System.out.println(player.getRowLocation());
                    player.updateHealth();
                    updateAttribute();
                } else {
                    Toast.makeText(NavigationActivity.this,
                            "Row exceeded limit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        westButton.setOnClickListener(v -> {
            if (player.getWin() == Player.WIN || player.getWin() == Player.LOSE) {
                String retMsg = getStatsMsg(player.getWin());
                Toast.makeText(NavigationActivity.this,
                        retMsg, Toast.LENGTH_SHORT).show();
            } else {
                if (player.getColLocation() > 0) {
                    player.setColLocation(-UPDATE_INDEX_UNIT);
                    player.updateHealth();
                    updateAttribute();
                } else {
                    Toast.makeText(NavigationActivity.this,
                            "Column exceeded limit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        eastButton.setOnClickListener(v -> {
            if (player.getWin() == Player.WIN || player.getWin() == Player.LOSE) {
                String retMsg = getStatsMsg(player.getWin());
                Toast.makeText(NavigationActivity.this,
                        retMsg, Toast.LENGTH_SHORT).show();
            } else {
                if (player.getColLocation() < GameMap.N_COL - 1) {
                    player.setColLocation(UPDATE_INDEX_UNIT);
                    player.updateHealth();
                    updateAttribute();
                } else {
                    Toast.makeText(NavigationActivity.this,
                            "Column exceeded limit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        optionButton.setOnClickListener(v -> {
            if (player.getWin() == Player.WIN || player.getWin() == Player.LOSE) {
                String retMsg = getStatsMsg(player.getWin());
                Toast.makeText(NavigationActivity.this,
                        retMsg, Toast.LENGTH_SHORT).show();
            } else {
                if (isTown()) {
                    startTownActivity();
                } else {
                    startWildActivity();
                }
            }
        });

        restartButton.setOnClickListener(v -> {
            player = new Player(0, 0, 1000, 100.0);
            cashView.setText(Integer.toString(player.getCash()));
            healthView.setText(Double.toString(player.getHealth()));
            equipmentMassView.setText(Double.toString(player.getEquipmentMass()));
            gameStatsView.setText("Win/Lose");
            map = new GameMap();
            updateAttribute();
        });
    }

    private void startTownActivity() {
        Intent intent = new Intent(NavigationActivity.this, TownActivity.class);
        intent.putExtra("playerData", player);
        intent.putExtra("mapData", map);
        startActivityForResult(intent, REQUEST_CODE_PLAY);
    }

    private void startWildActivity() {
        Intent intent = new Intent(NavigationActivity.this, WildernessActivity.class);
        intent.putExtra("playerData", player);
        intent.putExtra("mapData", map);
        startActivityForResult(intent, REQUEST_CODE_PLAY);
    }

    private void updateAttribute() {
        String updatedHealthStr = "HP: " + player.getHealth();
        healthView.setText(updatedHealthStr);
        if (isTown()) {
            locationView.setText(TOWN_MSG);
        } else {
            locationView.setText(JUNGLE_MSG);
        }
        areaDescView.setText(map.getArea(player.getRowLocation(), player.getColLocation()).getDescription());

        // check if player lose
        if (player.getHealth() <= 0) {
            player.setWin(Player.LOSE);
            gameStatsView.setText("Lose");
            Toast.makeText(NavigationActivity.this,
                    "You lose the game...", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isTown() {
        return map.getArea(player.getRowLocation(), player.getColLocation()).getTown();
    }

    private String getStatsMsg(int stats) {
        if (stats == Player.WIN) {
            return "You won the game! Consider restarting";
        } else if (stats == Player.LOSE) {
            return "You lose... Consider restarting";
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PLAY && resultCode == RESULT_OK) {
            player = (Player) data.getSerializableExtra("playerData");
            map = (GameMap) data.getSerializableExtra("mapData");

            cashView.setText(Integer.toString(player.getCash()));
            healthView.setText(Double.toString(player.getHealth()));
            equipmentMassView.setText(Double.toString(player.getEquipmentMass()));

            if (player.getWin() == Player.WIN) {
                Toast.makeText(NavigationActivity.this,
                        "Congratulations! You won!", Toast.LENGTH_SHORT).show();
                gameStatsView.setText("Win");
            } else if (player.getWin() == Player.LOSE) {
                Toast.makeText(NavigationActivity.this,
                        "You lose the game...", Toast.LENGTH_SHORT).show();
                gameStatsView.setText("Lose");
            }
        }
    }
}