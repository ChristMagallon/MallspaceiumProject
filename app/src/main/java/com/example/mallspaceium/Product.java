package com.example.mallspaceium;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mallspaceium.getproducts.MyAdapter;
import com.example.mallspaceium.getproducts.gettersetter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Product extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<gettersetter> products;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("fetching data...");
        progressDialog.show();

        rv = findViewById(R.id.recview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        db = FirebaseFirestore.getInstance();
        products = new ArrayList<gettersetter>();
        myAdapter = new  MyAdapter(this,products);

        rv.setAdapter(myAdapter);

        GetData();
    }

    public void GetData(){
        db.collection("ProductAdded")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(Product.this, "Firestore Error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                products.add(dc.getDocument().toObject(gettersetter.class));
                            }
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}