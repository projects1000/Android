package com.game.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IdentifyList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idenify_list);
        generateButtons();
    }
    private void generateButtons() {

        Button btnAnimals = findViewById(R.id.btnAnimals);
        btnAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IdentifyList.this, IdentifyObjectsActivity.class);
                startActivity(intent);
            }
        });

        Button btnBirds = findViewById(R.id.btnBirds);
        btnBirds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to IdentifyBirdsActivity
                Intent intent = new Intent(IdentifyList.this, IdentifyBirdsActivity.class);
                startActivity(intent);
            }
        });

        Button btnFlower = findViewById(R.id.btnFlowers);
        btnFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to IdentifyFlowersActivity
                Intent intent = new Intent(IdentifyList.this, IdentifyFlowersActivity.class);
                startActivity(intent);
            }
        });
    }

}
