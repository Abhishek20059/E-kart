package com.example.e_kart.ui.settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_kart.MainActivity;
import com.example.e_kart.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import Prevalent.Prevalent;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private EditText fullNameEditText,userPhoneEditText,addressEditText;
    private TextView profilechangeTextbtn,closeTextbtn,saveTextbtn;

    private Uri  imageUri;
    private StorageTask uplodtask;
    private String myUri ="";
    private StorageReference storageprofilepictureref;
    private String cheker="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        storageprofilepictureref = FirebaseStorage.getInstance().getReference().child("Profile Pictures");
        profileImageView=findViewById(R.id.setting_profile_image);
        fullNameEditText=findViewById(R.id.setting_full_name);
        userPhoneEditText=findViewById(R.id.setting_phone_number);
        addressEditText=findViewById(R.id.setting_adress);
        profilechangeTextbtn=findViewById(R.id.profile_image_change);
        closeTextbtn=findViewById(R.id.close_account_setting_btn);
        saveTextbtn=findViewById(R.id.update_account_setting_btn);


        userInfoDispaly(profileImageView,fullNameEditText,userPhoneEditText,addressEditText);

            closeTextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            saveTextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(cheker.equals("clicked"))
                    {
                        userInfoSaved();

                    }
                    else
                        {
                            UpdateOnlyUserInfo();
                        }
                }
            });
                profilechangeTextbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cheker="clicked";

                        // start cropping activity for pre-acquired image saved on the device
                        CropImage.activity(imageUri)
                                .setAspectRatio(1,1)
                                .start(SettingsActivity.this);

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!= null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri= result.getUri();
            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this,"ERROR TRY AGAIN..",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }

    private void UpdateOnlyUserInfo()
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String, Object> UserMap = new HashMap<>();
        UserMap.put("name",fullNameEditText.getText().toString());
        UserMap.put("address",addressEditText.getText().toString());
        UserMap.put("phoneOrder",userPhoneEditText.getText().toString());
        ref.child(Prevalent.currentonlineUser.getNumber()).updateChildren(UserMap);


        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        Toast.makeText(SettingsActivity.this, "Profile InFo updated sucesfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void userInfoSaved()
    {
    if(TextUtils.isEmpty(fullNameEditText.getText().toString()))
        {
            Toast.makeText(SettingsActivity.this,"Name Is Mandetory",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(SettingsActivity.this,"Name Is Mandetory",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(userPhoneEditText.getText().toString()))
        {
            Toast.makeText(SettingsActivity.this,"Name Is Mandetory",Toast.LENGTH_SHORT).show();
        }
        else if (cheker.equals("clicked"))
        {
            uploadImage();
        }

    }

    private void uploadImage()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait , while we are updating your Information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri!=null)
        {
            final StorageReference fileRef = storageprofilepictureref
                    .child(Prevalent.currentonlineUser.getNumber() + ".jpg");
            uplodtask = fileRef.putFile(imageUri);

            uplodtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull @NotNull Task task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            })
            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task) {

                    if(task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        myUri= downloadUri.toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                        HashMap<String, Object> UserMap = new HashMap<>();
                        UserMap.put("name",fullNameEditText.getText().toString());
                        UserMap.put("address",addressEditText.getText().toString());
                        UserMap.put("phoneOrder",userPhoneEditText.getText().toString());
                        UserMap.put("image",myUri);

                        ref.child(Prevalent.currentonlineUser.getNumber()).updateChildren(UserMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                        Toast.makeText(SettingsActivity.this, "Profile InFo updated sucesfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(SettingsActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
            }) ;
        }
        else
        {
            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDispaly(CircleImageView profileImageView, EditText fullNameEditText, EditText userPhoneEditText, EditText addressEditText) {

        DatabaseReference UserRef= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentonlineUser.getNumber());

                UserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot)
                    {
                            if(dataSnapshot.exists())
                            {
                                if(dataSnapshot.child("image").exists())
                                {
                                    String image=dataSnapshot.child("image").getValue().toString()  ;
                                    String name=dataSnapshot.child("name").getValue().toString()  ;
                                    String phone=dataSnapshot.child("Number").getValue().toString()  ;
                                    String address=dataSnapshot.child("address").getValue().toString()  ;

                                    Picasso.get().load(image).into(profileImageView);
                                    fullNameEditText.setText(name);
                                    userPhoneEditText.setText(phone);
                                    addressEditText.setText(address);

                                }
                            }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        }
}