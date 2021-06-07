package com.example.e_kart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_kart.ui.login.LoginActivity;
import com.example.e_kart.ui.login.SingupActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Prevalent.Prevalent;
import io.paperdb.Paper;
import model.User;

public class MainActivity extends AppCompatActivity {

    Button button1,button2;
    ImageView imageView1;
    private ProgressDialog LoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 =findViewById(R.id.button1);
        button2 =findViewById(R.id.button2);
        Paper.init(this);
        LoadingBar = new ProgressDialog(this);

        ActionBar actionBar = getSupportActionBar();   // or getActionBar()
        actionBar.hide();

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


        String UserPhoneKey = Paper.book().read(Prevalent.UserPhonekey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordkey);

        if(UserPhoneKey != "" && UserPasswordKey != "")
        {
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey,UserPasswordKey);

                LoadingBar.setTitle("Already Logged in");
                LoadingBar.setTitle("Please wait...");
                LoadingBar.setCanceledOnTouchOutside(false);
                LoadingBar.show();

            }
        }

    }

    private void AllowAccess(String userPhoneKey, String userPasswordKey)
    {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("Users").child(userPhoneKey).exists())
                {
                    User Userdata = dataSnapshot.child("Users").child(userPhoneKey).getValue(User.class);
                    if(Userdata.getNumber().equals(userPhoneKey))
                    {
                        if(Userdata.getPassword().equals(userPasswordKey))
                        {
                            Toast.makeText(MainActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                            Intent home = new Intent(MainActivity.this,homeActivity.class);
                            Prevalent.currentonlineUser=Userdata;
                            startActivity(home);
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Account with this "+ userPhoneKey+"number do not exists",Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
