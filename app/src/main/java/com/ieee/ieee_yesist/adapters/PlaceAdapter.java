package com.ieee.ieee_yesist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.model.OnPlaceClickListener;
import com.ieee.ieee_yesist.model.PlaceModel;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.Viewholder>{


    private Context context;
    private ArrayList<PlaceModel> placeModelArrayList;
    private OnPlaceClickListener clickListener;

    public void setClickListener (OnPlaceClickListener listener)
    {
        this.clickListener = listener;
    }

    public  PlaceAdapter(Context context,ArrayList<PlaceModel> placeModelArrayList)
    {
        this.context = context;
        this.placeModelArrayList = placeModelArrayList;
    }
    @NonNull
    @Override
    public PlaceAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.Viewholder holder, int position) {
        PlaceModel model = placeModelArrayList.get(holder.getAdapterPosition());
        holder.pname.setText(model.getName());
        Glide.with(context)
                .load(model.getImage1())

                .into(holder.pimage);


    }

    @Override
    public int getItemCount() {
        return placeModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder  implements View.OnClickListener
    {
        private ImageView pimage;
        private TextView pname;

        public  Viewholder(@NonNull View itemView)
        {
            super(itemView);
            pimage= itemView.findViewById(R.id.placeimage);
            pname =  itemView.findViewById(R.id.placeName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onPlaceClick(v, getAdapterPosition(),placeModelArrayList.get(getAdapterPosition()));

        }
    }
}