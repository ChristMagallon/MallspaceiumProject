package com.example.mallspaceium.shopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mallspaceium.R;

public class ShopperSignup extends AppCompatActivity {

    private Button ShopperSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_signup);

        ShopperSignupButton = findViewById(R.id.ShopperSignupButton);
        ShopperSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopperSignup.this, ShopperSignin.class));
            }
        });
    }
}