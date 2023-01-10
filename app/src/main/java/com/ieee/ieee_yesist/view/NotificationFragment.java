package com.ieee.ieee_yesist.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.adapters.AccomodationAdapter;
import com.ieee.ieee_yesist.adapters.NotificationHistoryAdapter;
import com.ieee.ieee_yesist.databinding.FragmentAccomodationBinding;
import com.ieee.ieee_yesist.databinding.FragmentNotificationBinding;
import com.ieee.ieee_yesist.model.AccomodationModel;
import com.ieee.ieee_yesist.model.NotificationModel;
import com.ieee.ieee_yesist.model.Sponsor;

import java.sql.Time;
import java.util.ArrayList;

/**
]
 */
public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;
    private RecyclerView notificationRV;
    private ArrayList<NotificationModel> notificationModelArrayList;
    private FirebaseFirestore db;
    private NotificationHistoryAdapter notifcationAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater,container,false);
        binding.backButton.setOnClickListener( v -> {
            Navigation.findNavController(requireActivity(), R.id.fragNavHost).popBackStack();
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        });
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        notificationRV = binding.RVnotifications;
        notificationModelArrayList = new ArrayList<NotificationModel>();

        notifcationAdapter = new NotificationHistoryAdapter(requireActivity(),notificationModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        notificationRV.setAdapter(notifcationAdapter);
        notificationRV.setLayoutManager(linearLayoutManager);

        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("notifications")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null)
                        {
                            return;
                        }
                        if(!value.isEmpty())
                            notificationModelArrayList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc != null) {
                                NotificationModel notification = doc.toObject(NotificationModel.class);
                                if(Timestamp.now().compareTo(notification.getExpiresOn())>0) {
                                    continue;
                                }
                                notificationModelArrayList.add(notification);
                                notifcationAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }


}