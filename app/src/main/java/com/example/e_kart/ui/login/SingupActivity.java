package com.example.e_kart.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_kart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SingupActivity extends AppCompatActivity {

    private EditText InputName,InputPassword,Password,userAddress, InputNumber;
    private ProgressDialog LoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        InputName = findViewById(R.id.registername);
        InputPassword = findViewById(R.id.registerpassword);
        Password = findViewById(R.id.secondpassword);
        InputNumber = findViewById(R.id.registerNumber);
        userAddress = findViewById(R.id.address);
        Button signup = findViewById(R.id.signup1);
        LoadingBar = new ProgressDialog(this);


       signup.setOnClickListener(v -> CreateAccount());

    }

    private void CreateAccount() {

        String UserName = InputName.getText().toString();
        String FirstPassword = InputPassword.getText().toString();
        String Password2 = Password.getText().toString();
        String Address = userAddress.getText().toString();
        String Number = InputNumber.getText().toString();

        if (TextUtils.isEmpty(UserName)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(FirstPassword)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Password2)) {
            Toast.makeText(this, "Please  confirmPassword", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Address)) {
            Toast.makeText(this, "Please enter number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Number)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        } else if (!FirstPassword.equals(Password2)) {
            LoadingBar.dismiss();
            Toast.makeText(SingupActivity.this, "Please confirm password", Toast.LENGTH_SHORT).show();

        } else {
            LoadingBar.setTitle("Create account");
            LoadingBar.setTitle("Please wait ,while we are checking the credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();


              ValidateEmail(UserName, Number,Password2);


        }
    }

    private void ValidateEmail(String UserName, String Number, String Password2)
    {

        final DatabaseReference Rootref;

        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                 if(!(dataSnapshot.child("users").child(Number).exists()))
                 {
                     HashMap<String, Object> UserDataMap = new HashMap<>();

                     UserDataMap.put("UserName",UserName);
                     UserDataMap.put("Password",Password2);
                     UserDataMap.put("Number",Number);

                     Rootref.child("Users").child(Number).updateChildren(UserDataMap)
                             .addOnCompleteListener(task -> {

                                 if (task.isSuccessful())
                                 {
                                     Toast.makeText(SingupActivity.this,"Congratulations your account is created",Toast.LENGTH_SHORT).show();
                                     LoadingBar.dismiss();

                                     Intent login=new Intent(SingupActivity.this, LoginActivity.class);
                                     startActivity(login);
                                 }
                                 else {
                                     LoadingBar.dismiss();
                                     Toast.makeText(SingupActivity.this,"Network Error: Please try again",Toast.LENGTH_SHORT).show();
                                 }
                             });

                 }
                 else {
                     Toast.makeText(SingupActivity.this, "this " + Number + "  already exits", Toast.LENGTH_SHORT).show();
                     LoadingBar.dismiss();
                     Toast.makeText(SingupActivity.this, "pleas try again using another Number address", Toast.LENGTH_SHORT).show();

                     Intent main = new Intent(SingupActivity.this, LoginActivity.class);
                     startActivity(main);
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

    }
}