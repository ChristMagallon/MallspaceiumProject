package com.example.mallspaceium;

import static android.graphics.Color.convert;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;


import android.graphics.Bitmap;


import android.os.Bundle;


import android.provider.MediaStore;

import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;

import java.util.HashMap;
import java.util.Map;


public class AddProduct extends AppCompatActivity {

    Button addbtn;
    TextView pname, pdescription, pprice, pimage;
    ImageView image;
    Bitmap bitmap;
    FirebaseFirestore firestoredb = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        addbtn = findViewById(R.id.AddProductButton);
        pname = findViewById(R.id.ProductNameid);
        pdescription = findViewById(R.id.ProductDescriptionID);
        pprice = findViewById(R.id.ProductPriceID);
        pimage = findViewById(R.id.UploadProductImage);
        image = findViewById(R.id.UploadedImage);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddata();
            }
        });

        pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            bitmap = (Bitmap)data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    void adddata() {
        String convertedimage = BitMapToString(bitmap);

        // Create a new user with a first and last name
        Map<String, Object> ProductName = new HashMap<>();
        ProductName.put("Product Name", pname.getText().toString());
        ProductName.put("Product Description", pdescription.getText().toString());
        ProductName.put("Product Price", pprice.getText().toString());
        ProductName.put("Product Image", convertedimage);

        // Add a new document with a generated ID
        firestoredb.collection("users")
                .add(ProductName)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}