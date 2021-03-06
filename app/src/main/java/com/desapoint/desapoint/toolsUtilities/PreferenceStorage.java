package com.desapoint.desapoint.toolsUtilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 3/14/17.
 */

public class PreferenceStorage {

    Context context;

    private static final String USERINFO = "USERINFO";
    private static final String WINDOWINFO = "WINDOWINFO";
    private static final String REGINFO = "REGINFO=";
    private static final String SUBJECT_STORE = "SUBJECT_STORE";
    private static final String CATEGORY_STORE = "CATEGORY_STORE";
    private static final String STATUS = "STATUS";
    public static final String STATUS_LOGOUT = "STATUS_LOGOUT";


    public PreferenceStorage(Context context) {
        this.context = context;
    }

    public static void addStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STATUS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STATUS,STATUS_LOGOUT);
        editor.commit();
    }

    public static String getStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STATUS, 0);
        return sharedPreferences.getString(STATUS, "none");
    }

    public static void addRegInfo(Context context, String json) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REGINFO, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REGINFO, json);
        editor.commit();
    }

    public static String getRegInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REGINFO, 0);
        return sharedPreferences.getString(REGINFO, "none");
    }

    public static void addWindowInfo(Context context, String json) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WINDOWINFO, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WINDOWINFO, json);
        editor.commit();
    }

    public static String getWindowInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WINDOWINFO, 0);
        return sharedPreferences.getString(WINDOWINFO, "none");
    }

    public static void addSubjectJson(Context context, String json) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceStorage.SUBJECT_STORE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PreferenceStorage.SUBJECT_STORE, json);
        editor.commit();
    }

    public static void addCategoriesJson(Context context, String json) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceStorage.CATEGORY_STORE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PreferenceStorage.CATEGORY_STORE, json);
        editor.commit();
    }

    public static String getCategoriesJson(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceStorage.CATEGORY_STORE, 0);
        return sharedPreferences.getString(CATEGORY_STORE, "none");
    }

    public static String getSubjectJson(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceStorage.SUBJECT_STORE, 0);
        return sharedPreferences.getString(SUBJECT_STORE, "none");
    }

    public static void addUserJson(Context context, String json) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USERINFO, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERINFO, json);
        editor.commit();
    }

    public static String getUserJson(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(USERINFO, 0);
        return sharedPreferences.getString(USERINFO, "none");
    }

    public static void clearInformation(Context context) {

        //status clear
        SharedPreferences sharedPreferenceStatus = context.getSharedPreferences(STATUS, 0);
        SharedPreferences.Editor editorStatus = sharedPreferenceStatus.edit();
        editorStatus.clear();
        editorStatus.commit();

        SharedPreferences sharedPreferences = context.getSharedPreferences(USERINFO, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        //clear subject list//

        SharedPreferences sharedPreferencesSubjects = context.getSharedPreferences(SUBJECT_STORE, 0);
        SharedPreferences.Editor editorSubject = sharedPreferencesSubjects.edit();
        editorSubject.clear();
        editorSubject.commit();


        //clear categories list//

        SharedPreferences sharedPreferencesCat = context.getSharedPreferences(CATEGORY_STORE, 0);
        SharedPreferences.Editor editorCat = sharedPreferencesCat.edit();
        editorCat.clear();
        editorCat.commit();

    }
}
