//package com.game.myapplication01;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.bumptech.glide.Glide;
//
//public class BehavioralActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_behaviour);  // Use the correct layout file
//
//        // Initialize the ImageView where the GIF will be displayed
//        ImageView gifImageView = findViewById(R.id.catImage);
//        Button behavioralButton = findViewById(R.id.btnBehavioral);
//
//        // Set OnClickListener on the "Behavioral" button to display the GIF
//        behavioralButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // When the button is clicked, make the ImageView visible
//                gifImageView.setVisibility(View.VISIBLE);
//
//                // Load the GIF using Glide (from the raw folder)
//                Glide.with(BehavioralActivity.this)
//                        .asGif()  // Specify to load a GIF
//                        .load(R.raw.c)  // Ensure the 'catgif' GIF is in the 'raw' folder
//                        .into(gifImageView);  // Load the GIF into the ImageView
//            }
//        });
//    }
//}
