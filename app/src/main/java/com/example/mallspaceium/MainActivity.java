package com.example.mallspaceium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mallspaceium.shopowner.ShopOwnerSigninSignup;
import com.example.mallspaceium.shopper.ShopperSigninSignup;

public class MainActivity extends AppCompatActivity {

    private Button ShopperButton, ShopOwnerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShopperButton = findViewById(R.id.ShopperButton);
        ShopperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShopperSigninSignup.class));
            }
        });

        ShopOwnerButton = findViewById(R.id.ShopOwnerButton);
        ShopOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShopOwnerSigninSignup.class));
            }
        });
    }
}