package com.ieee.ieee_yesist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.adapters.PlaceAdapter;
import com.ieee.ieee_yesist.databinding.FragmentPlacesBinding;
import com.ieee.ieee_yesist.model.OnPlaceClickListener;
import com.ieee.ieee_yesist.model.PlaceModel;

import java.util.ArrayList;

/**
 */
public class PlacesFragment extends Fragment implements OnPlaceClickListener {


    private FragmentPlacesBinding  binding ;
    private RecyclerView placeRV;
    private ArrayList<PlaceModel> placeModelArrayList ;
    private FirebaseFirestore db;
    private PlaceAdapter placeAdapter;





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlacesBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        placeRV = binding.RVplaces;
        placeModelArrayList = new ArrayList<PlaceModel>();

        Glide.with(requireActivity()).load("https://i.pinimg.com/originals/85/c4/c3/85c4c3131f7c4861d209c1f2a9f38408.jpg").into(binding.bengImage);

        placeAdapter = new PlaceAdapter(requireActivity(),placeModelArrayList);
        placeAdapter.setClickListener(this);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(),2);

        placeRV.setAdapter(placeAdapter);
        placeRV.setLayoutManager(gridLayoutManager);



        EventChangeListener();


       /* FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("places")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           placeModelArrayList = (ArrayList<PlaceModel>) task.getResult().toObjects(PlaceModel.class);
                           placeAdapter.notifyDataSetChanged();


                        } else {
                            Toast.makeText(requireActivity(), "Some Error Occured ", Toast.LENGTH_SHORT).show();
                        }




                    }

                });
*/



//        DocumentReference docRef = db.collection("places").document("lalbaghBotanicalGarden");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                PlaceModel place = documentSnapshot.toObject(PlaceModel.class);
//
//                assert place != null;
//                Glide.with(requireActivity())
//                        .load(place.getImage1())
//                        .into(binding.one);
//            }
//        });

    }

    private void EventChangeListener() {
        db.collection("places")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED)
                            {
                                placeModelArrayList.add(dc.getDocument().toObject(PlaceModel.class));
                            }
                            placeAdapter.notifyDataSetChanged();


                        }
                    }
                });
    }

    @Override
    public void onPlaceClick(View view, int pos,PlaceModel model) {


        Bundle bundle = new Bundle();

        bundle.putString("name",  model.getName());
        bundle.putString("location", model.getLocation());
        bundle.putString("description", model.getDescription());
        bundle.putString("image", model.getImage1());
        bundle.putFloat("rating",model.getStars());
        bundle.putDouble("Latitude",model.getLatlang().getLatitude());
        bundle.putDouble("Longitude",model.getLatlang().getLongitude());// Put anything what you want




      /*  Fragment fragment = new OnePlaceFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragNavHost, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/

        Navigation.findNavController(requireView()).navigate(R.id.action_placesFragment_to_onePlaceFragment,bundle);
    }
}