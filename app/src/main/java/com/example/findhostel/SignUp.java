package com.example.findhostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static String TAG = "SignUp";
    private FirebaseFirestore db ;
    private FirebaseAuth fb;
    private TextView fullNameField;
    private TextView emailField;
    private TextView passwordField;
    private TextView cpasswordField;
    private Button signupBtn;
    private Spinner userType;
    private String selectedUserType;
    private boolean allOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fullNameField = findViewById(R.id.inputFullName);
        emailField = findViewById(R.id.inputEmail);
        passwordField = findViewById(R.id.inputPassword);
        cpasswordField = findViewById(R.id.inputConfirmPassword);
        signupBtn = findViewById(R.id.btnRegister);
        userType = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"User","Hosteler"});
        userType.setAdapter(adapter);
        userType.setOnItemSelectedListener(this);
        db = FirebaseFirestore.getInstance();
        fb = FirebaseAuth.getInstance();
        signupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    allOK = true;
                    final String email = emailField.getText().toString().trim();
                    final String password = passwordField.getText().toString().trim();
                    String fullName = fullNameField.getText().toString().trim();
                    String cpassword = cpasswordField.getText().toString().trim();
                    if (fullName.equals("")) {
                        allOK = false;
                        fullNameField.setError("Field Required");
                    }
                    else if (email.equals("")) {
                        allOK = false;
                        emailField.setError("Field Required");
                    }
                    else if (password.equals("")) {
                        allOK = false;
                        passwordField.setError("Field Required");
                    }
                    else if (cpassword.equals("")) {
                        allOK = false;
                        cpasswordField.setError("Field Required");
                    }
                    else if(!password.equals(cpassword)){
                        allOK = false;
                        Toast.makeText(getApplicationContext(),"Password and Confirm Password doesn't match",Toast.LENGTH_LONG).show();
                    }
                    else if(password.length() < 6){
                        allOK = false;
                        passwordField.setError("Password Length must be greater than 6");
                    }
                    if (allOK) {

                        final User myUser = new User();
                        myUser.setEmail(email);
                        myUser.setPassword(password);
                        myUser.setFullName(fullName);
                        myUser.setUserType(selectedUserType);

                        // Add a new document with a generated ID


                                fb.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Log.d(TAG, "createUserWithEmail:success");
                                                    FirebaseUser user = fb.getCurrentUser();
                                                    db.collection("users").add(myUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                                            Log.w(TAG, "Error adding document", e);
                                                        }
                                                    });
                                                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_LONG).show();
                                                    if (selectedUserType.equals("User")) {
                                                        Intent userIntent = new Intent(SignUp.this, User_dashboard.class);
                                                        startActivity(userIntent);
                                                    } else {
                                                        Intent hostelIntent = new Intent(SignUp.this, HomePage.class);
                                                        startActivity(hostelIntent);
                                                    }
                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                                    Toast.makeText(getApplicationContext(),"User Already Exists",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                //Intent homeIntent = new Intent(SignUp.this,HomePage.class);
                                //startActivity(homeIntent);


                }
                }
            });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedUserType = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedUserType = "User";
    }
}