package com.example.mallspaceium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShopperSigninSignup extends AppCompatActivity {

    private Button ShopperSigninButton, ShopperSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_signin_signup);

        ShopperSigninButton = findViewById(R.id.ShopperSigninButton);
        ShopperSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopperSigninSignup.this, ShopperSignin.class));
            }
        });

        ShopperSignupButton = findViewById(R.id.ShopperSignupButton);
        ShopperSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopperSigninSignup.this, ShopperSignup.class));
            }
        });
    }
}