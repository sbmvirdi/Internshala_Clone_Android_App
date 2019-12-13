package com.shubamvirdi.internshala.ui.MyApplications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.shubamvirdi.internshala.Adapters.ApplicationAdapter;
import com.shubamvirdi.internshala.Database.WorkshopDatabase;
import com.shubamvirdi.internshala.ModelClasses.WorkshopModel;
import com.shubamvirdi.internshala.R;
import com.shubamvirdi.internshala.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ApplicationFragment extends Fragment {
    private RecyclerView mApplicationRecyclerView;
    private WorkshopDatabase mDatabase;
    private TextView mEmpty;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_applications, container, false);

        mApplicationRecyclerView = root.findViewById(R.id.applicationRecycler);
        mDatabase = new WorkshopDatabase(getContext());
        mEmpty = root.findViewById(R.id.emptyText);

        mApplicationRecyclerView.setHasFixedSize(true);
        mApplicationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<WorkshopModel> mList;


        SharedPreferences preferences  = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = preferences.getString("email","null");
        if (email.equals("null")){
            Toast.makeText(getContext(), "not logged in", Toast.LENGTH_SHORT).show();
            mApplicationRecyclerView.setVisibility(View.GONE);
        }
        else {
            mList = new ArrayList<>();
            Toast.makeText(getContext(), "logged in", Toast.LENGTH_SHORT).show();
            mList = mDatabase.fetchUserWorkshops(email);
            if (mList.size() == 0){
                mEmpty.setVisibility(View.VISIBLE);
                mApplicationRecyclerView.setVisibility(View.GONE);
            }
            else {

                ApplicationAdapter applicationAdapter = new ApplicationAdapter(mList,getContext());
                mApplicationRecyclerView.setAdapter(applicationAdapter);
            }
        }




        return root;
    }
}