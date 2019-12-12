package com.shubamvirdi.internshala.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.shubamvirdi.internshala.ModelClasses.UsersModel;
import com.shubamvirdi.internshala.ModelClasses.WorkshopModel;
import com.shubamvirdi.internshala.Utils.Utils;


import java.util.ArrayList;
import java.util.List;

public class WorkshopDatabase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME="INTERNSHALA_DB";
    private static final String TABLE_NAME1 = "workshops";
    private static final String TABLE_NAME2 = "users";
    private static final String TABLE_NAME3 = "user_workshops";
    private static final String TABLE1_COLUMN_1 = "uid";
    private static final String TABLE1_COLUMN_2 = "title";
    private static final String TABLE1_COLUMN_3 = "subtitle";
    private static final String TABLE1_COLUMN_4 = "time";
    private static final String TABLE1_COLUMN_5 = "date";
    private static final String TABLE1_COLUMN_6 = "location";
    private static final String TABLE2_COLUMN_1 = "uid";
    private static final String TABLE2_COLUMN_2 = "email";
    private static final String TABLE2_COLUMN_3 = "passkey";
    private static final String TABLE3_COLUMN_1 = "uid";
    private static final String TABLE3_COLUMN_2 = "email";
    private static final String TABLE3_COLUMN_3 = "workshop_name";


    // CREATE TABLE QUERIES
    private static final String TABLE1_QUERY = "CREATE TABLE "+TABLE_NAME1+"("+TABLE1_COLUMN_1+" INT PRIMARY KEY ,"+TABLE1_COLUMN_2+" TEXT,"+TABLE1_COLUMN_3+" TEXT,"+TABLE1_COLUMN_4+" TEXT,"+TABLE1_COLUMN_5+" TEXT,"+TABLE1_COLUMN_6+" TEXT)";
    private static final String TABLE2_QUERY  = "CREATE TABLE "+TABLE_NAME2+"("+TABLE2_COLUMN_1+" INT PRIMARY KEY ,"+TABLE2_COLUMN_2+" TEXT,"+TABLE2_COLUMN_3+" TEXT)";
    private static final String TABLE3_QUERY  = "CREATE TABLE "+TABLE_NAME3+"("+TABLE3_COLUMN_1+" INT PRIMARY KEY ,"+TABLE3_COLUMN_2+" TEXT,"+TABLE3_COLUMN_3+" TEXT)";






    public WorkshopDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE1_QUERY);
        db.execSQL(TABLE2_QUERY);
        db.execSQL(TABLE3_QUERY);

        List<WorkshopModel> mInsertList = new ArrayList<>();

        mInsertList.add(new WorkshopModel("Sector 55C, Gurugram","9:00 PM","PC Unleashed","Learn the basics of pc parts",1,"25th Dec 2019"));
        mInsertList.add(new WorkshopModel("Live Stream on Zoom","10:00 PM","Android App Development","Learn the basics of Android App Development",2,"26th Dec 2019"));
        mInsertList.add(new WorkshopModel("Sector 34A, Chandigarh","11:00 PM","Web Development","Let's Deep Dive into the world of web development",3,"27th Dec 2019"));
        mInsertList.add(new WorkshopModel("Online","12:00 PM","Data Science","Learn the basics of Data Science",4,"28th Dec 2019"));
        mInsertList.add(new WorkshopModel("Lovely Professional University, Jalandhar","2:00 PM","BlockChain technology","Learn the basics of BlockChain",5,"29th Dec 2019"));

        for (WorkshopModel model : mInsertList){

            ContentValues mValues = new ContentValues();
            mValues.put(TABLE1_COLUMN_2,model.getTitle());
            mValues.put(TABLE1_COLUMN_3,model.getSubtitle());
            mValues.put(TABLE1_COLUMN_4,model.getTime());
            mValues.put(TABLE1_COLUMN_5,model.getDate());
            mValues.put(TABLE1_COLUMN_6,model.getLocation());
            db.insert(TABLE_NAME1,null,mValues);
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);

        onCreate(db);
    }

    public List<WorkshopModel> fetchAllWorkshops(){
        List<WorkshopModel> mList = new ArrayList<>();
        String mSelectQuery = "SELECT * FROM "+TABLE_NAME1;
        Log.e(WorkshopDatabase.class.getSimpleName(),mSelectQuery);
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor c = db.rawQuery(mSelectQuery,null);
        if (c.moveToFirst()){

            do{
                WorkshopModel model = new WorkshopModel();
                model.setUid(c.getInt(c.getColumnIndex(TABLE1_COLUMN_1)));
                model.setTitle(c.getString(c.getColumnIndex(TABLE1_COLUMN_2)));
                model.setSubtitle(c.getString(c.getColumnIndex(TABLE1_COLUMN_3)));
                model.setLocation(c.getString(c.getColumnIndex(TABLE1_COLUMN_6)));
                model.setDate(c.getString(c.getColumnIndex(TABLE1_COLUMN_5)));
                model.setTime(c.getString(c.getColumnIndex(TABLE1_COLUMN_4)));

                mList.add(model);

            }while (c.moveToNext());
        }

        c.close();

        return mList;

    }

    public void CloseDB(){

        SQLiteDatabase db  = this.getReadableDatabase();
        if (db!=null && db.isOpen()){
            db.close();
        }
    }



    public boolean login(String email,String pass,Context mContext){

        SQLiteDatabase db  = this.getWritableDatabase();
       // String query = "select email,passkey from "+TABLE_NAME2+"where email = ? and passkey = ?";
        Cursor c = db.rawQuery("select email,passkey from "+TABLE_NAME2+" where email = ? and passkey = ?",new String[]{email,pass});

        Toast.makeText(mContext, ""+c, Toast.LENGTH_SHORT).show();
        if (c.getCount()>0){

            UsersModel usersModel = new UsersModel();
            usersModel.setUid(c.getInt(c.getColumnIndex(TABLE2_COLUMN_1)));
            usersModel.setEmail(c.getString(c.getColumnIndex(TABLE2_COLUMN_2)));
            usersModel.setPass(c.getString(c.getColumnIndex(TABLE2_COLUMN_3)));
            SharedPreferences preferences = mContext.getSharedPreferences(Utils.SHARED_PREF_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email",email);
            editor.putString("password",pass);
            editor.apply();
            c.close();
            return true;
        }else {
            c.close();
            return false;

        }

    }

    public long signup(String email,String pass,Context mContext){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mValues = new ContentValues();
        mValues.put(TABLE2_COLUMN_2,email);
        mValues.put(TABLE2_COLUMN_3,pass);
        long status = db.insert(TABLE_NAME2,null,mValues);
        if (status!=-1){
            SharedPreferences preferences = mContext.getSharedPreferences(Utils.SHARED_PREF_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email",email);
            editor.putString("password",pass);
            editor.apply();
        }
        return status;

    }

}
