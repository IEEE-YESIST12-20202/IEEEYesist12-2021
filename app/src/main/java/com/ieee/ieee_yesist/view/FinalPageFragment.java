package com.ieee.ieee_yesist.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.databinding.FragmentFinalPageBinding;


public class FinalPageFragment extends Fragment {

    private FragmentFinalPageBinding binding;

    public FinalPageFragment() {
        // Required empty public constructor
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    binding = FragmentFinalPageBinding.inflate(inflater,container,false);
    View view = binding.getRoot();
    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.acoomadationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_finalPageFragment_to_accomodationFragment);
            }
        });


        binding.navigateToFinale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=Sri+Venkateshwara+College+of+Engineering+Vidyanagar,+Kempegowda+International+Airport+Road,+Bengaluru");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        binding.reachByTaxiCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.byTaxiDes.getVisibility() == View.VISIBLE)
                {
                    TransitionManager.beginDelayedTransition(binding.getRoot(), new AutoTransition());
                    binding.byTaxiDes.setVisibility(View.GONE);
                    binding.arrowDown.setImageResource(R.drawable.ic__arrow_down);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(binding.getRoot(), new AutoTransition());
                    binding.byTaxiDes.setVisibility(View.VISIBLE);
                    binding.arrowDown.setImageResource(R.drawable.ic_arrow_up);
                }
            }
        });

        binding.reachByPublicTransportCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.publicTransportDescription.getVisibility()==View.VISIBLE){
                    TransitionManager.beginDelayedTransition(binding.getRoot(), new AutoTransition());
                    binding.publicTransportDescription.setVisibility(View.GONE);
                    binding.pubTransArrowDown.setImageResource(R.drawable.ic__arrow_down);
                }
                else {
                    TransitionManager.beginDelayedTransition(binding.getRoot(), new AutoTransition());
                    binding.publicTransportDescription.setVisibility(View.VISIBLE);
                    binding.pubTransArrowDown.setImageResource(R.drawable.ic_arrow_up);
                }
            }
        });

        binding.svgWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                try {
                    intent.setData(Uri.parse("https://www.svcengg.edu.in/"));
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    Toast.makeText(getContext(), "Error text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.svg3Dtour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                try {
                    intent.setData(Uri.parse("https://www.easytourz.com/BT-EmabedTour/all/5d111f3d2f317eb1"));
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    Toast.makeText(getContext(), "Error text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}