package com.example.mallspaceium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ShopperProfile extends Fragment {


    TextView name, email, phone;
    CardView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopper_profile, container, false);

        name = view.findViewById(R.id.profilename);
        email = view.findViewById(R.id.profileemail);
        phone = view.findViewById(R.id.profilephone);
        logout = view.findViewById(R.id.Logout);
        FirebaseAuth.getInstance();
        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        phone.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),ShopperSignin.class));
                getActivity().finish();
            }
        });





        return view;
    }
}