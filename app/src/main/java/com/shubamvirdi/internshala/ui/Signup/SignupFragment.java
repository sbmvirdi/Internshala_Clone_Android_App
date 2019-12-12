package com.shubamvirdi.internshala.ui.Signup;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shubamvirdi.internshala.R;
import com.shubamvirdi.internshala.ui.Login.Login;


public class SignupFragment extends Fragment {

    private TextView login;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_signup, container, false);

        login = root.findViewById(R.id.tologin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new Login());
                ft.commit();
            }
        });
        return root;
    }

}
