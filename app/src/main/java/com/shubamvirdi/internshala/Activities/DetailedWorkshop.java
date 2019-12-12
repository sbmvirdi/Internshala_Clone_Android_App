package com.shubamvirdi.internshala.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shubamvirdi.internshala.ModelClasses.WorkshopModel;
import com.shubamvirdi.internshala.R;

public class DetailedWorkshop extends AppCompatActivity {
    private TextView mTitle,mSubtitle,mDate,mTime,mLocation;
    private Button mRegisterWorkshop;
    private WorkshopModel mWorshopModel;

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



        mWorshopModel = (WorkshopModel) getIntent().getSerializableExtra("workshopModel");

        mTitle.setText(mWorshopModel.getTitle());
        mSubtitle.setText(mWorshopModel.getSubtitle());
        mDate.setText(mWorshopModel.getDate());
        mTime.setText(mWorshopModel.getTime());
        mLocation.setText(mWorshopModel.getTime());

        mRegisterWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
