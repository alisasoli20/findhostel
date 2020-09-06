package com.example.findhostel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ListItemView extends AppCompatActivity {
    private ImageView hostelImage;
    private TextView hostelNameField;
    private TextView hostelTypeField;
    private TextView hostelAddressField;
    private TextView rentPerPersonField;
    private TextView numberOfRoomsField;
    private TextView hostelBuildingTypeField;
    private Button hostelPhoneNumberBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_view);
        hostelImage = findViewById(R.id.showHostelImage);
        hostelNameField = findViewById(R.id.hostelName);
        hostelTypeField = findViewById(R.id.hostelTypeField);
        hostelAddressField = findViewById(R.id.hostelAddress);
        rentPerPersonField = findViewById(R.id.hostelRentPerPerson);
        numberOfRoomsField = findViewById(R.id.hostelNumberOfRooms);
        hostelBuildingTypeField = findViewById(R.id.buildingType);
        hostelPhoneNumberBtn = findViewById(R.id.hostelPhoneNumber);
        final Intent intent = getIntent();
        hostelNameField.setText(intent.getStringExtra("hostelName"));
        hostelTypeField.setText(intent.getStringExtra("hostelType"));
        hostelAddressField.setText(intent.getStringExtra("hostelAddress"));
        rentPerPersonField.setText(intent.getStringExtra("rentPerPerson"));
        numberOfRoomsField.setText(intent.getStringExtra("numberOfRooms"));
        hostelBuildingTypeField.setText(intent.getStringExtra("hostelBuildingType"));
        hostelPhoneNumberBtn.setText(intent.getStringExtra("phoneNumber"));
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(intent.getStringExtra("hostelImage"));
        Glide.with(this).load(storageReference).into(hostelImage);
        hostelPhoneNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkPermission = ContextCompat.checkSelfPermission(ListItemView.this, Manifest.permission.CALL_PHONE);
                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            ListItemView.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                }
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+intent.getStringExtra("phoneNumber")));
                startActivity(intent1);
            }
        });
    }
}