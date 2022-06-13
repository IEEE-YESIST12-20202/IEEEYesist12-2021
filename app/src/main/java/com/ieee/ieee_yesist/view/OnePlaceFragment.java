package com.ieee.ieee_yesist.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.databinding.FragmentOnePlaceBinding;


public class OnePlaceFragment extends Fragment {

    private FragmentOnePlaceBinding binding;
    private Bundle bundle;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.onePlacename.setText(bundle.getString("name"));
        binding.opname2.setText(bundle.getString("name"));
        binding.description.setText(bundle.getString("description"));
        binding.simpleRatingBar.setRating(bundle.getFloat("rating"));
        Glide.with(requireActivity()).load(bundle.getString("image")).into(binding.oneplaceimage);




        binding.nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+bundle.getString("location"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOnePlaceBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        bundle = this.getArguments();
        return  view;
        // Inflate the layout for this fragment

    }

}