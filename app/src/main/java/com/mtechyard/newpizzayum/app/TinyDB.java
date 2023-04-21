package com.mtechyard.newpizzayum.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class TinyDB extends Application {

    //Store Data
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static final String DATABASE_NAME = "DB1";
    SharedPreferences DATABASE;
    int DEFAULT_MOD = Context.MODE_PRIVATE;
    SharedPreferences.Editor DATABASE_EDIT;


    public TinyDB(Context context) {
       TinyDB.context =context;
       DATABASE = context.getSharedPreferences(DATABASE_NAME,DEFAULT_MOD);
       DATABASE_EDIT = DATABASE.edit();
    }

    public TinyDB(Context context,int dbMode){
        TinyDB.context =context;
        DATABASE = context.getSharedPreferences(DATABASE_NAME,dbMode);
        DATABASE_EDIT = DATABASE.edit();
    }
    public TinyDB(Context context,String dbName){
        TinyDB.context =context;
        DATABASE = context.getSharedPreferences(dbName,DEFAULT_MOD);
        DATABASE_EDIT = DATABASE.edit();
    }
    public TinyDB(Context context,int dbMode,String dbName){
        TinyDB.context =context;
        DATABASE = context.getSharedPreferences(dbName,dbMode);
        DATABASE_EDIT = DATABASE.edit();
    }


    public void save(String Tag, String Value) {
        DATABASE_EDIT.putString(Tag, Value);
        DATABASE_EDIT.apply();
    }
    public void save(String Tag, int Value) {
        DATABASE_EDIT.putInt(Tag, Value);
        DATABASE_EDIT.apply();
    }
    public void save(String Tag, float Value) {
        DATABASE_EDIT.putFloat(Tag, Value);
        DATABASE_EDIT.apply();
    }
    public void save(String Tag, Boolean Value) {
        DATABASE_EDIT.putBoolean(Tag, Value);
        DATABASE_EDIT.apply();
    }
    public void save(String Tag, long Value) {
        DATABASE_EDIT.putLong(Tag, Value);
        DATABASE_EDIT.apply();
    }

    public String get(String Tag, String DefValue) {
       return  DATABASE.getString(Tag, DefValue);
    }
    public int get(String Tag, int DefValue) {
        return DATABASE.getInt(Tag, DefValue);
    }
    public float get(String Tag, float DefValue) {
        return DATABASE.getFloat(Tag, DefValue);
    }
    public Boolean get(String Tag, Boolean DefValue) {
        return DATABASE.getBoolean(Tag, DefValue);
    }
    public long get(String Tag, long DefValue) {
        return DATABASE.getLong(Tag, DefValue);
    }

    //Get Data
    public static int GetInt(String Tag, int DefValue) {

        SharedPreferences Store1 = context.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getInt(Tag, DefValue);
    }

    public static String GetString(String Tag, String DefValue) {

        SharedPreferences Store1 = context.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getString(Tag, DefValue);
    }

    public static float GetFloat(String Tag, float DefValue) {

        SharedPreferences Store1 = context.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getFloat(Tag, DefValue);
    }

    public boolean GetBoolean(String Tag, boolean DefValue) {

        SharedPreferences Store1 = context.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getBoolean(Tag, DefValue);
    }

    public static long GetLong(String Tag, long DefValue) {

        SharedPreferences Store1 = context.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getLong(Tag, DefValue);
    }

    //Clare Data
    public void ClearDB1() {
        SharedPreferences Store1 = context.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        SharedPreferences.Editor Store = Store1.edit();
        Store.clear();
        Store.apply();
    }



}





