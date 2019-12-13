package com.shubamvirdi.internshala.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.shubamvirdi.internshala.Database.WorkshopDatabase;
import com.shubamvirdi.internshala.ModelClasses.WorkshopModel;
import com.shubamvirdi.internshala.R;
import com.shubamvirdi.internshala.Utils.Utils;
import com.shubamvirdi.internshala.ui.Dashboard.DashboardFragment;
import com.shubamvirdi.internshala.ui.Login.Login;

public class DetailedWorkshop extends AppCompatActivity {
    private TextView mTitle,mSubtitle,mDate,mTime,mLocation;
    private Button mRegisterWorkshop;
    private WorkshopModel mWorshopModel;
    private WorkshopDatabase mDatabase;
    private boolean loggedin = false;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_workshop);

        mWorshopModel = new WorkshopModel();
        mTitle = findViewById(R.id.dw_card_title);
        mSubtitle = findViewById(R.id.dw_card_subtitle);
        mDate = findViewById(R.id.dw_startdate);
        mTime = findViewById(R.id.dw_time);
        mLocation = findViewById(R.id.dw_location);
        mRegisterWorkshop = findViewById(R.id.registerWorkshop);


        mDatabase = new WorkshopDatabase(getApplicationContext());
        mWorshopModel = (WorkshopModel) getIntent().getSerializableExtra("workshopModel");

        mTitle.setText(mWorshopModel.getTitle());
        mSubtitle.setText(mWorshopModel.getSubtitle());
        mDate.setText(mWorshopModel.getDate());
        mTime.setText(mWorshopModel.getTime());
        mLocation.setText(mWorshopModel.getLocation());


        SharedPreferences preferences  = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
         email = preferences.getString("email","null");
        if (email.equals("null")){
            Toast.makeText(getApplicationContext(), "not logged in", Toast.LENGTH_SHORT).show();
            loggedin = false;
        }
        else {
            Toast.makeText(getApplicationContext(), "logged in", Toast.LENGTH_SHORT).show();
            loggedin = true;
        }

        boolean registered = mDatabase.isRegisteredWorkshop(email,mWorshopModel.getTitle());

        if (registered){
            mRegisterWorkshop.setText("Already Registered");
            mRegisterWorkshop.setVisibility(View.GONE);
        }

        mRegisterWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedin){
                    boolean register = mDatabase.registerWorkshop(email,mWorshopModel);
                    if (register){
                        Intent i = new Intent(DetailedWorkshop.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(DetailedWorkshop.this, "failed to register", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Intent i = new Intent(DetailedWorkshop.this,MainActivity.class);
                    i.putExtra("login","1");
                    startActivity(i);
                    finish();

                }

            }
        });




    }
}
