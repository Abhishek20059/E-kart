package com.example.e_kart.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.e_kart.AdminCategoryActivity;
import com.example.e_kart.R;
import com.example.e_kart.homeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Prevalent.Prevalent;
import io.paperdb.Paper;
import model.User;

public class LoginActivity extends AppCompatActivity {



    private EditText number, password;
    private ProgressDialog LoadingBar;
    private  String parentDbName = "Users";
    private CheckBox chkboxRememberMe;
    private TextView adminlink, notadminlink;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();   // or getActionBar()

        actionBar.hide();

        number = findViewById(R.id.registerNumber);
        password = findViewById(R.id.registerpassword);
        Button loginButton = findViewById(R.id.login_btn);
        LoadingBar = new ProgressDialog(this);
        chkboxRememberMe = findViewById(R.id.checkBox);
        adminlink = findViewById(R.id.iamadmin);
        notadminlink = findViewById(R.id.iamnotadmin);


        loginButton.setOnClickListener(v -> loginuser());

        adminlink.setOnClickListener(v -> {
            loginButton.setText(R.string.LoginAdmin);
            adminlink.setVisibility(View.INVISIBLE);
            notadminlink.setVisibility(View.VISIBLE);
            parentDbName = "admins";


        });
            notadminlink.setOnClickListener(v -> {
                loginButton.setText(R.string.login);
                adminlink.setVisibility(View.VISIBLE);
                notadminlink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";

            });
    }



    private void loginuser() {


        String Password = password.getText().toString();
        String Phonenumber = number.getText().toString();

        if (TextUtils.isEmpty(Password))
        {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Phonenumber))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }

        else
        {
            LoadingBar.setTitle("Login account");
            LoadingBar.setTitle("Please wait ,while we are checking the credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            AllowAccessToAccount(Phonenumber,Password);
        }

    }

    private void AllowAccessToAccount(String Phonenumber, String Password) {

        if(chkboxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhonekey,Phonenumber);
            Paper.book().write(Prevalent.UserPasswordkey, Password);
        }


        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDbName).child(Phonenumber).exists())
                {

                    User Userdata = dataSnapshot.child(parentDbName).child(Phonenumber).getValue(User.class);

                    assert Userdata != null;
                    if(Userdata.getNumber().equals(Phonenumber))
                    {
                        if(Userdata.getPassword().equals(Password))
                        {
                           if(parentDbName.equals("admins"))
                           {
                               Toast.makeText(LoginActivity.this,"welcome admin, your Logged in successfully",Toast.LENGTH_SHORT).show();
                               LoadingBar.dismiss();
                               Intent home = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                               startActivity(home);
                           }
                           else if(parentDbName.equals("Users"))
                           {
                               Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                               LoadingBar.dismiss();
                               Intent home = new Intent(LoginActivity.this, homeActivity.class);
                               Prevalent.currentonlineUser = Userdata;
                               startActivity(home);
                           }
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Account with this "+ Phonenumber+"number do not exists",Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
