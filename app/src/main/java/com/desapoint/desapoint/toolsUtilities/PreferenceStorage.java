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


    public PreferenceStorage(Context context){
        this.context=context;
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
