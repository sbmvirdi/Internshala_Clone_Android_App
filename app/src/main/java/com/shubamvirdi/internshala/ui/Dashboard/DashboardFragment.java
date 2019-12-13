package com.shubamvirdi.internshala.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.shubamvirdi.internshala.Adapters.WorkshopAdapter;
import com.shubamvirdi.internshala.Database.WorkshopDatabase;
import com.shubamvirdi.internshala.ModelClasses.WorkshopModel;
import com.shubamvirdi.internshala.R;
import com.shubamvirdi.internshala.Utils.Utils;
import com.shubamvirdi.internshala.ui.Login.Login;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {


    private RecyclerView mWorkshopRecyclerView;
    private List<WorkshopModel> mList;
    private WorkshopDatabase mDatabase;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


//
//        SharedPreferences preferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME,Context.MODE_PRIVATE);
//        String tologin = preferences.getString("login","2");
//        if (tologin.equals("1")){
//
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.nav_host_fragment, new Login());
//                ft.commit();
//                Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//                toolbar.setTitle("Login/Signup");
//        }

//        if (bundle.getString("login")!=null && bundle.getString("login").equals("1")) {
//            String login = bundle.getString("login");
//            Toast.makeText(getContext(), ""+login, Toast.LENGTH_SHORT).show();
//            if (login!=null && login.equals("1")){
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.nav_host_fragment, new Login());
//                ft.commit();
//                Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//                toolbar.setTitle("Login/Signup");
//                bundle.putString("login","0");
//            }
//
//        }

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


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences  = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = preferences.getString("email","null");
        if (email.equals("null")){
            Toast.makeText(getContext(), "not logged in", Toast.LENGTH_SHORT).show();
            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_app).setVisible(false);
            navigationView.getMenu().getItem(0).setChecked(true);
        }
        else {
            Toast.makeText(getContext(), "logged in", Toast.LENGTH_SHORT).show();
            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_app).setVisible(true);
            navigationView.getMenu().getItem(0).setChecked(true);

        }

    }
}