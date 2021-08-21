package com.example.simpleadventuregame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.widget.Toast;

public class SellActivity extends AppCompatActivity {
    TextView itemView, priceView, massView;
    TextView currentStatus, cashStatus;
    Button confirmButton, leaveButton;
    Button nextButton, prevButton;
    Player player;
    GameMap map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        itemView = findViewById(R.id.itemNameViewInDrop);
        priceView = findViewById(R.id.priceViewInDrop);
        massView = findViewById(R.id.massViewInDrop);
        currentStatus = findViewById(R.id.currentStatsViewInSell);
        cashStatus = findViewById(R.id.cashViewInSell);
        confirmButton = findViewById(R.id.confirmButtonInDrop);
        leaveButton = findViewById(R.id.leaveButtonInDrop);
        nextButton = findViewById(R.id.nextButtonInDrop);
        prevButton = findViewById(R.id.prevButtonInDrop);

        player = (Player) getIntent().getSerializableExtra("playerData");
        map = (GameMap) getIntent().getSerializableExtra("mapData");

        Area area = map.getArea(player.getRowLocation(), player.getColLocation());
        List<Equipment> equipments = player.getEquipment();
        Object[] eqArr = equipments.toArray();
        Equipment currentEq = null;

        // item status
        if (equipments.isEmpty()) {
            itemView.setText("No Items Available");
            priceView.setText("-");
            massView.setText("-");
        } else {
            String initialItem = ((Equipment) eqArr[0]).getDescription();
            String initialPrice = "$" + ((Equipment) eqArr[0]).getValue();
            String initialMass = ((Equipment) eqArr[0]).getMass() + "g";

            itemView.setText(initialItem.toString());
            priceView.setText(initialPrice);
            massView.setText(initialMass);
        }

        // player status bar
        String assetStr = "$" + player.getCash();
        cashStatus.setText(assetStr);

        // displaying items
        AtomicInteger finalIdx = new AtomicInteger(0);
        nextButton.setOnClickListener(v -> {
            if (equipments.isEmpty()) {
                Toast.makeText(SellActivity.this,
                        "You don't have any item to be sold!", Toast.LENGTH_SHORT).show();
            } else {
                int idx = finalIdx.incrementAndGet();
                if (idx >= 0 && idx < eqArr.length) {
                    if (eqArr[idx] instanceof Equipment) {
                        String priceMsg = "$" + ((Equipment) eqArr[idx]).getValue();
                        String massMsg = ((Equipment) eqArr[idx]).getMass() + "g";
                        itemView.setText(((Equipment) eqArr[idx]).getDescription());
                        priceView.setText(priceMsg);
                        massView.setText(massMsg);
                    }
                } else {
                    Toast.makeText(SellActivity.this,
                            "No more items to be shown!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        prevButton.setOnClickListener(v -> {
            if (equipments.isEmpty()) {
                Toast.makeText(SellActivity.this,
                        "You don't have any item to be sold!", Toast.LENGTH_SHORT).show();
            } else {
                int idx = finalIdx.decrementAndGet();
                if (idx >= 0 && idx < eqArr.length) {
                    if (eqArr[idx] instanceof Equipment) {
                        String priceMsg = "$" + ((Equipment) eqArr[idx]).getValue();
                        String massMsg = ((Equipment) eqArr[idx]).getMass() + "g";
                        itemView.setText(((Equipment) eqArr[idx]).getDescription());
                        priceView.setText(priceMsg);
                        massView.setText(massMsg);
                    }
                } else {
                    Toast.makeText(SellActivity.this,
                            "No more items to be shown!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // end of displaying items

        currentEq = (Equipment) eqArr[finalIdx.get()];
        Equipment finalCurrentEq = currentEq;
        confirmButton.setOnClickListener(v -> {
            if (equipments.isEmpty()) {
                Toast.makeText(SellActivity.this,
                        "Items is not available!", Toast.LENGTH_SHORT).show();
            } else {
                player.setEquipmentMass(player.getEquipmentMass() - ((Equipment) finalCurrentEq).getMass());
                player.setCash((int) (player.getCash() + (0.75 * ((Equipment) finalCurrentEq).getValue())));
                area.addItem((Equipment) finalCurrentEq);
                player.removeEquipment((Equipment) finalCurrentEq);
                Intent intent = new Intent();
                intent.putExtra("playerData", player);
                intent.putExtra("mapData", map);
                setResult(SellActivity.RESULT_OK, intent);
                finish();
            }
        });

        leaveButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            setResult(SellActivity.RESULT_OK, intent);
            finish();
        });
    }
}
