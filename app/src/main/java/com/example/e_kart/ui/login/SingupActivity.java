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

    private EditText InputName,InputPassword,Password,userAddress,InputEmailAddress;
    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

         EditText InputName = findViewById(R.id.registername);
         EditText InputPassword = findViewById(R.id.registerpassword);
         EditText Password = findViewById(R.id.secondpassword);
         EditText InputEmailAddress= findViewById(R.id.registeremail);
         EditText Address = findViewById(R.id.address);
        Button signup = findViewById(R.id.signup1);
        LoadingBar = new ProgressDialog(this);

        signup.setOnClickListener(v -> CreateAccount());

    }

    private void CreateAccount() {

        String UserName =  InputName.getText().toString();
        String FirstPassword =  InputPassword.getText().toString();
        String Password2 = Password.getText().toString();
        String Address = userAddress.getText().toString();
        String EmailAddress = InputEmailAddress.getText().toString();

        if (TextUtils.isEmpty(UserName))
        {
            Toast.makeText(this,"Please enter username",Toast.LENGTH_SHORT).show();
        }

       else if (TextUtils.isEmpty(FirstPassword))
        {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
        }


       else if (TextUtils.isEmpty((CharSequence) Password2))
        {
            Toast.makeText(this,"Please  confirmPassword",Toast.LENGTH_SHORT).show();
        }

       else if (TextUtils.isEmpty(Address))
        {
            Toast.makeText(this,"Please enter address",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(EmailAddress))
        {
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
        }

        else {
            LoadingBar.setTitle("Create account");
            LoadingBar.setTitle("Please wait ,while we are checking the credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            ValidateEmail(UserName,EmailAddress,Password2);

        }
    }

    private void ValidateEmail(String InputName, String InputEmailAddress, String InputPassword) {

        final DatabaseReference Rootref;
         Rootref = FirebaseDatabase.getInstance().getReference();
         Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                 if(!(dataSnapshot.child("users").child(InputEmailAddress).exists()))
                 {
                     HashMap<String, Object> UserDataMap = new HashMap<>();
                     UserDataMap.put("UserName",InputName);
                     UserDataMap.put("Password",InputPassword);
                     UserDataMap.put("Email",InputEmailAddress);

                     Rootref.child("Users").child(InputEmailAddress).updateChildren(UserDataMap)
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
                                     Toast.makeText(SingupActivity.this,"Network Error: Plzz try again",Toast.LENGTH_SHORT).show();
                                 }
                             });

                 }
                 else {
                     Toast.makeText(SingupActivity.this, "this " + InputEmailAddress + "  alredy exits", Toast.LENGTH_SHORT).show();
                     LoadingBar.dismiss();
                     Toast.makeText(SingupActivity.this, "pleas try again using another email address", Toast.LENGTH_SHORT).show();

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