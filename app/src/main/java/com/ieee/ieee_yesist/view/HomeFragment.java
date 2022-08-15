package com.ieee.ieee_yesist.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.SubmitAbstractActivity;
import com.ieee.ieee_yesist.adapters.YesistHomeAdapter;
import com.ieee.ieee_yesist.model.Sponsor;
import com.ieee.ieee_yesist.model.YesistHome;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import im.crisp.client.ChatActivity;
import im.crisp.client.Crisp;

public class HomeFragment extends Fragment {
    RecyclerView yesistRecyler;
    YesistHomeAdapter yesistHomeAdapter;
    List<YesistHome> yesistHomeList;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yesistRecyler=view.findViewById(R.id.yesist_recycler);

        Crisp.configure(getActivity().getApplicationContext(), "35d59f9c-e6df-416d-9364-5356d91fc5df");
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crispIntent = new Intent(getActivity(), ChatActivity.class);
                startActivity(crispIntent);
            }
        });



        yesistHomeList = new ArrayList<>();
        yesistHomeList.add(new YesistHome(R.string.knowledge_title,getString(R.string.knowledge)));
        yesistHomeList.add(new YesistHome(R.string.prof_connect_title,getString(R.string.prof_connect)));
        yesistHomeList.add(new YesistHome(R.string.arena_title,getString(R.string.arena)));
        yesistHomeList.add(new YesistHome(R.string.time_title,getString(R.string.time)));
        yesistHomeList.add(new YesistHome(R.string.recognised_title,getString(R.string.recognised)));
        yesistHomeList.add(new YesistHome(R.string.presence_title,getString(R.string.presence)));
        setYesistRecycler(yesistHomeList);

        db = FirebaseFirestore.getInstance();
        db.collection("cards").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("CARDS", "Listen failed.", error);
                return;
            }
            if(value.isEmpty())
                view.findViewById(R.id.home_card).setVisibility(View.GONE);
            for (QueryDocumentSnapshot doc : value) {
                if (doc.getId().equals("homeCard")) {
                    TextView cardHeader = view.findViewById(R.id.card_header);
                    TextView cardDescription = view.findViewById(R.id.card_description);
                    Button cardButton = view.findViewById(R.id.card_button);
                    Button submitButton = view.findViewById(R.id.card_button);
                    cardHeader.setText(doc.getString("card_header"));
                    if (doc.getString("card_header").equals(""))
                        cardHeader.setVisibility(View.GONE);
                    cardDescription.setText(doc.getString("card_description"));
                    if (doc.getString("card_description").equals(""))
                        cardDescription.setVisibility(View.GONE);
                    cardButton.setText(doc.getString("card_button_text"));
                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent submitIntent = new Intent(getActivity(), SubmitAbstractActivity.class);
                            submitIntent.putExtra("url", doc.getString("card_button_url"));
                            startActivity(submitIntent);
                        }
                    });
                }
            }
        });
    }

    private void setYesistRecycler(List<YesistHome> yesistHomeList){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
            yesistRecyler.setLayoutManager(layoutManager);
            yesistRecyler.setHasFixedSize(true);
            yesistRecyler.setNestedScrollingEnabled(false);
            yesistHomeAdapter = new YesistHomeAdapter(requireContext(),yesistHomeList);
            yesistRecyler.setAdapter(yesistHomeAdapter);
    }
}