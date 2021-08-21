package com.example.simpleadventuregame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.List;

public class TownActivity extends AppCompatActivity implements Serializable {
    public static final int REQUEST_CODE_PLAY = 1;
    Button buyButton, sellButton, leaveButton;
    Player player;
    GameMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        buyButton = (Button) findViewById(R.id.buyButton);
        sellButton = (Button) findViewById(R.id.sellButton);
        leaveButton = (Button) findViewById(R.id.leaveButton);

        player = (Player) getIntent().getSerializableExtra("playerData");
        map = (GameMap) getIntent().getSerializableExtra("mapData");
        System.out.println("Cash Available: " + player.getCash());

        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(TownActivity.this, BuyActivity.class);
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            startActivityForResult(intent, REQUEST_CODE_PLAY);
        });

        sellButton.setOnClickListener(v -> {
            Intent intent = new Intent(TownActivity.this, SellActivity.class);
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            startActivityForResult(intent, REQUEST_CODE_PLAY);
        });

        leaveButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("playerData", player);
            intent.putExtra("mapData", map);
            setResult(TownActivity.RESULT_OK, intent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PLAY && resultCode == RESULT_OK) {
            // Intent intent = new Intent(data);
            player = (Player) data.getSerializableExtra("playerData");
            map = (GameMap) data.getSerializableExtra("mapData");
            System.out.println("Cash Available (Returned): " + player.getCash());

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
