package com.desapoint.desapoint.toolsUtilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 3/14/17.
 */

public class PreferenceStorage {

    Context context;

    private static final String USERINFO="USERINFO";
    private static final String SUBJECTINFO="USERINFO";

    public PreferenceStorage(Context context){
        this.context=context;
    }

    public static void addSubjectJson(Context context,String json){
        SharedPreferences sharedPreferences=context.getSharedPreferences("NOTIFICATIONS",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(SUBJECTINFO,json);
        editor.commit();
    }

    public String getSubjectJson(){

        SharedPreferences sharedPreferences=context.getSharedPreferences("NOTIFICATIONS",0);
        return sharedPreferences.getString(SUBJECTINFO,"none");
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
