package com.example.findhostel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class HomePage extends AppCompatActivity {
    private TextView logoutView;
    private TextView showHostelsView;
    private FirebaseAuth mAuth;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth = FirebaseAuth.getInstance();
        mp = MediaPlayer.create(this,R.raw.sample);
        logoutView = findViewById(R.id.logout);
        showHostelsView = findViewById(R.id.showHostels);
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                mAuth.signOut();
                startActivity(new Intent(HomePage.this,LoginActivity.class));
            }
        });
        showHostelsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                startActivity(new Intent(HomePage.this,addHostel.class));
            }
        });

    }
}