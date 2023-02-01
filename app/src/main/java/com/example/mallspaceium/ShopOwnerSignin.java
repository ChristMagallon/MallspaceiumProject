package com.example.mallspaceium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShopOwnerSignin extends AppCompatActivity {

    private Button ShopOwnerSigninButton;
    private TextView ShopOwnerForgotPassTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_signin);

        ShopOwnerForgotPassTextView = findViewById(R.id.ShopOwnerForgotPassTextBox);
        ShopOwnerForgotPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopOwnerSignin.this, AccountRecovery.class));
            }
        });

        ShopOwnerSigninButton = findViewById(R.id.ShopOwnerSigninButton);
        ShopOwnerSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_role = "ShopOwner";
                Intent i = new Intent(ShopOwnerSignin.this, HomePage.class);
                i.putExtra("user_role", user_role);
                startActivity(i);
            }
        });
    }
}