package com.shubamvirdi.internshala.ui.Dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shubamvirdi.internshala.Adapters.WorkshopAdapter;
import com.shubamvirdi.internshala.Database.WorkshopDatabase;
import com.shubamvirdi.internshala.ModelClasses.WorkshopModel;
import com.shubamvirdi.internshala.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {


    private RecyclerView mWorkshopRecyclerView;
    private List<WorkshopModel> mList;
    private WorkshopDatabase mDatabase;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mWorkshopRecyclerView = root.findViewById(R.id.workshoprecycler);
        mWorkshopRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWorkshopRecyclerView.setHasFixedSize(true);


        mList = new ArrayList<>();
        mDatabase = new WorkshopDatabase(getContext());

        // Fetching all the workshops available from the database
        mList = mDatabase.fetchAllWorkshops();


        //setting the adapter for the workshop recycler view
        WorkshopAdapter adapter = new WorkshopAdapter(mList,getContext());
        mWorkshopRecyclerView.setAdapter(adapter);

        return root;
    }
}