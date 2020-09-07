package com.example.findhostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login Activity";
    private EditText emailField;
    private EditText passwordField;
    private Button loginBtn;
    private TextView gotoRegister;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        fb = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Query query = fb.collection("users").whereEqualTo("email",currentUser.getEmail());
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot mydoc :
                            task.getResult().getDocuments()) {
                        if(mydoc.get("userType").equals("User")){
                            startActivity(new Intent(LoginActivity.this,User_dashboard.class));
                        }
                        else{
                            startActivity(new Intent(LoginActivity.this,HomePage.class));
                        }
                    }
                }
            });
        }
        emailField = findViewById(R.id.inputEmail);
        passwordField = findViewById(R.id.inputPassword);
        loginBtn = findViewById(R.id.btnLogin);
        gotoRegister = findViewById(R.id.gotoRegister);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailField.getText().toString().trim().equals("")) {
                    emailField.setError("Field Required");
                }
                if (passwordField.getText().toString().trim().equals("")) {
                    passwordField.setError("Field Required");
                }
                if(!emailField.getText().toString().trim().equals("") && !passwordField.getText().toString().trim().equals("")) {
                    String email = emailField.getText().toString().trim();
                    String password = passwordField.getText().toString().trim();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                final FirebaseUser user = mAuth.getCurrentUser();
                                Query query = fb.collection("users").whereEqualTo("email",user.getEmail());
                                query.get().addOnCompleteListener(LoginActivity.this, new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (DocumentSnapshot mydoc :task.getResult().getDocuments()) {
                                            if(mydoc.get("userType").equals("User")){
                                                Intent userIntent = new Intent(LoginActivity.this,User_dashboard.class);
                                                startActivity(userIntent);
                                            }
                                            else{
                                                Intent hostelerIntent = new Intent(LoginActivity.this,HomePage.class);
                                                startActivity(hostelerIntent);
                                            }
                                        }
                                    }
                                });
                                //Intent homeIntent = new Intent(getApplicationContext(), HomePage.class);
                                //startActivity(homeIntent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(signUpIntent);
            }
        });
    }
}