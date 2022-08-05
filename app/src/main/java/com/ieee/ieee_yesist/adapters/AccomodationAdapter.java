package com.ieee.ieee_yesist.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.model.AccomodationModel;
import com.ieee.ieee_yesist.model.OnAccomodationClickListener;
import com.ieee.ieee_yesist.model.OnPlaceClickListener;

import java.util.ArrayList;

public class AccomodationAdapter extends RecyclerView.Adapter<AccomodationAdapter.Viewholder> {

    private  Context context;
    private  ArrayList<AccomodationModel> accomodationModelArrayList;
    private OnAccomodationClickListener clickListener;

    public void setClickListener (OnAccomodationClickListener listener)
    {
        this.clickListener = listener;
    }

    public  AccomodationAdapter(Context context,ArrayList<AccomodationModel> accomodationModelArrayList)
    {
        this.context = context;
        this.accomodationModelArrayList =accomodationModelArrayList;
    }

    @NonNull
    @Override
    public AccomodationAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accomodation_item,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        AccomodationModel model = accomodationModelArrayList.get(holder.getAdapterPosition());
        holder.accName.setText(model.getName());
        holder.accAddress.setText(model.getAddress());
        Glide.with(context).load(model.getImage()).thumbnail(0.05f).into(holder.accImage);

    }

    @Override
    public int getItemCount() {
        return accomodationModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView accImage;
        private  TextView accName;
        private  TextView accAddress;
        private  Button goIbiboButton;

        public  Viewholder(@NonNull View itemView)
        {
            super(itemView);
            accImage = itemView.findViewById(R.id.accomadationImage);
            accName = itemView.findViewById(R.id.accomodationName);
            accAddress = itemView.findViewById(R.id.address);
            goIbiboButton = itemView.findViewById(R.id.know_more);

            goIbiboButton.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onAccomodationClick(v,getAdapterPosition(),accomodationModelArrayList.get(getAdapterPosition()));
        }


    }
}
