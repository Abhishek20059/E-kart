package com.example.e_kart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.e_kart.ui.Kart.KartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import Prevalent.Prevalent;
import model.Products;

public class ProductDetalisActivity extends AppCompatActivity {

    //private FloatingActionButton addtocart ;
    private Button addtocart;
    private ImageView productImage1,productImage2,productImage3,productImage4;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private String productID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detalis);

        productID = getIntent().getStringExtra("pid");

       addtocart=findViewById(R.id.product_add_to_cart_button);
        productImage1=findViewById(R.id.Product_image_details1);
        productImage2=findViewById(R.id.Product_image_details2);
        productImage3=findViewById(R.id.Product_image_details3);
        productImage4=findViewById(R.id.Product_image_details4);
        numberButton=findViewById(R.id.count_number_btn);
        productPrice= findViewById(R.id.product_price_details);
        productDescription=findViewById(R.id.product_desription_details);
        productName=findViewById(R.id.product_name_details);


            getProductDetails(productID);

            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addingtocartlist();
                }
            });
    }

    private void addingtocartlist()
    {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MMM ,dd,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentDate.format(calForDate.getTime());

       final DatabaseReference cartListref=FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String ,Object> cartMap = new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");

        cartListref.child("User View").child(Prevalent.currentonlineUser.getNumber())
                .child("Cart List").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            cartListref.child("Admin View").child(Prevalent.currentonlineUser.getNumber())
                                    .child("Cart List").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
    if(task.isSuccessful())
    {
        Toast.makeText(ProductDetalisActivity.this, "Added to cart..", Toast.LENGTH_SHORT).show();
        Intent inten = new Intent(ProductDetalisActivity.this,homeActivity.class);
        startActivity(inten);
    }
                                        }
                                    });
                        }
                    }
                });





    }

    private void getProductDetails(String productID)
    {
        DatabaseReference productref = FirebaseDatabase.getInstance().getReference().child("Products");
        productref.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    Products products= dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage1()).into(productImage1);
                    Picasso.get().load(products.getImage2()).into(productImage2);
                    Picasso.get().load(products.getImage3()).into(productImage3);
                    Picasso.get().load(products.getImage4()).into(productImage4);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}