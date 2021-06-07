package com.example.e_kart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AdminaddnewproductActivity extends AppCompatActivity {

    private String categoryName, Description, Price, Pname , saveCurrentDate , saveCurrentTime;
    private ImageView InputProductImage1,InputProductImage2,InputProductImage3,InputProductImage4;
    private EditText InputProductName, InputProductDescription, InputProductPrice;
    private static final int GalleryPick = 1 ;
    public Uri ImageUri1,ImageUri2,ImageUri3,ImageUri4;
    private final Uri[] uri=new Uri[4];
    private final String[] downimg=new String[4];
    private String productRandomkey;
    private StorageReference productImageref;
    private DatabaseReference productsref;
    private ProgressDialog LoadingBar;
    public int j = -1;

    public AdminaddnewproductActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddnewproduct);

        Button addNewProductButton = findViewById(R.id.add_new_product);
        InputProductImage1  = findViewById(R.id.select_product_image);
        InputProductImage2  = findViewById(R.id.select_product_image2);
        InputProductImage3  = findViewById(R.id.select_product_image3);
        InputProductImage4  = findViewById(R.id.select_product_image4);

        ActionBar actionBar = getSupportActionBar();   // or getActionBar()
        assert actionBar != null;
        actionBar.hide();

        InputProductName = findViewById(R.id.product_name);
        InputProductDescription = findViewById(R.id.product_description);
        InputProductPrice = findViewById(R.id.product_price);
        LoadingBar = new ProgressDialog(this);

        categoryName = getIntent().getExtras().get("category").toString();

    productImageref = FirebaseStorage.getInstance().getReference().child("Product Images");
    productsref = FirebaseDatabase.getInstance().getReference().child("Products");

        InputProductImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { ValidateProductData(); }
        });
        InputProductImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        InputProductImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        InputProductImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });


    }


    private void ValidateProductData()
    {
            Description = InputProductDescription.getText().toString();
            Price = InputProductPrice.getText().toString();
            Pname = InputProductName.getText().toString();

            if(ImageUri1 == null)
            {
                Toast.makeText(AdminaddnewproductActivity.this,"Image is required",Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(Description))
            {
                Toast.makeText(AdminaddnewproductActivity.this,"Description  is required",Toast.LENGTH_SHORT).show();

            }
            else if (TextUtils.isEmpty(Price))
            {
                Toast.makeText(AdminaddnewproductActivity.this,"Price  is required",Toast.LENGTH_SHORT).show();

            }
            else if (TextUtils.isEmpty(Pname))
            {
                Toast.makeText(AdminaddnewproductActivity.this,"Product name is required",Toast.LENGTH_SHORT).show();

            }
            else
            {
                StoreProductInformation();
            }

    }

    private void StoreProductInformation() {
        LoadingBar.setTitle("adding new product ");
        LoadingBar.setTitle("Please wait ,while we are  adding new product");
        LoadingBar.setCanceledOnTouchOutside(false);
        LoadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:MM:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomkey = saveCurrentDate + saveCurrentTime;


        for (  int i = 0; i <= 3; i++) {

            StorageReference filepath = productImageref.child(uri[i].getLastPathSegment() + productRandomkey + ".jpg");
            final UploadTask upolodtask = filepath.putFile(uri[i]);


            upolodtask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String massege = e.toString();
                    Toast.makeText(AdminaddnewproductActivity.this, "ERROR : " + massege, Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override


                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AdminaddnewproductActivity.this, "Product Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    Task<Uri> urltask = upolodtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override

                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful())
                            {
                                throw Objects.requireNonNull(task.getException());
                            }

                            j++;
                            downimg[j] = String.valueOf(Uri.parse(filepath.getDownloadUrl().toString()));//change this line
                            return filepath.getDownloadUrl();

                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {



                                downimg[j] = String.valueOf(Uri.parse(task.getResult().toString()));//and this also
                                Toast.makeText(AdminaddnewproductActivity.this, "got the Product Image URL Successfully", Toast.LENGTH_SHORT).show();
                                saveProductinfoToDatabase();

                            }


                        }
                    });
                }
            });
        }//for loop end


    }
    private void saveProductinfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomkey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("Description", Description);
        productMap.put("Image1", downimg[0]);
        productMap.put("Image2", downimg[1]);
        productMap.put("Image3", downimg[2]);
        productMap.put("Image4", downimg[3]);
        productMap.put("category", categoryName);
        productMap.put("price", Price);
        productMap.put("pname", Pname);

    productsref.child(productRandomkey).updateChildren(productMap)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                            if(j==3) {                                                                                                                    //ERROR fix

                                Toast.makeText(AdminaddnewproductActivity.this, " Product is Added Successfully", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                                Intent intent = new Intent(AdminaddnewproductActivity.this, AdminCategoryActivity.class);
                                    startActivity(intent);

                                   }
                    }else
                    {
                        LoadingBar.dismiss();
                        String massege = task.getException().toString();
                        Toast.makeText(AdminaddnewproductActivity.this,"ERROR : "+ massege,Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void opengallery()
    {
   Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {

            ImageUri1 = data.getData();
            InputProductImage1.setImageURI(ImageUri1);
            uri[0]= ImageUri1;

            ImageUri2 = data.getData();
            InputProductImage2.setImageURI(ImageUri2);
            uri[1]= ImageUri2;

            ImageUri3 = data.getData();
            InputProductImage3.setImageURI(ImageUri3);
            uri[2]= ImageUri3;

            ImageUri4 = data.getData();
            InputProductImage4.setImageURI(ImageUri4);
            uri[3]= ImageUri4;

        }
    }
}