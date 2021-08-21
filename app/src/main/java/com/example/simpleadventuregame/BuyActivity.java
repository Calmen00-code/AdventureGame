package com.example.simpleadventuregame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;
import android.widget.Toast;

public class BuyActivity extends AppCompatActivity implements Serializable {
    public static final String CURRENT_STATS = "Current Status";
    Player player;
    GameMap map;
    TextView itemView, priceView, massView;
    TextView currentStatsView, healthView, assetView;
    Button confirmButton, backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        itemView = findViewById(R.id.itemViewInPick);
        priceView = findViewById(R.id.itemPriceViewInPick);
        massView = findViewById(R.id.itemMassViewInPick);
        currentStatsView = findViewById(R.id.currentStatsViewInPick);
        healthView = findViewById(R.id.healthViewInPick);
        assetView = findViewById(R.id.moneyViewInPick);
        confirmButton = findViewById(R.id.confirmButtonInPick);
        backButton = findViewById(R.id.backButtonInPick);

        player = (Player) getIntent().getSerializableExtra("playerData");
        map = (GameMap) getIntent().getSerializableExtra("mapData");

        Area area = map.getArea(player.getRowLocation(), player.getColLocation());
        List<Item> items = area.getItems();

        Item retItem = null;
        int price = 0;
        // represent all items info in a string variable
        StringBuilder itemName = new StringBuilder();
        StringBuilder itemPrice = new StringBuilder();
        StringBuilder itemMass = new StringBuilder();
        if (items.isEmpty()) {
            itemView.setText("No Items Available");
            priceView.setText("-");
            massView.setText("-");
        } else {
            for (Item item : items) {
                itemName.append(item.getDescription()).append("\n");
                itemPrice.append(item.getValue()).append("\n");
                // only equipment has mass
                // food is automatically eaten
                if (item instanceof Equipment) {
                    itemMass.append(((Equipment) item).getMass()).append("g\n");
                }
                retItem = item;
            }
            price = Integer.parseInt(itemPrice.toString().replace("\n", ""));

            String priceStr = "$" + itemPrice.toString();
            String massStr = itemMass.toString();

            itemView.setText(itemName.toString());
            priceView.setText(priceStr);
            massView.setText(massStr);
        }

        // player status bar
        currentStatsView.setText(CURRENT_STATS);
        String healthStr = "HP: " + player.getHealth();
        String assetStr = "$" + player.getCash();
        healthView.setText(healthStr);
        assetView.setText(assetStr);

        int finalPrice = price;
        Item finalRetItem = retItem;
        confirmButton.setOnClickListener(v -> {
            if (items.isEmpty()) {
                Toast.makeText(BuyActivity.this,
                        "Item is not available!", Toast.LENGTH_SHORT).show();
            } else {
                if (player.getCash() >= finalPrice) {
                    player.setCash(player.getCash() - finalPrice);
                    if (finalRetItem instanceof Equipment) {
                        player.setEquipmentMass(player.getEquipmentMass() +
                                Double.parseDouble(itemMass.toString().replace("g\n", "")));
                        player.addEquipment((Equipment) finalRetItem);
                    } else if (finalRetItem instanceof Food) {
                        player.setHealth(player.getHealth() + ((Food) finalRetItem).getHealth());
                    }
                    area.removeItem(finalRetItem);
                } else {
                    Toast.makeText(BuyActivity.this,
                            "Insufficient Cash!", Toast.LENGTH_SHORT).show();
                }
            }
            System.out.println("Cash Remain: " + player.getCash());
            Intent intent = new Intent();
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            setResult(BuyActivity.RESULT_OK, intent);
            finish();
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            setResult(BuyActivity.RESULT_OK, intent);
            finish();
        });
    }
}
