package com.ieee.ieee_yesist.view.Tracks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.adapters.TrackListAdapter;
import com.ieee.ieee_yesist.model.TrackList;
import com.ieee.ieee_yesist.model.YesistHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TracksFragment extends Fragment implements TrackListAdapter.OnTrackListener {
    RecyclerView trackRecycler;
    TrackListAdapter trackListAdapter;
    List<TrackList> trackList;
    ImageView imgProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks, container, false);
        trackRecycler=view.findViewById(R.id.tracks_recycler);

        callApi();
//        trackList = new ArrayList<>();
//        trackList.add(new TrackList("Innovation Challenge",R.drawable.innovation));
//        trackList.add(new TrackList("Maker Fair",R.drawable.maker_fair));
//        trackList.add(new TrackList("Junior Einstein",R.drawable.jr_einstein));
//        trackList.add(new TrackList("WePOWER",R.drawable.wepower));
//        trackList.add(new TrackList("Special Track",R.drawable.special_track_new_bg));
//
//        setTrackRecycler(trackList);

        /*imgProfile = view.findViewById(R.id.imageProfile);
        imgProfile.setOnClickListener( v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_tracksFragment_to_profileFragment);
        });*/

        return view;
    }

    private void callApi() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(getString(R.string.firebase_database_url)+"/tracks.json",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        ArrayList<String> first = new ArrayList<>();
                        ArrayList<String> second = new ArrayList<>();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject trackObj = jsonArray.getJSONObject(i);
                                String firstPrize = trackObj.getString("track_1stPrize");
                                String secondPrize = trackObj.getString("track_2ndPrize");
                                first.add(i,firstPrize);
                                second.add(i,secondPrize);
                            }
                            catch(JSONException e) {
//                                Log.e("Error: " + e.toString());
                            }
                        }

                            trackList = new ArrayList<>();
                            trackList.add(new TrackList("Innovation Challenge", R.drawable.innovation, first.get(0),second.get(0)));
                            trackList.add(new TrackList("Maker Fair", R.drawable.maker_fair,first.get(1),second.get(1)));
                            trackList.add(new TrackList("Junior Einstein", R.drawable.jr_einstein,first.get(2),second.get(2)));
                            trackList.add(new TrackList("WePOWER", R.drawable.wepower,first.get(3),second.get(3)));
                            trackList.add(new TrackList("Special Track", R.drawable.special_track_new_bg,first.get(4),second.get(4)));
                            trackList.add(new TrackList("IEngage Track", R.drawable.ie_engage_track,first.get(5),second.get(5)));

                            setTrackRecycler(trackList);

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Error", "ER");
                    }
                });

        queue.add(request);
    }

    private void setTrackRecycler(List<TrackList> trackList) {
        if(isAdded()){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        trackRecycler.setLayoutManager(layoutManager);
        trackListAdapter = new TrackListAdapter(requireContext(),trackList,this);
        trackRecycler.setAdapter(trackListAdapter);}

    }

    @Override
    public void onTrackClick(int position) {
        Log.d("name","onTrackClick: clicked.");
        Bundle bundle = new Bundle();
        bundle.putString("trackName",trackList.get(position).getTrackName());
        bundle.putInt("trackImage",trackList.get(position).getImageUrl());
        Navigation.findNavController(requireView()).navigate(R.id.action_tracksFragment_to_trackDetailsFragment,bundle);
        /*Fragment fragment = new TrackDetailsFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragNavHost,fragment,null);
        transaction.commit();*/
    }
}