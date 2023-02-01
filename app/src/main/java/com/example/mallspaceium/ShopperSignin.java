package com.example.mallspaceium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShopperSignin extends AppCompatActivity {

    private Button ShopperSigninButton;
    private TextView ShopperForgotPassTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_signin);

        ShopperForgotPassTextView = findViewById(R.id.ShopperForgotPassTextView);
        ShopperForgotPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopperSignin.this, AccountRecovery.class));
            }
        });

        ShopperSigninButton = findViewById(R.id.ShopperSigninButton);
        ShopperSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_role = "Shopper";
                Intent i = new Intent(ShopperSignin.this, HomePage.class);
                i.putExtra("user_role", user_role);
                startActivity(i);
            }
        });
    }
}