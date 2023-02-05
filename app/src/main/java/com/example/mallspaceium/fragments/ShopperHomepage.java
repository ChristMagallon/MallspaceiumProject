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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopperHomepage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopperHomepage extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopperHomepage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopperHomepage.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopperHomepage newInstance(String param1, String param2) {
        ShopperHomepage fragment = new ShopperHomepage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private CardView PopularShopsCardView, SalesDiscountsCardView, ProductsCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopper_homepage, container, false);

        PopularShopsCardView = view.findViewById(R.id.PopularShopsCardView);
        PopularShopsCardView.setOnClickListener(this);

        SalesDiscountsCardView = view.findViewById(R.id.SalesDiscountsCardView);
        SalesDiscountsCardView.setOnClickListener(this);

        ProductsCardView = view.findViewById(R.id.ProductsCardView);
        ProductsCardView.setOnClickListener(this);

        return view;
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.PopularShopsCardView:
                PopularShopsCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), PopularShop.class);
                        startActivity(i);
                    }
                });
                break;
            case R.id.SalesDiscountsCardView:
                SalesDiscountsCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Sales & Discounts Activity is still undermaintenance",Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.ProductsCardView:
                ProductsCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), Product.class);
                        startActivity(i);
                    }
                });
                break;
        }
    }
}