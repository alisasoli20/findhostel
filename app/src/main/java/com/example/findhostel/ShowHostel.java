package com.example.findhostel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowHostel extends AppCompatActivity {
    private ListView showHostels;
    private FirebaseFirestore fb;
    private StorageReference sr;
    private List<Hostel> dataList = new ArrayList<Hostel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hostel);
        showHostels = findViewById(R.id.hostelsList);
        fb = FirebaseFirestore.getInstance();
        sr = FirebaseStorage.getInstance().getReference();
        fb.collection("hostels").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    dataList.clear();
                    for (DocumentSnapshot mydoc:
                            task.getResult().getDocuments()) {
                        dataList.add(mydoc.toObject(Hostel.class));
                    }
                    MyAdapter myAdapter = new MyAdapter(getApplicationContext(),dataList);
                    myAdapter.notifyDataSetChanged();
                    showHostels.setAdapter(myAdapter);
                }
            }
        });
        showHostels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowHostel.this,ListItemView.class);
                intent.putExtra("hostelImage",dataList.get(i).getHostelImage());
                intent.putExtra("hostelName",dataList.get(i).getHostelName());
                intent.putExtra("hostelAddress",dataList.get(i).getHostelAddress());
                intent.putExtra("rentPerPerson",dataList.get(i).getRentPerPerson());
                intent.putExtra("numberOfRooms",dataList.get(i).getNumberOfRooms());
                intent.putExtra("hostelType",dataList.get(i).getHostelType());
                intent.putExtra("phoneNumber",dataList.get(i).getPhoneNumber());
                intent.putExtra("hostelBuildingType",dataList.get(i).getHostelBuildingType());
                startActivity(intent);
            }
        });
    }
    class MyAdapter extends ArrayAdapter<Hostel>{
        Context context;
        List<Hostel> myList;
        MyAdapter(Context c, List<Hostel> myList){
            super(c,R.layout.row,myList);
            this.context = c;
            this.myList = myList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images = row.findViewById(R.id.hostelImage);
            TextView myTitle = row.findViewById(R.id.myTitle);
            TextView phoneNumber = row.findViewById(R.id.phoneNumber);
            TextView type = row.findViewById(R.id.typeTitleField);
            TextView buildingType = row.findViewById(R.id.buildingTypeTitleField);
            Hostel myHostel = getItem(position);
            myTitle.setText(myHostel.getHostelName());
            phoneNumber.setText(myHostel.getPhoneNumber());
            type.setText(myHostel.getHostelType());
            buildingType.setText(myHostel.getHostelBuildingType());
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(myHostel.getHostelImage());
            Glide.with(context).load(storageReference).into(images );
            return row;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
