package com.shubamvirdi.internshala.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubamvirdi.internshala.Activities.DetailedWorkshop;
import com.shubamvirdi.internshala.ModelClasses.WorkshopModel;
import com.shubamvirdi.internshala.R;
import com.shubamvirdi.internshala.Utils.Utils;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


// ADAPTER FOR THE DASHBOARD FRAGMENT FOR ALL AVAILABLE WORKSHOPS
public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.myViewHolder>{

    // VARIABLES
    private List<WorkshopModel> mWorkshopModel;
    private Context mContext;



   //CONSTRUCTOR
    public WorkshopAdapter(List<WorkshopModel> mWorkshopModel, Context mContext) {
        this.mWorkshopModel = mWorkshopModel;
        this.mContext = mContext;
    }


    //RECYCLER VIEW METHODS
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(mContext).inflate(R.layout.workshop_card,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.setData(mWorkshopModel.get(position).getTitle(),mWorkshopModel.get(position).getSubtitle(),mWorkshopModel.get(position).getLocation(),mWorkshopModel.get(position).getTime(),mWorkshopModel.get(position));
    }

    @Override
    public int getItemCount() {
        return mWorkshopModel.size();
    }

     class myViewHolder extends RecyclerView.ViewHolder {


        // VARIABLE DECLARATION
        private View mView;
        private WorkshopModel model;
         private CircleImageView RankImage;
        private TextView title,subtitle,location,time,Rank;

        //CONSTRUCTOR OF THE VIEW HOLDER CLASS
         myViewHolder(@NonNull View itemView) {
            super(itemView);
            mView  = itemView;


            // ID'S INITIALIZATION
            title = mView.findViewById(R.id.card_title);
            subtitle = mView.findViewById(R.id.card_subtitle);
            time = mView.findViewById(R.id.time);
            location = mView.findViewById(R.id.location);
             Rank = mView.findViewById(R.id.rankValue);
             RankImage = mView.findViewById(R.id.rankImage);
            model = new WorkshopModel();

            // ONCLICK LISTENER FOR EACH WORKSHOP CARD VIEW
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, DetailedWorkshop.class);
                    i.putExtra("workshopModel",model);
                    i.putExtra("applications",false);
                    i.putExtra("rank",getAdapterPosition()+1);
                    mContext.startActivity(i);
                }
            });



        }

        // FUNCTION TO SET DATA TO WORKSHOP CARD VIEW
        private void setData(String title,String subtitle,String location,String time,WorkshopModel mObj){

            this.title.setText(title);
            this.subtitle.setText(subtitle);
            this.location.setText(location);
            this.time.setText(time);
            model = mObj;
            int val  = new Random().nextInt(Utils.Colors.length);
            GradientDrawable drawable  = (GradientDrawable) RankImage.getBackground();
            drawable.setColor(Color.parseColor(Utils.Colors[val]));
            Rank.setText("#"+(getAdapterPosition()+1));
            this.title.setTextColor(Color.parseColor(Utils.Colors[val]));
        }
    }
}
