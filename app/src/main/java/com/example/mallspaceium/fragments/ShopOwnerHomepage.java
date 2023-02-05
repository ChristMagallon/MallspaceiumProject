package com.example.mallspaceium.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mallspaceium.PopularShop;
import com.example.mallspaceium.Product;
import com.example.mallspaceium.R;

public class ShopOwnerHomepage extends Fragment {



    private CardView PopularShopsCardView, SalesDiscountsCardView, ProductsCardView, AdvertiseProductsCardView, PostSalesDiscountsCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_owner_homepage, container, false);

        PopularShopsCardView = view.findViewById(R.id.PopularShopsCardView);
        SalesDiscountsCardView = view.findViewById(R.id.SalesDiscountsCardView);
        ProductsCardView = view.findViewById(R.id.ProductsCardView);
        AdvertiseProductsCardView = view.findViewById(R.id.AdvertiseProductsCardView);
        PostSalesDiscountsCardView = view.findViewById(R.id.PostSalesDiscountsCardView);


        PopularShopsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PopularShop.class);
                startActivity(i);
            }
        });

        SalesDiscountsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Sales & Discounts Activity is still undermaintenance", Toast.LENGTH_LONG).show();
            }
        });

        ProductsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Product.class);
                startActivity(i);
            }
        });

        AdvertiseProductsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Post Sales & Discounts is still undermaintenance", Toast.LENGTH_LONG).show();
            }
        });

        PostSalesDiscountsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Advertise Products is still undermaintenance", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}