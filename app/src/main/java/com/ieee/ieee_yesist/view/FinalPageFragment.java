package com.ieee.ieee_yesist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    }



}