package com.example.simpleadventuregame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PickActivity extends AppCompatActivity {
    public static final String CURRENT_STATS = "Current Status";
    Player player;
    GameMap map;
    TextView itemView, priceView, massView;
    TextView currentStatsView, healthView;
    Button confirmButton, backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        player = (Player) getIntent().getSerializableExtra("playerData");
        map = (GameMap) getIntent().getSerializableExtra("mapData");

        itemView = findViewById(R.id.itemViewInPick);
        priceView = findViewById(R.id.itemPriceViewInPick);
        massView = findViewById(R.id.itemMassViewInPick);
        currentStatsView = findViewById(R.id.currentStatsViewInPick);
        healthView = findViewById(R.id.healthViewInPick);
        confirmButton = findViewById(R.id.confirmButtonInPick);
        backButton = findViewById(R.id.backButtonInPick);

        Area area = map.getArea(player.getRowLocation(), player.getColLocation());
        List<Item> items = area.getItems();
        Object[] itemArr = items.toArray();

        // display items stats
        String itemName, itemPrice, itemMass;
        if (itemArr[0] instanceof Equipment) {
            itemName = ((Equipment) itemArr[0]).getDescription();
            itemPrice = "$" + ((Equipment) itemArr[0]).getValue();
            itemMass = ((Equipment) itemArr[0]).getMass() + "g";
            massView.setText(itemMass);
        } else {
            itemName = ((Food) itemArr[0]).getDescription();
            itemPrice = Integer.toString(((Food) itemArr[0]).getValue());
        }
        itemView.setText(itemName);
        priceView.setText(itemPrice);

        // display player stats
        currentStatsView.setText(CURRENT_STATS);
        String healthStr;
        healthStr = "HP: " + player.getHealth();
        healthView.setText(healthStr);

        confirmButton.setOnClickListener(v -> {
            if (itemArr[0] instanceof Equipment) {
                player.getEquipment().add((Equipment) itemArr[0]);
                player.setEquipmentMass(player.getEquipmentMass() +
                                ((Equipment) itemArr[0]).getMass());
            } else if (itemArr[0] instanceof Food) {
                player.setHealth(player.getHealth() + ((Food) itemArr[0]).getHealth());
            }
            Intent intent = new Intent();
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            setResult(PickActivity.RESULT_OK, intent);
            finish();
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            setResult(PickActivity.RESULT_OK, intent);
            finish();
        });
    }
}
