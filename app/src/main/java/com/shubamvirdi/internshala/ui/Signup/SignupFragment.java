package com.shubamvirdi.internshala.ui.Signup;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.shubamvirdi.internshala.Database.WorkshopDatabase;
import com.shubamvirdi.internshala.R;
import com.shubamvirdi.internshala.ui.Dashboard.DashboardFragment;
import com.shubamvirdi.internshala.ui.Login.Login;

import org.w3c.dom.Text;


public class SignupFragment extends Fragment {

    private TextView mLogin;
    private EditText mEmail,mPass;
    private Button mSignup;
    private WorkshopDatabase mDatabase;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_signup, container, false);

        mLogin = root.findViewById(R.id.tologin);
        mSignup = root.findViewById(R.id.signinbutton);
        mEmail = root.findViewById(R.id.email_signup);
        mPass = root.findViewById(R.id.pass_signup);
        mDatabase = new WorkshopDatabase(getContext());


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new Login());
                ft.commit();
            }
        });


        mSignup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)){
                    Toast.makeText(getContext(), "Enter all details", Toast.LENGTH_SHORT).show();
                }
                long signup = mDatabase.signup(email,pass,getContext());
                if (signup == -3){
                    Toast.makeText(getContext(), "Already registered kindly login ", Toast.LENGTH_SHORT).show();
                }else if (signup == -1){
                    Toast.makeText(getContext(), "Error while signing up", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Successfully signed up", Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, new DashboardFragment());
                    ft.commit();
                    NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                    navigationView.getMenu().getItem(0).setChecked(true);
                    Toolbar toolbar  = getActivity().findViewById(R.id.toolbar);
                    toolbar.setTitle("Dashboard");
                }

            }
        });


        return root;
    }

}
