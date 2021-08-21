package com.example.simpleadventuregame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import android.widget.Toast;

import java.util.List;

public class WildernessActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PLAY = 1;
    Button pickButton, dropButton, leaveButton;
    Player player;
    GameMap map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild);

        pickButton = findViewById(R.id.pickButton);
        dropButton = findViewById(R.id.dropButton);
        leaveButton = findViewById(R.id.leaveButtonInWild);
        player = (Player) getIntent().getSerializableExtra("playerData");
        map = (GameMap) getIntent().getSerializableExtra("mapData");
        Area area = map.getArea(player.getRowLocation(), player.getColLocation());

        pickButton.setOnClickListener(v -> {
            if (area.getItems().isEmpty()) {
                Toast.makeText(WildernessActivity.this,
                        "No items available!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(WildernessActivity.this, PickActivity.class);
                intent.putExtra("playerData", player);
                intent.putExtra("mapData", map);
                startActivityForResult(intent, REQUEST_CODE_PLAY);
            }
        });

        dropButton.setOnClickListener(v -> {
            Intent intent = new Intent(WildernessActivity.this, DropActivity.class);
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            startActivityForResult(intent, REQUEST_CODE_PLAY);
        });

        leaveButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            setResult(WildernessActivity.RESULT_OK, intent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PLAY && resultCode == RESULT_OK) {
            player = (Player) data.getSerializableExtra("playerData");
            map = (GameMap) data.getSerializableExtra("mapData");

            // check if player wins
            List<Equipment> keyItems = player.getEquipment();
            if (player.hasItem("JADE MONKEY") &&
                    player.hasItem("ICE SCRAPPER") &&
                    player.hasItem("ROADMAP")) {
                System.out.println("Player wins");
                player.setWin(Player.WIN);
                Intent intent = new Intent();
                intent.putExtra("playerData", player);
                intent.putExtra("mapData", map);
                setResult(TownActivity.RESULT_OK, intent);
                finish();
            }

            // check if player lose
            if (player.getHealth() <= 0) {
                player.setWin(Player.LOSE);
                Intent intent = new Intent();
                intent.putExtra("playerData", player);
                intent.putExtra("mapData", map);
                setResult(TownActivity.RESULT_OK, intent);
                finish();
            }
        }
    }
}
