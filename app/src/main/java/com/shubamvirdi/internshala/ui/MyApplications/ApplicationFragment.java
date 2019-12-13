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

    //VARIABLE DECLARATION
    private RecyclerView mApplicationRecyclerView;
    private WorkshopDatabase mDatabase;
    private TextView mEmpty;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_applications, container, false);

        //SETTING UP ID'S AND SETTING UP RECYCLER VIEW
        mApplicationRecyclerView = root.findViewById(R.id.applicationRecycler);
        mDatabase = new WorkshopDatabase(getContext());
        mEmpty = root.findViewById(R.id.emptyText);

        mApplicationRecyclerView.setHasFixedSize(true);
        mApplicationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<WorkshopModel> mList;

        // ACCESSING SHARED PREFERENCES TO CHECK WHETHER WHETHER USER IS LOGGED IN
        SharedPreferences preferences  = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = preferences.getString("email","null");
        if (email.equals("null")){

            // USER NOT LOGGED IN
            mApplicationRecyclerView.setVisibility(View.GONE);
        }
        else {
            mList = new ArrayList<>();
            // USER LOGGED IN
            // FETCH ALL THE USER REGISTERED WORKSHOPS FROM THE DATABASE
            mList = mDatabase.fetchUserWorkshops(email);
            if (mList.size() == 0){
                // IF NO WORKSHOP IS REGISTERED
                mEmpty.setVisibility(View.VISIBLE);
                mApplicationRecyclerView.setVisibility(View.GONE);
            }
            else {
                // IF SOME WORKSHOPS ARE REGISTERED
                ApplicationAdapter applicationAdapter = new ApplicationAdapter(mList,getContext());
                mApplicationRecyclerView.setAdapter(applicationAdapter);
            }
        }




        return root;
    }
}