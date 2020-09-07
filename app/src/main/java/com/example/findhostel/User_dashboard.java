package com.example.findhostel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User_dashboard extends AppCompatActivity {
    private TextView logoutText;
    private TextView showHostelsView;
    private FirebaseAuth mAuth;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        mAuth = FirebaseAuth.getInstance();
        mp = MediaPlayer.create(this, R.raw.sample);
        logoutText = findViewById(R.id.logout);
        showHostelsView = findViewById(R.id.showHostels);
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                mAuth.signOut();
                startActivity(new Intent(User_dashboard.this,MainActivity.class));
            }
        });
        showHostelsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                startActivity(new Intent(User_dashboard.this,ShowHostel.class));
            }
        });
    }
}