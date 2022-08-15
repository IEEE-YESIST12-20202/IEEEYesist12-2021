package com.ieee.ieee_yesist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ieee.ieee_yesist.R;

import com.ieee.ieee_yesist.model.NotificationModel;

import java.util.ArrayList;

public class NotificationHistoryAdapter extends RecyclerView.Adapter<NotificationHistoryAdapter.Viewholder> {


    private  Context context;
    private ArrayList<NotificationModel> notificationModelArrayList;



    public  NotificationHistoryAdapter(Context context,ArrayList<NotificationModel> notificationModelArrayList)
    {
        this.context = context;
        this.notificationModelArrayList =notificationModelArrayList;
    }



    @NonNull
    @Override
    public NotificationHistoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);

        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        NotificationModel model = notificationModelArrayList.get(holder.getAdapterPosition());
        holder.notificationBody.setText(model.getBody());
        holder.notificationTitle.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return  notificationModelArrayList.size();
//
//        if(notificationModelArrayList.size()<=10)
//        {
//            return  notificationModelArrayList.size();
//        }
//        return  10;
    }


    public class Viewholder extends RecyclerView.ViewHolder{

        private TextView notificationBody;
        private TextView notificationTitle;

        public  Viewholder(@NonNull View itemView)
        {
            super(itemView);

            notificationBody = itemView.findViewById(R.id.notificationText);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
        }




    }
}
