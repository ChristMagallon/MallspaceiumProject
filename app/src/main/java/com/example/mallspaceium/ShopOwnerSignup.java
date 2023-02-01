package com.example.mallspaceium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShopOwnerSignup extends AppCompatActivity {

    private Button ShopOwnerSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_signup);

        ShopOwnerSignupButton = findViewById(R.id.ShopOwnerSignupButton);
        ShopOwnerSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopOwnerSignup.this, ShopOwnerSignin.class));
            }
        });
    }
}