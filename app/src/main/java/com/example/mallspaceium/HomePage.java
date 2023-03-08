package com.example.mallspaceium;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TabLayout HomePageTabLayout;
    private ViewPager2 ViewPager2;
    private MyFragmentAdapterShopper adapterShopper;
    private MyFragmentAdapterShopOwner adapterShopOwner;

    // global declaration for fetching user role
    String user_role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        HomePageTabLayout = findViewById(R.id.HomePageTabLayout);
        ViewPager2 = findViewById(R.id.ViewPager2);

        searchUserShopper();
        searchUserShopOwner();

        HomePageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                HomePageTabLayout.getTabAt(position).select();
            }
        });
    }

    // this will search the user role
    public void searchUserShopper() {
        Intent intent = getIntent();
        String user_email = intent.getStringExtra("user_email");

        db.collection("SHOPPERREGISTER")
                .document(user_email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // store the name value in a local variable here
                                user_role = document.getString("shopper_role");
                                if (user_role.equals("Shopper")) {
                                    fragmentShopper(user_role);
                                } else if (user_role.equals("ShopOwner")) {
                                    fragmentShopOwner(user_role);
                                }
                            } else {
                                Log.d("DEBUGG", "No such document");
                            }
                        } else {
                            Log.d("DEBUGG", "Error getting document: ", task.getException());
                        }
                    }
                });
    }

    public void searchUserShopOwner() {
        Intent intent = getIntent();
        String user_email = intent.getStringExtra("user_email");

        db.collection("SHOPOWNERREGISTER")
                .document(user_email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // store the name value in a local variable here
                                user_role = document.getString("shopper_role");
                                if (user_role.equals("Shopper")) {
                                    fragmentShopper(user_role);
                                } else if (user_role.equals("ShopOwner")) {
                                    fragmentShopOwner(user_role);
                                }
                            } else {
                                Log.d("DEBUGG", "No such document");
                            }
                        } else {
                            Log.d("DEBUGG", "Error getting document: ", task.getException());
                        }
                    }
                });
    }

    public void fragmentShopper(String user_role){
        Log.d("VALIDATE USER ROLE", user_role);
        adapterShopper = new MyFragmentAdapterShopper(this);
        ViewPager2.setAdapter(adapterShopper);
    }

    public void fragmentShopOwner(String user_role){
        Log.d("VALIDATE USER ROLE", user_role);
        adapterShopOwner = new MyFragmentAdapterShopOwner(this);
        ViewPager2.setAdapter(adapterShopOwner);
    }
}