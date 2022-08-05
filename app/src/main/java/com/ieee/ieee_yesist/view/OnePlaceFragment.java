package com.ieee.ieee_yesist.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ieee.ieee_yesist.MainActivity;
import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.databinding.FragmentOnePlaceBinding;


import java.util.Formatter;


public class OnePlaceFragment extends Fragment {

    private FragmentOnePlaceBinding binding;
    private Bundle bundle;
    FusedLocationProviderClient client;



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

        binding.placeDescription.setText(bundle.getString("description"));
        binding.placeDescription.setMovementMethod(new ScrollingMovementMethod());
        binding.simpleRatingBar.setRating(bundle.getFloat("rating"));
        Glide.with(requireActivity()).load(bundle.getString("image")).thumbnail(0.05f).into(binding.oneplaceimage);




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

        client = LocationServices
                .getFusedLocationProviderClient(
                        getActivity());

        getCurrentLocation();



        return  view;
        // Inflate the layout for this fragment

    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        LocationManager locationManager
                = (LocationManager)getActivity()
                .getSystemService(
                        Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location
            client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(
                                @NonNull Task<Location> task)
                        {

                            // Initialize location
                            Location location
                                    = task.getResult();
                            // Check condition
                            if (location != null) {
                                // When location result is not
                                // null set latitude
                            binding.distance.setText(getDistance(location));
                            }
                            else {
                                // When location result is null
                                // initialize location request
                                LocationRequest locationRequest
                                        = new LocationRequest()
                                        .setPriority(
                                                LocationRequest
                                                        .PRIORITY_HIGH_ACCURACY)
                                        .setInterval(10000)
                                        .setFastestInterval(
                                                1000)
                                        .setNumUpdates(1);

                                // Initialize location call back
                                LocationCallback
                                        locationCallback
                                        = new LocationCallback() {
                                    @Override
                                    public void
                                    onLocationResult(
                                            LocationResult
                                                    locationResult)
                                    {
                                        // Initialize
                                        // location
                                        Location location1
                                                = locationResult
                                                .getLastLocation();
                                        // Set latitude
                                       binding.distance.setText(getDistance(location1));
                                    }
                                };

                                // Request location updates
                                client.requestLocationUpdates(
                                        locationRequest,
                                        locationCallback,
                                        Looper.myLooper());
                            }
                        }
                    });
        }
        else {
            // When location service is not enabled
            // open location setting
            startActivity(
                    new Intent(
                            Settings
                                    .ACTION_LOCATION_SOURCE_SETTINGS)
                            .setFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private String getDistance(Location location) {
        Location endPoint = new Location("locationPlace");
        endPoint.setLatitude(bundle.getDouble("Latitude"));
        endPoint.setLongitude(bundle.getDouble("Longitude"));
        double distance=location.distanceTo(endPoint);
        distance=distance/1000;

        Formatter fmt = new Formatter();
        fmt.format("%.2f", distance);
        String dist = "(" + fmt + " Km)";
        return  dist;
    }


}