package com.example.findhostel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.TargetOrBuilder;

import java.util.UUID;

public class addHostel extends AppCompatActivity  {
    private EditText hostelNameField;
    private EditText hostelAddressField;
    private EditText numberOfRoomsField;
    private  EditText rentPerPersonField;
    private EditText peopleCapacity;
    private Spinner hostelTypeDropdown;
    private Spinner hostelBuildingTypeDropdown;
    private  String hostelType;
    private String hostelBuildingType;
    private EditText phoneNumberField;
    private boolean allOK;
    private Button addHostelBtn;
    private ImageView hostelPicture;
    private Uri imageURI;
    private FirebaseFirestore fb;
    private StorageReference sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hostel);
        hostelNameField = findViewById(R.id.inputHostelName);
        hostelAddressField = findViewById(R.id.inputAddress);
        numberOfRoomsField = findViewById(R.id.inputNumberOfRooms);
        rentPerPersonField = findViewById(R.id.rentPerPerson);
        hostelTypeDropdown = findViewById(R.id.hostelType);
        hostelBuildingTypeDropdown = findViewById(R.id.hostelBuildingType);
        hostelPicture = findViewById(R.id.hostelPictureView);
        addHostelBtn = findViewById(R.id.btnaddHostel);
        phoneNumberField = findViewById(R.id.inputPhoneNumber);
        peopleCapacity = findViewById(R.id.inputPeopleCapacity);
        fb = FirebaseFirestore.getInstance();
        sr = FirebaseStorage.getInstance().getReference();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Boys","Girls"});
        hostelTypeDropdown.setAdapter(adapter);
        hostelTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hostelType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                allOK = false;
            }
        });
        final ArrayAdapter<String> secondAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, new String[]{"Flat","Hostel"});
        hostelBuildingTypeDropdown.setAdapter(secondAdapter);
        hostelBuildingTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hostelBuildingType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        hostelPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
        addHostelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allOK = true;
                final String hostelName = hostelNameField.getText().toString();
                final String hostelAddresss = hostelAddressField.getText().toString();
                final String rentPerPerson = rentPerPersonField.getText().toString();
                final String numberOfRomms = numberOfRoomsField.getText().toString();
                final String phoneNumber = phoneNumberField.getText().toString();
                final String numberOfPeople = peopleCapacity.getText().toString();
                if(allOK){
                    String randomKey = UUID.randomUUID().toString();
                    final StorageReference riversRef = sr.child("images/"+randomKey);
                    riversRef.putFile(imageURI)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();
                                    Hostel hostel = new Hostel();
                                    hostel.setHostelName(hostelName);
                                    hostel.setHostelAddress(hostelAddresss);
                                    hostel.setRentPerPerson(rentPerPerson);
                                    hostel.setNumberOfRooms(numberOfRomms);
                                    hostel.setPhoneNumber(phoneNumber);
                                    hostel.setHostelType(hostelType);
                                    hostel.setHostelBuildingType(hostelBuildingType);
                                    hostel.setPeopleCapacity(numberOfPeople);
                                    hostel.setHostelImage(riversRef.getPath());
                                    try {


                                    fb.collection("hostels").add(hostel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Toast.makeText(getApplicationContext(),"Hostel Added Successfully",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(addHostel.this,HomePage.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Hostel Not Added Successfully",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    }catch (Exception e){
                                        Log.w("Firebase Insert",e.getMessage());
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(getApplicationContext(),"Failed to Upload Image",Toast.LENGTH_LONG).show();
                                }
                            });

                }
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data !=null && data.getData() !=null){
            imageURI = data.getData();
            hostelPicture.setImageURI(imageURI);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

}