package com.example.mallspaceium.shopowner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mallspaceium.R;
import com.example.mallspaceium.shopper.ShopperSignin;
import com.example.mallspaceium.shopper.ShopperSignup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ShopOwnerSignup extends AppCompatActivity {

    final String userRole = "ShopOwner";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextInputLayout shopOwnerFnameBox, shopOwnerLnameBox, shopNameBox, shopDescBox, shopEmailBox, shopPhoneNumBox, shopAddressBox, shopOwnerPassBox, shopOwnerConfirmPassBox;
    private TextInputEditText shopOwnerFname, shopOwnerLname, shopName, shopDesc, shopEmail, shopPhoneNum, shopAddress, shopOwnerPass, shopOwnerConfirmPass;

    private Button ShopOwnerSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_signup);

        // set helper text error for layout style outline box
        shopOwnerFnameBox = (TextInputLayout) findViewById(R.id.ShopOwnerFirstNameTextBox);
        shopOwnerLnameBox = (TextInputLayout) findViewById(R.id.ShopOwnerLastNameTextBox);
        shopNameBox = (TextInputLayout) findViewById(R.id.ShopNameTextBox);
        shopDescBox = (TextInputLayout) findViewById(R.id.ShopDescriptionTextBox);
        shopEmailBox = (TextInputLayout) findViewById(R.id.ShopEmailTextBox);
        shopPhoneNumBox = (TextInputLayout) findViewById(R.id.ShopPhoneNumTextBox);
        shopAddressBox = (TextInputLayout) findViewById(R.id.ShopAddressTextBox);
        shopOwnerPassBox = (TextInputLayout) findViewById(R.id.ShopOwnerPasswordTextBox);
        shopOwnerConfirmPassBox = (TextInputLayout) findViewById(R.id.ShopOwnerConfirmPassTextBox);

        // get the input data from the views
        shopOwnerFname = (TextInputEditText) findViewById(R.id.ShopOwnerFirstNameInput);
        shopOwnerLname = (TextInputEditText) findViewById(R.id.ShopOwnerLastNameInput);
        shopName = (TextInputEditText) findViewById(R.id.ShopNameInput);
        shopDesc = (TextInputEditText) findViewById(R.id.ShopDescriptionInput);
        shopEmail = (TextInputEditText) findViewById(R.id.ShopEmailInput);
        shopPhoneNum = (TextInputEditText) findViewById(R.id.ShopPhoneNumInput);
        shopAddress = (TextInputEditText) findViewById(R.id.ShopAddressInput);
        shopOwnerPass = (TextInputEditText) findViewById(R.id.ShopOwnerPasswordInput);
        shopOwnerConfirmPass = (TextInputEditText) findViewById(R.id.ShopOwnerConfirmPassInput);

        ShopOwnerSignupButton = findViewById(R.id.ShopOwnerSignupButton);
        ShopOwnerSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateRegisterShopOwner()) {
                    registerShopOwner();
                }
            }
        });
    }

    // register method
    public void registerShopOwner(){
        String shopOwnerFnameInput = shopOwnerFname.getText().toString();
        String shopOwnerLnameInput = shopOwnerLname.getText().toString();
        String shopNameInput = shopName.getText().toString();
        String shopDescInput = shopDesc.getText().toString();
        String shopEmailInput = shopEmail.getText().toString();
        String shopPhoneNumInput = shopPhoneNum.getText().toString();
        String shopAddressInput = shopAddress.getText().toString();
        String shopOwnerPassInput = shopOwnerPass.getText().toString();
        String shopOwnerConfirmPassInput = shopOwnerConfirmPass.getText().toString();

        // register a new user with the following credentials
        Map<String, Object> shopOwner = new HashMap<>();
        shopOwner.put("shopOwner_firstName", shopOwnerFnameInput.substring(0, 1).toUpperCase() + shopOwnerFnameInput.substring(1));
        shopOwner.put("shopOwner_lastName", shopOwnerLnameInput.substring(0, 1).toUpperCase() + shopOwnerLnameInput.substring(1));
        shopOwner.put("shop_name", shopNameInput);
        shopOwner.put("shop_desc", shopDescInput);
        shopOwner.put("shop_email", shopEmailInput);
        shopOwner.put("shop_phoneNumber", shopPhoneNumInput);
        shopOwner.put("shop_address", shopAddressInput);
        shopOwner.put("shopOwner_password", shopOwnerPassInput);
        shopOwner.put("shopOwner_confirmPassword", shopOwnerConfirmPassInput);
        shopOwner.put("shopper_role", userRole);

        // add a new document with a generated username id
        db.collection("SHOPOWNERREGISTER").document(shopEmailInput)
                .set(shopOwner)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ShopOwnerSignup.this, "Successfully registered " + shopEmailInput, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ShopOwnerSignup.this, ShopOwnerSignin.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShopOwnerSignup.this, "There's a problem registering this user!" + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean validateRegisterShopOwner(){
        // validation patterns
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        // check if all required fields are filled
        if (shopOwnerFname.getText().toString().trim().isEmpty()) {
            shopOwnerFnameBox.setHelperText("Field can't be empty");
            shopOwnerFnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopOwnerFnameBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopOwnerFnameBox.setHelperText(null);
            shopOwnerFnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopOwnerFnameBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopOwnerLname.getText().toString().trim().isEmpty()) {
            shopOwnerLnameBox.setHelperText("Field can't be empty");
            shopOwnerLnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopOwnerLnameBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopOwnerLnameBox.setHelperText(null);
            shopOwnerLnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopOwnerLnameBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopName.getText().toString().trim().isEmpty()) {
            shopNameBox.setHelperText("Field can't be empty");
            shopNameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopNameBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopNameBox.setHelperText(null);
            shopNameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopNameBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopDesc.getText().toString().trim().isEmpty()) {
            shopDescBox.setHelperText("Field can't be empty");
            shopDescBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopDescBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopDescBox.setHelperText(null);
            shopDescBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopDescBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopEmail.getText().toString().trim().isEmpty()) {
            shopEmailBox.setHelperText("Field can't be empty");
            shopEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopEmailBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (!emailPattern.matcher(shopEmail.getText().toString().trim()).matches()) {
            shopEmailBox.setHelperText("Invalid email");
            shopEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopEmailBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopEmailBox.setHelperText(null);
            shopEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopEmailBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopPhoneNum.getText().toString().trim().isEmpty()) {
            shopPhoneNumBox.setHelperText("Field can't be empty");
            shopPhoneNumBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopPhoneNumBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (shopPhoneNum.getText().toString().trim().length() < 11 || shopPhoneNum.getText().toString().trim().length() > 11) {
            shopPhoneNumBox.setHelperText("Invalid phone number");
            shopPhoneNumBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopPhoneNumBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopPhoneNumBox.setHelperText(null);
            shopPhoneNumBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopPhoneNumBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopEmail.getText().toString().trim().isEmpty()) {
            shopEmailBox.setHelperText("Field can't be empty");
            shopEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopEmailBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopEmailBox.setHelperText(null);
            shopEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopEmailBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
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

        if (shopOwnerConfirmPass.getText().toString().trim().isEmpty()) {
            shopOwnerConfirmPassBox.setHelperText("Field can't be empty");
            shopOwnerConfirmPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopOwnerConfirmPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (shopOwnerConfirmPass.getText().toString().contains(" ")) {
            shopOwnerConfirmPassBox.setHelperText("Spaces are not allowed");
            shopOwnerConfirmPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopOwnerConfirmPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (!shopOwnerConfirmPass.getText().toString().equals(shopOwnerPass.getText().toString())) {
            shopOwnerConfirmPassBox.setHelperText("Password does not match");
            shopOwnerConfirmPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopOwnerConfirmPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopOwnerConfirmPassBox.setHelperText(null);
            shopOwnerConfirmPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopOwnerConfirmPassBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        // if all validation checks pass, return true
        return true;
    }
}