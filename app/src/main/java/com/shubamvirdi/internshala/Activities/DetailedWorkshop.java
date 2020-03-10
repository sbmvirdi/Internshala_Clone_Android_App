package com.shubamvirdi.internshala.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailedWorkshop extends AppCompatActivity {
    // DECLARATION OF VARIABLES
    private TextView mTitle,mSubtitle,mDate,mTime,mLocation,mRegisteredText;
    private Button mRegisterWorkshop;
    private WorkshopModel mWorshopModel;
    private WorkshopDatabase mDatabase;
    private boolean loggedin = false;
    private boolean application;
    private CircleImageView mRankImage;
    private TextView mRank;
    private String email;
    private int Rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_workshop);


        // DECLARATION OF ID'S
        mTitle = findViewById(R.id.dw_card_title);
        mSubtitle = findViewById(R.id.dw_card_subtitle);
        mDate = findViewById(R.id.dw_startdate);
        mTime = findViewById(R.id.dw_time);
        mLocation = findViewById(R.id.dw_location);
        mRegisterWorkshop = findViewById(R.id.registerWorkshop);
        mRegisteredText = findViewById(R.id.registeredText);
        mRankImage = findViewById(R.id.rankImage);
        mRank= findViewById(R.id.rankValue);

        // INITIALIZING THE OBJECT AND FETCHING THE VALUE USING SERIALIZABLE
        mWorshopModel = new WorkshopModel();
        mDatabase = new WorkshopDatabase(getApplicationContext());
        mWorshopModel = (WorkshopModel) getIntent().getSerializableExtra("workshopModel");
        application = getIntent().getBooleanExtra("applications",false);
        Rank = getIntent().getIntExtra("rank",1);

        // SETTING DATA TO MODEL CLASS USING WORKSHOPMODEL OBJECT
        mTitle.setText(mWorshopModel.getTitle());
        mSubtitle.setText(mWorshopModel.getSubtitle());
        mDate.setText(mWorshopModel.getDate());
        mTime.setText(mWorshopModel.getTime());
        mLocation.setText(mWorshopModel.getLocation());


        // USING SHARED PREFERENCES TO DETERMINE WHETHER USER IS LOGGED IN OR NOT
        SharedPreferences preferences  = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
         email = preferences.getString("email","null");
        if (email.equals("null")){
            // USER IS NOT LOGGED IN
            loggedin = false;
        }
        else {
            // USER IS LOGGED IN
            loggedin = true;
        }

        // FETCHING WHETHER THE USER HAS ALREADY REGISTERED IN THE WORKSHOP
        boolean registered = mDatabase.isRegisteredWorkshop(email,mWorshopModel.getTitle());

        // CHANGING THE BEHAVIOUR OF THE LAYOUT BASED ON LOGIN OR NOT
        if (registered){
            mRegisterWorkshop.setText("Already Registered");
            mRegisterWorkshop.setVisibility(View.GONE);
            mRegisteredText.setVisibility(View.VISIBLE);
        }

        if (application){
            mRegisteredText.setVisibility(View.GONE);
        }

        // ON CLICK LISTENER TO THE REGISTER WORKSHOP BUTTON
        mRegisterWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IF USER IS LOGGED IN
                if (loggedin){
                    // REGISTERING THE WORKSHOP IN USER ACCOUNT
                    boolean register = mDatabase.registerWorkshop(email,mWorshopModel);
                    if (register){
                        //IF REGISTRATION IS SUCCESSFULL
                        Toast.makeText(DetailedWorkshop.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(DetailedWorkshop.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        // IF REGISTERATION FAILS
                        Toast.makeText(DetailedWorkshop.this, "failed to register", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    // IF USER IS NOT LOGGED IN AND TRY TO REGISTER TO THE WORKSHOP
                    Intent i = new Intent(DetailedWorkshop.this,MainActivity.class);
                    SharedPreferences preferences  = getSharedPreferences(Utils.SHARED_PREF_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("login","1");
                    editor.apply();
                    startActivity(i);
                    finish();


                }

            }
        });


        //SETTING UP RANK AND RANK IMAGE

        int val =new Random().nextInt(Utils.Colors.length);
        GradientDrawable drawable = (GradientDrawable) mRankImage.getBackground();
        drawable.setColor(Color.parseColor(Utils.Colors[val]));
        mRank.setText("#"+Rank);
        mTitle.setTextColor(Color.parseColor(Utils.Colors[val]));


    }
}
