package com.example.e_kart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_kart.ui.login.LoginActivity;
import com.example.e_kart.ui.login.SingupActivity;

public class MainActivity extends AppCompatActivity {

    Button button1,button2;
    ImageView imageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 =findViewById(R.id.button1);
        button2 =findViewById(R.id.button2);
        imageView1 = findViewById(R.id.imageView1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent login=new Intent(MainActivity.this, LoginActivity.class);
               startActivity(login);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singup = new Intent(MainActivity.this, SingupActivity.class);
                startActivity(singup);
            }
        });





    }
}
