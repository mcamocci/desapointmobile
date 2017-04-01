package com.desapoint.desapoint.toolsUtilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 3/14/17.
 */

public class PreferenceStorage {

    Context context;

    private static final String USERINFO="USERINFO";
    private static final String WINDOWINFO="WINDOWINFO";
    private static final String REGINFO="REGINFO=";
    private  static final String SUBJECT_STORE="SUBJECT_STORE";
    private  static final String CATEGORY_STORE="CATEGORY_STORE";


    public PreferenceStorage(Context context){
        this.context=context;
    }

    public static void addRegInfo(Context context,String json){
        SharedPreferences sharedPreferences=context.getSharedPreferences(REGINFO,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(REGINFO,json);
        editor.commit();
    }

    public static String getRegInfo(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(REGINFO,0);
        return sharedPreferences.getString(REGINFO,"none");
    }

    public static void addWindowInfo(Context context,String json){
        SharedPreferences sharedPreferences=context.getSharedPreferences(WINDOWINFO,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(WINDOWINFO,json);
        editor.commit();
    }

    public static String getWindowInfo(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(WINDOWINFO,0);
        return sharedPreferences.getString(WINDOWINFO,"none");
    }

    public static void addSubjectJson(Context context,String json){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PreferenceStorage.SUBJECT_STORE,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(PreferenceStorage.SUBJECT_STORE,json);
        editor.commit();
    }

    public static String getSubjectJson(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PreferenceStorage.SUBJECT_STORE,0);
        return sharedPreferences.getString(SUBJECT_STORE,"none");
    }

    public static void addUserJson(Context context,String json){
        SharedPreferences sharedPreferences=context.getSharedPreferences("NOTIFICATIONS",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USERINFO,json);
        editor.commit();
    }

    public static String getUserJson(Context context){

        SharedPreferences sharedPreferences=context.getSharedPreferences("NOTIFICATIONS",0);
        return sharedPreferences.getString(USERINFO,"none");
    }

    public static void clearInformation(Context context){

        SharedPreferences sharedPreferences=context.getSharedPreferences("NOTIFICATIONS",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }
}
