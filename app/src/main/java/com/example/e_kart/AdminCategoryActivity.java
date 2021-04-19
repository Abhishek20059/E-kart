package com.example.e_kart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tshirts,SportsTshirts,FemaleDresses,sweathers;
    private ImageView glasses,walletbagspurses,HatsCaps,shoes;
    private ImageView headphoneshandfree,Laptops,Watches,mobilesPhones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tshirts = findViewById(R.id.t_shirts);
        SportsTshirts = findViewById(R.id.sports_t_shirt);
        FemaleDresses = findViewById(R.id.female_dresses);
        sweathers = findViewById(R.id.sweathers);
        glasses = findViewById(R.id.glasses);
        walletbagspurses = findViewById(R.id.purses_bags_wallets);
        HatsCaps = findViewById(R.id.hats_caps);
        shoes = findViewById(R.id.shoes);
        headphoneshandfree = findViewById(R.id.headphones);
        Laptops = findViewById(R.id.laptops);
        Watches = findViewById(R.id.watches);
        mobilesPhones = findViewById(R.id.mobiles);



        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","tshirts");
                startActivity(intent);

            }
        });

        SportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","SportsTshirts");
                startActivity(intent);

            }
        });

        FemaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","FemaleDresses");
                startActivity(intent);
            }
        });

        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","sweathers");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","glasses");
                startActivity(intent);
            }
        });
        walletbagspurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","walletbagspurses");
                startActivity(intent);
            }
        });

        HatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","HatsCaps");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });
        headphoneshandfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","headphoneshandfree");
                startActivity(intent);
            }
        });
        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });
        Watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","watches");
                startActivity(intent);
            }
        });

        mobilesPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminCategoryActivity.this, AdminaddnewproductActivity.class);
                intent.putExtra("category","mobilesPhones");
                startActivity(intent);
            }
        });

    }
}