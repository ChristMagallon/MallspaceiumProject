package com.example.mallspaceium.shopowner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallspaceium.AccountRecovery;
import com.example.mallspaceium.HomePage;
import com.example.mallspaceium.R;
import com.example.mallspaceium.shopper.ShopperSignin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Pattern;

public class ShopOwnerSignin extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextInputLayout shopOwnerEmailBox, shopOwnerPassBox;
    private TextInputEditText shopOwnerEmail, shopOwnerPass;

    private Button ShopOwnerSigninButton;
    private TextView ShopOwnerForgotPassTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_signin);

        // set helper text error for layout style outline box
        shopOwnerEmailBox = (TextInputLayout) findViewById(R.id.ShopOwnerEmailTextBox);
        shopOwnerPassBox = (TextInputLayout) findViewById(R.id.ShopOwnerPasswordTextBox);

        // get the input data from the views
        shopOwnerEmail = (TextInputEditText) findViewById(R.id.ShopOwnerEmailInput);
        shopOwnerPass = (TextInputEditText) findViewById(R.id.ShopOwnerPasswordInput);

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
                if(validateLoginShopOwner()){
                    loginShopOwner();
                }
            }
        });
    }

    // login a user with the following credentials such as email and password
    public void loginShopOwner() {
        String shopOwnerEmailInput = shopOwnerEmail.getText().toString();
        String shopOwnerPasswordInput = shopOwnerPass.getText().toString();
        String user_email = shopOwnerEmailInput;
        db.collection("SHOPOWNERREGISTER")
                .whereEqualTo("shop_email", shopOwnerEmailInput)
                .whereEqualTo("shopOwner_password", shopOwnerPasswordInput)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {
                                Intent i = new Intent(ShopOwnerSignin.this, HomePage.class);
                                i.putExtra("user_email", user_email);
                                startActivity(i);
                            } else {
                                clearTextBox();
                            }
                        } else {
                            Toast.makeText(ShopOwnerSignin.this, "The email you entered isn’t connected to an account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean validateLoginShopOwner(){
        // validation patterns
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        // check if all required fields are filled
        if (shopOwnerEmail.getText().toString().trim().isEmpty()) {
            shopOwnerEmailBox.setHelperText("Field can't be empty");
            shopOwnerEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopOwnerEmailBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (!emailPattern.matcher(shopOwnerEmail.getText().toString().trim()).matches()) {
            shopOwnerEmailBox.setHelperText("Invalid email");
            shopOwnerEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopOwnerEmailBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopOwnerEmailBox.setHelperText(null);
            shopOwnerEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopOwnerEmailBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopOwnerPass.getText().toString().trim().isEmpty()) {
            shopOwnerPassBox.setHelperText("Field can't be empty");
            shopOwnerPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopOwnerPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (shopOwnerPass.getText().toString().contains(" ")) {
            shopOwnerPassBox.setHelperText("Spaces are not allowed");
            shopOwnerPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopOwnerPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopOwnerPassBox.setHelperText(null);
            shopOwnerPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopOwnerPassBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        // if all validation checks pass, return true
        return true;
    }

    // this will clear the password text box whenever a user failed to login their account
    public void clearTextBox(){
        shopOwnerPass.setText(null);
    }
}