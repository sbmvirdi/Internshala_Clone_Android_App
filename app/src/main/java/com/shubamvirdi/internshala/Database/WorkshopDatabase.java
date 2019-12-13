package com.shubamvirdi.internshala.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
    private static final String TABLE3_COLUMN_3 = "title";
    private static final String TABLE3_COLUMN_4 = "subtitle";
    private static final String TABLE3_COLUMN_5 = "time";
    private static final String TABLE3_COLUMN_6 = "date";
    private static final String TABLE3_COLUMN_7 = "location";


    // CREATE TABLE QUERIES
    private static final String TABLE1_QUERY = "CREATE TABLE "+TABLE_NAME1+"("+TABLE1_COLUMN_1+" INT PRIMARY KEY ,"+TABLE1_COLUMN_2+" TEXT,"+TABLE1_COLUMN_3+" TEXT,"+TABLE1_COLUMN_4+" TEXT,"+TABLE1_COLUMN_5+" TEXT,"+TABLE1_COLUMN_6+" TEXT)";
    private static final String TABLE2_QUERY  = "CREATE TABLE "+TABLE_NAME2+"("+TABLE2_COLUMN_1+" INT PRIMARY KEY ,"+TABLE2_COLUMN_2+" TEXT,"+TABLE2_COLUMN_3+" TEXT)";
    private static final String TABLE3_QUERY  = "CREATE TABLE "+TABLE_NAME3+"("+TABLE3_COLUMN_1+" INT PRIMARY KEY ,"+TABLE3_COLUMN_2+" TEXT,"+TABLE3_COLUMN_3+" TEXT,"+TABLE3_COLUMN_4+" TEXT,"+TABLE3_COLUMN_5+" TEXT,"+TABLE3_COLUMN_6+" TEXT,"+TABLE3_COLUMN_7+" TEXT)";






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

        for (WorkshopModel model : mInsertList) {

            ContentValues mValues = new ContentValues();
            mValues.put(TABLE1_COLUMN_2, model.getTitle());
            mValues.put(TABLE1_COLUMN_3, model.getSubtitle());
            mValues.put(TABLE1_COLUMN_4, model.getTime());
            mValues.put(TABLE1_COLUMN_5, model.getDate());
            mValues.put(TABLE1_COLUMN_6, model.getLocation());
            db.insert(TABLE_NAME1, null, mValues);

        }

//
//            ContentValues Values = new ContentValues();
//            Values.put(TABLE2_COLUMN_2,"shubhamvirdic3@gmail.com");
//            Values.put(TABLE2_COLUMN_3,"Mekingsbm@123");
//            long d = db.insert(TABLE_NAME2,null,Values);
//            Log.e("INSERTED DATA","EMAIL ID,PASSWORD");
//



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



    public long login(String email,String pass,Context mContext) {

        SQLiteDatabase db = this.getWritableDatabase();
        // String query = "select email,passkey from "+TABLE_NAME2+"where email = ? and passkey = ?";
        Cursor c = db.rawQuery("select * from " + TABLE_NAME2 + " where email = ? and passkey = ?", new String[]{email, pass});


//        Toast.makeText(mContext, ""+c, Toast.LENGTH_SHORT).show();

            boolean isuser = isUser(email);
            if (isuser) {
                SharedPreferences preferences = mContext.getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", email);
                editor.putString("password", pass);
                editor.apply();
                c.close();
                Log.e("LOGIN","LOGGED IN");
                return 1;
            } else {
                c.close();
                Log.e("LOGIN","Does not exist");
                return -3;
            }

            //Toast.makeText(mContext, ""+c.getString(c.getColumnIndex(TABLE2_COLUMN_2)), Toast.LENGTH_SHORT).show();
//                SharedPreferences preferences = mContext.getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("email", email);
//                editor.putString("password", pass);
//                editor.apply();
//                c.close();
//                return 1;
    }

    public long signup(String email,String pass,Context mContext){

        SQLiteDatabase db = this.getWritableDatabase();

        if (isUser(email)){
            return -3;
        }
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

    public boolean isUser(String email){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c  = db.rawQuery("SELECT * FROM "+TABLE_NAME2+" where email=?",new String[]{email});

        if (c.getCount()>0 && c.moveToFirst()){
            c.close();
            return true;
        }else{
            c.close();
            return false;
        }
    }

    public List<WorkshopModel> fetchUserWorkshops(String email){
        List<WorkshopModel> mList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from "+TABLE_NAME3+" where email=?",new String[]{email});

        if (c.moveToFirst()){

            do{
                WorkshopModel model = new WorkshopModel();
                model.setUid(c.getInt(c.getColumnIndex(TABLE3_COLUMN_1)));
                model.setTitle(c.getString(c.getColumnIndex(TABLE3_COLUMN_3)));
                model.setSubtitle(c.getString(c.getColumnIndex(TABLE3_COLUMN_4)));
                model.setLocation(c.getString(c.getColumnIndex(TABLE3_COLUMN_5)));
                model.setDate(c.getString(c.getColumnIndex(TABLE3_COLUMN_6)));
                model.setTime(c.getString(c.getColumnIndex(TABLE3_COLUMN_7)));
                mList.add(model);

            }while (c.moveToNext());
        }

        c.close();
        return mList;

    }


    public boolean isRegisteredWorkshop(String email,String title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =db.rawQuery("select * from "+TABLE_NAME3+" where email = ? and title = ?",new String[]{email,title});
        if (c.getCount()>0 && c.moveToFirst()){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }

    }


    public boolean registerWorkshop(String email,WorkshopModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE3_COLUMN_2,email);
        values.put(TABLE3_COLUMN_3,model.getTitle());
        values.put(TABLE3_COLUMN_4,model.getSubtitle());
        values.put(TABLE3_COLUMN_5,model.getTime());
        values.put(TABLE3_COLUMN_6,model.getDate());
        values.put(TABLE3_COLUMN_7,model.getLocation());

        long insert = db.insert(TABLE_NAME3,null,values);
        if(insert!=-1){
            return true;
        }
        else {
            return false;
        }




    }

}
