package com.shubamvirdi.internshala.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubamvirdi.internshala.ModelClasses.WorkshopModel;
import com.shubamvirdi.internshala.R;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder> {

    private List<WorkshopModel> mWorkshopModel;
    private Context mContext;

    public ApplicationAdapter(List<WorkshopModel> mWorkshopModel, Context mContext) {
        this.mWorkshopModel = mWorkshopModel;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.workshop_card,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(mWorkshopModel.get(position).getTitle(),mWorkshopModel.get(position).getSubtitle(),mWorkshopModel.get(position).getLocation(),mWorkshopModel.get(position).getTime(),mWorkshopModel.get(position));
    }

    @Override
    public int getItemCount() {
        return mWorkshopModel.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private WorkshopModel model;
        private TextView title,subtitle,location,time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mView  = itemView;

            title = mView.findViewById(R.id.card_title);
            subtitle = mView.findViewById(R.id.card_subtitle);
            time = mView.findViewById(R.id.time);
            location = mView.findViewById(R.id.location);

        }

        private void setData(String title,String subtitle,String location,String time,WorkshopModel mObj){

            this.title.setText(title);
            this.subtitle.setText(subtitle);
            this.location.setText(location);
            this.time.setText(time);
            model = mObj;
        }
    }
}
