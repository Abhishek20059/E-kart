package com.example.e_kart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Adminpanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        Toast.makeText(this, "welcome admin", Toast.LENGTH_SHORT).show();
    }
}