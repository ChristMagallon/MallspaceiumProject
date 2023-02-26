package com.example.mallspaceium.shopper;

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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Pattern;

public class ShopperSignin extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    GoogleSignInClient mGoogleSignInClient;

    private TextInputLayout shopperEmailBox, shopperPassBox;
    private TextInputEditText shopperEmail, shopperPass;
    private Button ShopperSigninButton;
    private TextView ShopperForgotPassTextView;

    public ShopperSignin() {
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    private void updateUI(FirebaseUser user) {

        if(user!=null)
        {
            finish();
            mGoogleSignInClient.signOut();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_signin);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        // set helper text error for layout style outline box
        shopperEmailBox = (TextInputLayout) findViewById(R.id.ShopperEmailTextBox);
        shopperPassBox = (TextInputLayout) findViewById(R.id.ShopperPasswordTextBox);

        // get the input data from the views
        shopperEmail = (TextInputEditText) findViewById(R.id.ShopperEmailInput);
        shopperPass = (TextInputEditText) findViewById(R.id.ShopperPasswordInput);
        ShopperForgotPassTextView = (TextView) findViewById(R.id.ShopperForgotPassTextView);
        ShopperSigninButton = (Button) findViewById(R.id.ShopperSigninButton);

        ShopperForgotPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopperSignin.this, AccountRecovery.class));
            }
        });

        ShopperSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(validateLoginShopper()){
                  loginShopper();
              }
            }
        });
    }

    // login a user with the following credentials such as email and password
    public void loginShopper(){
        String shopperEmailInput = shopperEmail.getText().toString();
        String shopperPasswordInput = shopperPass.getText().toString();
        String user_email = shopperEmailInput;
        db.collection("SHOPPERREGISTER")
                .whereEqualTo("shopper_email", shopperEmailInput)
                .whereEqualTo("shopper_password", shopperPasswordInput)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {
                                Intent i = new Intent(ShopperSignin.this, HomePage.class);
                                i.putExtra("user_email", user_email);
                                startActivity(i);
                            } else {
                                clearTextBox();
                            }
                        }else{
                            Toast.makeText(ShopperSignin.this,"The email you entered isn’t connected to an account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean validateLoginShopper(){
        // validation patterns
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        // check if all required fields are filled
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

        // if all validation checks pass, return true
        return true;
    }

    // this will clear the password text box whenever a user failed to login their account
    public void clearTextBox(){
        shopperPass.setText(null);
    }
}