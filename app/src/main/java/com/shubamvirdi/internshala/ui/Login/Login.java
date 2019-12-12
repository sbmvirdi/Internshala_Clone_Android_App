package com.shubamvirdi.internshala.ui.Login;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.shubamvirdi.internshala.Utils.Utils;
import com.shubamvirdi.internshala.ui.Signup.SignupFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    private TextView signup;
    private Button mLogin;
    private EditText mEmail,mPass;
    private WorkshopDatabase mDatabase;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_login, container, false);


        signup = root.findViewById(R.id.tosignup);
        mLogin = root.findViewById(R.id.loginbutton);
        mEmail = root.findViewById(R.id.email);
        mPass = root.findViewById(R.id.pass);
        mDatabase = new WorkshopDatabase(getContext());

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new SignupFragment());
                ft.commit();
            }

        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String pass = mPass.getText().toString().trim();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(getContext(), "Enter all details", Toast.LENGTH_SHORT).show();

                }else{

                    boolean loggedin =  mDatabase.login(email,pass,getContext());
                    if(loggedin){
                        Toast.makeText(getContext(), "Logged in", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "failed to login!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return root;

    }

}
