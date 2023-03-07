package com.example.mallspaceium.getproducts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallspaceium.R;

import java.util.ArrayList;
import android.util.Base64;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    // variable for our array list and context
    private ArrayList<gettersetter> arrayList;
    private Context context;

    // constructor
    public MyAdapter(Context context,ArrayList<gettersetter> arrayList) {
        this.arrayList = arrayList;
        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showproductdata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        gettersetter modal = arrayList.get(position);
        holder.name.setText(modal.getProductName());
        holder.price.setText(modal.getProductPrice());
        holder.image.setImageBitmap(getUserImage(modal.getImage()));

        // below line is to add on click listener for our recycler view item.
     /**   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are calling an intent.
                Intent i = new Intent(context, Update.class);

                // below we are passing all our values.
                i.putExtra("name", modal.getname());
                i.putExtra("cash", modal.getcash());
                i.putExtra( "date", modal.getdate());
                // starting our activity.
                context.startActivity(i);
            }
        });**/

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView name, price;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            name = itemView.findViewById(R.id.pname);
            price = itemView.findViewById(R.id.pprice);
            image = itemView.findViewById(R.id.pimage);
        }
    }

    public Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0,bytes.length);
    }


}