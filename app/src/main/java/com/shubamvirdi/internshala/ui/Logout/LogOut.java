package com.shubamvirdi.internshala.ui.Logout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.shubamvirdi.internshala.R;
import com.shubamvirdi.internshala.Utils.Utils;
import com.shubamvirdi.internshala.ui.Dashboard.DashboardFragment;
import com.shubamvirdi.internshala.ui.Signup.SignupFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogOut extends Fragment {


    public LogOut() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_log_out, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email","null");
        editor.putString("password","null");
        editor.putString("login","0");
        editor.apply();
        Toast.makeText(getContext(), "Logged out and cleared SharedPreferences", Toast.LENGTH_SHORT).show();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, new DashboardFragment());
        ft.commit();
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");


        return root;
    }

}
