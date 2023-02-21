package com.example.mallspaceium.shopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mallspaceium.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShopperSignup extends AppCompatActivity {

    final String TAG = "FIRESTORE";
    final String userRole = "Shopper";
    private String userGender = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextInputEditText shopperFname, shopperLname, shopperDOB, shopperPhoneNum, shopperAddress, shopperEmail, shopperUname, shopperPass, shopperConfirmPass;
    private RadioButton selectedRadioID;
    private RadioGroup shopperGender;
    private Button ShopperSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_signup);

        shopperFname = (TextInputEditText) findViewById(R.id.ShopperFirstNameInput);
        shopperLname = (TextInputEditText) findViewById(R.id.ShopperLastNameInput);
        shopperDOB = (TextInputEditText) findViewById(R.id.ShopperDOBInput);
        shopperGender = (RadioGroup) findViewById(R.id.ShopperGenderRadioGroup);
        shopperPhoneNum = (TextInputEditText) findViewById(R.id.ShopperPhoneNumInput);
        shopperAddress = (TextInputEditText) findViewById(R.id.ShopperAddressInput);
        shopperEmail = (TextInputEditText) findViewById(R.id.ShopperEmailInput);
        shopperUname = (TextInputEditText) findViewById(R.id.ShopperUsernameInput);
        shopperPass = (TextInputEditText) findViewById(R.id.ShopperPasswordInput);
        shopperConfirmPass = (TextInputEditText) findViewById(R.id.ShopperConfirmPassInput);

        ShopperSignupButton = (Button) findViewById(R.id.ShopperSignupButton);
        ShopperSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shopperFnameInput = shopperFname.getText().toString();
                String shopperLnameInput = shopperLname.getText().toString();
                String shopperDOBInput = shopperDOB.getText().toString();
                String shopperGenderSelected = userGender;
                Integer shopperPhoneNumInput = shopperPhoneNum.getInputType();
                String shopperAddressInput = shopperAddress.getText().toString();
                String shopperEmailInput = shopperEmail.getText().toString();
                String shopperUnameInput = shopperUname.getText().toString();
                String shopperPassInput = shopperPass.getText().toString();
                String shopperConfirmPassInput = shopperConfirmPass.getText().toString();

                if(!shopperFnameInput.isEmpty() && !shopperLnameInput.isEmpty()) {
                    registerShopper(shopperFnameInput, shopperLnameInput, shopperDOBInput, shopperGenderSelected, shopperPhoneNumInput, shopperAddressInput, shopperEmailInput, shopperUnameInput, shopperPassInput, shopperConfirmPassInput);
                }
                else{
                    Toast.makeText(ShopperSignup.this,"Please make sure there are no empty fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // This method will register a new user to the firebase
    public void registerShopper(String shopperFname, String shopperLname, String shopperDOB, String shopperGender, Integer shopperPhoneNum, String shopperAddress, String shopperEmail, String shopperUname, String shopperPass, String shopperConfirmPass){
        // Register a new user with the following credentials
        Map<String, Object> shopper = new HashMap<>();
        shopper.put("shopper_firstName", shopperFname);
        shopper.put("shopper_lastName", shopperLname);
        shopper.put("shopper_birthdate", shopperDOB);
        shopper.put("shopper_gender", shopperGender);

        /*not yet fixed!, if ako siya eh string ok ra pero if eh integer nako siya kai isa ra ka number iyang eh store*/
        shopper.put("shopper_phoneNumber", shopperPhoneNum);

        shopper.put("shopper_address", shopperAddress);
        shopper.put("shopper_email", shopperEmail);
        shopper.put("shopper_username", shopperUname);
        shopper.put("shopper_password", shopperPass);
        shopper.put("shopper_confirmPassword", shopperConfirmPass);
        shopper.put("shopper_role", userRole);

        // Add a new document with a generated username ID
        db.collection("SHOPPERREGISTER").document(shopperEmail)
                .set(shopper)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + shopperEmail);
                        Toast.makeText(ShopperSignup.this, "Successfully registered " + shopperEmail, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ShopperSignup.this, ShopperSignin.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShopperSignup.this, "There's a problem registering this user!" + e, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Error adding document", e);
                    }
                });
    }

    /*not yet fixed!, mag search pako unsaon pag get text sa mga na selected nga radio button*/

    //This method will check the selected radio buttons from the radio group
    public void checkRadioGroup(View v){
        int selectedRadioButtonID = shopperGender.getCheckedRadioButtonId();
        selectedRadioID = findViewById(selectedRadioButtonID);
        userGender = selectedRadioID.getText().toString();
    }
}