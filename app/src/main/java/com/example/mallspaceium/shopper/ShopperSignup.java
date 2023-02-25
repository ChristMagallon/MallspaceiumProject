package com.example.mallspaceium.shopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallspaceium.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class ShopperSignup extends AppCompatActivity {

    final String userRole = "Shopper";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextInputLayout shopperFnameBox, shopperLnameBox, shopperDOBBox, shopperPhoneNumBox, shopperAddressBox, shopperEmailBox, shopperUnameBox, shopperPassBox, shopperConfirmPassBox;
    private TextInputEditText shopperFname, shopperLname, shopperDOB, shopperPhoneNum, shopperAddress, shopperEmail, shopperUname, shopperPass, shopperConfirmPass;
    private RadioGroup shopperGenderRadioGroup;
    private RadioButton shopperGenderRadioButton;
    private Button ShopperSignupButton;
    private Calendar calendar = Calendar.getInstance();

    // global initialization for radio group
    String selectedShopperGender = "";
    int selectedRadioButtonID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_signup);

        // set helper text error for layout style outline box
        shopperFnameBox = (TextInputLayout) findViewById(R.id.ShopperFirstNameTextBox);
        shopperLnameBox = (TextInputLayout) findViewById(R.id.ShopperLastNameTextBox);
        shopperDOBBox = (TextInputLayout) findViewById(R.id.ShopperDOBTextBox);
        shopperPhoneNumBox = (TextInputLayout) findViewById(R.id.ShopperPhoneNumTextBox);
        shopperAddressBox = (TextInputLayout) findViewById(R.id.ShopperAddressTextBox);
        shopperEmailBox = (TextInputLayout) findViewById(R.id.ShopperEmailTextBox);
        shopperUnameBox = (TextInputLayout) findViewById(R.id.ShopperUsernameTextBox);
        shopperPassBox = (TextInputLayout) findViewById(R.id.ShopperPasswordTextBox);
        shopperConfirmPassBox = (TextInputLayout) findViewById(R.id.ShopperConfirmPassTextBox);

        // get the input data from the views
        shopperFname = (TextInputEditText) findViewById(R.id.ShopperFirstNameInput);
        shopperLname = (TextInputEditText) findViewById(R.id.ShopperLastNameInput);
        shopperDOB = (TextInputEditText) findViewById(R.id.ShopperDOBInput);
        shopperGenderRadioGroup = (RadioGroup) findViewById(R.id.ShopperGenderRadioGroup);
        shopperPhoneNum = (TextInputEditText) findViewById(R.id.ShopperPhoneNumInput);
        shopperAddress = (TextInputEditText) findViewById(R.id.ShopperAddressInput);
        shopperEmail = (TextInputEditText) findViewById(R.id.ShopperEmailInput);
        shopperUname = (TextInputEditText) findViewById(R.id.ShopperUsernameInput);
        shopperPass = (TextInputEditText) findViewById(R.id.ShopperPasswordInput);
        shopperConfirmPass = (TextInputEditText) findViewById(R.id.ShopperConfirmPassInput);
        ShopperSignupButton = (Button) findViewById(R.id.ShopperSignupButton);

        shopperDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog();
            }
        });

        ShopperSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateRegisterShopper()) {
                    registerShopper();
                }
            }
        });
    }

    // register method
    public void registerShopper() {
        String shopperFnameInput = shopperFname.getText().toString();
        String shopperLnameInput = shopperLname.getText().toString();
        String shopperDOBInput = shopperDOB.getText().toString();

        shopperGenderRadioButton = findViewById(selectedRadioButtonID);
        selectedShopperGender = shopperGenderRadioButton.getText().toString();
        String shopperGenderInput = selectedShopperGender;

        String shopperPhoneNumInput = shopperPhoneNum.getText().toString();
        String shopperAddressInput = shopperAddress.getText().toString();
        String shopperEmailInput = shopperEmail.getText().toString();
        String shopperUnameInput = shopperUname.getText().toString();
        String shopperPassInput = shopperPass.getText().toString();
        String shopperConfirmPassInput = shopperConfirmPass.getText().toString();

        // register a new user with the following credentials
        Map<String, Object> shopper = new HashMap<>();
        shopper.put("shopper_firstName", shopperFnameInput);
        shopper.put("shopper_lastName", shopperLnameInput);
        shopper.put("shopper_birthdate", shopperDOBInput);
        shopper.put("shopper_gender", shopperGenderInput);
        shopper.put("shopper_phoneNumber", shopperPhoneNumInput);
        shopper.put("shopper_address", shopperAddressInput);
        shopper.put("shopper_email", shopperEmailInput);
        shopper.put("shopper_username", shopperUnameInput);
        shopper.put("shopper_password", shopperPassInput);
        shopper.put("shopper_confirmPassword", shopperConfirmPassInput);
        shopper.put("shopper_role", userRole);

        // add a new document with a generated username id
        db.collection("SHOPPERREGISTER").document(shopperEmailInput)
                .set(shopper)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ShopperSignup.this, "Successfully registered " + shopperEmail, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ShopperSignup.this, ShopperSignin.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShopperSignup.this, "There's a problem registering this user!" + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean validateRegisterShopper() {
        // validation patterns
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        String passwordPattern = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        // validate selected radio buttons from the radio group
        selectedRadioButtonID = shopperGenderRadioGroup.getCheckedRadioButtonId();

        // check if all required fields are filled
        if (shopperFname.getText().toString().trim().isEmpty()) {
            shopperFnameBox.setHelperText("Field can't be empty");
            shopperFnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperFnameBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopperFnameBox.setHelperText(null);
            shopperFnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopperFnameBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopperLname.getText().toString().trim().isEmpty()) {
            shopperLnameBox.setHelperText("Field can't be empty");
            shopperLnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperLnameBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopperLnameBox.setHelperText(null);
            shopperLnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopperLnameBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopperDOB.getText().toString().trim().isEmpty()) {
            shopperDOBBox.setHelperText("Field can't be empty");
            shopperDOBBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperDOBBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopperDOBBox.setHelperText(null);
            shopperDOBBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopperDOBBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (selectedRadioButtonID == -1) {
            Toast.makeText(ShopperSignup.this, "Select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (shopperPhoneNum.getText().toString().trim().isEmpty()) {
            shopperPhoneNumBox.setHelperText("Field can't be empty");
            shopperPhoneNumBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperPhoneNumBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (shopperPhoneNum.getText().toString().trim().length() < 11 || shopperPhoneNum.getText().toString().trim().length() > 11) {
            shopperPhoneNumBox.setHelperText("Invalid phone number");
            shopperPhoneNumBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperPhoneNumBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopperPhoneNumBox.setHelperText(null);
            shopperPhoneNumBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopperPhoneNumBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopperAddress.getText().toString().trim().isEmpty()) {
            shopperAddressBox.setHelperText("Field can't be empty");
            shopperAddressBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperAddressBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopperAddressBox.setHelperText(null);
            shopperAddressBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopperAddressBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopperEmail.getText().toString().trim().isEmpty()) {
            shopperEmailBox.setHelperText("Field can't be empty");
            shopperEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperEmailBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (!emailPattern.matcher(shopperEmail.getText().toString().trim()).matches()) {
            shopperEmailBox.setHelperText("Invalid email");
            shopperEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperEmailBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopperEmailBox.setHelperText(null);
            shopperEmailBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopperEmailBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopperUname.getText().toString().trim().isEmpty()) {
            shopperUnameBox.setHelperText("Field can't be empty");
            shopperUnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperUnameBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (shopperUname.getText().toString().trim().length() >= 15) {
            shopperUnameBox.setHelperText("Username is too long");
            shopperUnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperUnameBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (shopperUname.getText().toString().contains(" ")) {
            shopperUnameBox.setHelperText("Spaces are not allowed");
            shopperUnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperUnameBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopperUnameBox.setHelperText(null);
            shopperUnameBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopperUnameBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopperPass.getText().toString().trim().isEmpty()) {
            shopperPassBox.setHelperText("Field can't be empty");
            shopperPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (shopperPass.getText().toString().contains(" ")) {
            shopperPassBox.setHelperText("Spaces are not allowed");
            shopperPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopperPassBox.setHelperText(null);
            shopperPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopperPassBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        if (shopperConfirmPass.getText().toString().trim().isEmpty()) {
            shopperConfirmPassBox.setHelperText("Field can't be empty");
            shopperConfirmPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperConfirmPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (shopperConfirmPass.getText().toString().contains(" ")) {
            shopperConfirmPassBox.setHelperText("Spaces are not allowed");
            shopperConfirmPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperConfirmPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else if (!shopperConfirmPass.getText().toString().equals(shopperPass.getText().toString())) {
            shopperConfirmPassBox.setHelperText("Password does not match");
            shopperConfirmPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            shopperConfirmPassBox.setBoxStrokeColor(getResources().getColor(R.color.red));
            return false;
        } else {
            shopperConfirmPassBox.setHelperText(null);
            shopperConfirmPassBox.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.sky_blue)));
            shopperConfirmPassBox.setBoxStrokeColor(getResources().getColor(R.color.sky_blue));
        }

        // if all validation checks pass, return true
        return true;
    }

    // show the date time picker dialog
    public void datePickerDialog(){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }
        };

        // hide the soft keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(shopperDOB.getWindowToken(), 0);

        new DatePickerDialog(ShopperSignup.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    // this will set the EditText with selected date
    private void updateCalendar() {
        String Format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.getDefault());
        shopperDOB.setText(sdf.format(calendar.getTime()));
    }
}