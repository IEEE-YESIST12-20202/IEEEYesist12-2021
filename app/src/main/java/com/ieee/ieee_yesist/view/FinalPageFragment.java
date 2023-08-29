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

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.databinding.FragmentFinalPageBinding;
import com.ieee.ieee_yesist.util.PhotoFullPopupWindow;


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
        binding.viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code to show image in full screen:
                new PhotoFullPopupWindow(getActivity(), R.layout.popup_photo_full, view, R.drawable.map_egypt);

            }
        });


        binding.navigateToFinale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=Arab+Academy+for+Science,+Technology+and+Maritime+Transport,+Alexandria,+Egypt");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        binding.byTaxiDes.setText(Html.fromHtml(getResources().getString(R.string.reachbytaxi)));
        binding.byTaxiDes.setMovementMethod(LinkMovementMethod.getInstance());
        View.OnClickListener reachByTaxiOnClickListener = v -> {
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
        };
        binding.publicTransportDescription.setText(Html.fromHtml(getResources().getString(R.string.byPublicMeans)));
        binding.publicTransportDescription.setMovementMethod(LinkMovementMethod.getInstance());
        binding.reachByTaxiCardview.setOnClickListener(reachByTaxiOnClickListener);
        binding.arrowDown.setOnClickListener(reachByTaxiOnClickListener);

        View.OnClickListener reachByPublicTransportOnClickListener = v -> {
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
        };

        binding.reachByPublicTransportCardview.setOnClickListener(reachByPublicTransportOnClickListener);
        binding.pubTransArrowDown.setOnClickListener(reachByPublicTransportOnClickListener);

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
                    intent.setData(Uri.parse("http://360.aast.edu/abokir-e/"));
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    Toast.makeText(getContext(), "Error text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}