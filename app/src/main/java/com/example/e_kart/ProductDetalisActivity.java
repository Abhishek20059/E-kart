package com.example.e_kart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import model.Products;

public class ProductDetalisActivity extends AppCompatActivity {

    //private FloatingActionButton addtocart ;
    private ImageView productImage1,productImage2,productImage3,productImage4;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private String productID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detalis);

        productID = getIntent().getStringExtra("pid");

       // addtocart=findViewById(R.id.add_product_to_cart_btn);
        productImage1=findViewById(R.id.Product_image_details1);
        productImage2=findViewById(R.id.Product_image_details2);
        productImage3=findViewById(R.id.Product_image_details3);
        productImage4=findViewById(R.id.Product_image_details4);
        numberButton=findViewById(R.id.count_number_btn);
        productPrice= findViewById(R.id.product_price_details);
        productDescription=findViewById(R.id.product_desription_details);
        productName=findViewById(R.id.product_name_details);


            getProductDetails(productID);
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