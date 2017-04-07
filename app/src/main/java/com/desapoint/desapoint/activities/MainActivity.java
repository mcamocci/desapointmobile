package com.desapoint.desapoint.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.fragments.*;
import com.desapoint.desapoint.fragments.Articles;
import com.desapoint.desapoint.fragments.Books;
import com.desapoint.desapoint.fragments.Notes;
import com.desapoint.desapoint.pojos.Subject;
import com.desapoint.desapoint.pojos.User;
import com.desapoint.desapoint.pojos.WindowInfo;
import com.desapoint.desapoint.toolsUtilities.ConnectionChecker;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;


import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class MainActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    private Subjects subjectFragment=new Subjects();
    private Notes notesFragment=new Notes();
    private PastPaperFragment pastPaperFragment=new PastPaperFragment();
    private Articles articleFragment=new Articles();
    private Books bookFragment=new Books();
    private ProgressDialog progress;
    private List<String> docPaths = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        PreferenceStorage.addWindowInfo(getBaseContext(),WindowInfo.SUBJECT);

        if (!(ConnectionChecker.isInternetConnected(getBaseContext()))) {
            Intent intent=new Intent(getBaseContext(),ConnectionProblem.class);
            startActivity(intent);
        }


        actionBarTitle("Desapoint");

        // The request code used in ActivityCompat.requestPermissions()
        // and returned in the Activity's onRequestPermissionsResult()
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.MEDIA_CONTENT_CONTROL
                , Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomBar=BottomBar.attach(this,savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottom_menu, new OnMenuTabClickListener(){
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                int id=menuItemId;
                if(id==R.id.subjects){
                    actionBarTitle("Subjects");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,subjectFragment).commit();
                    PreferenceStorage.addWindowInfo(getBaseContext(),WindowInfo.SUBJECT);
                }else if(id==R.id.notes){
                    actionBarTitle("Notes");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,notesFragment).commit();
                    PreferenceStorage.addWindowInfo(getBaseContext(),WindowInfo.NOTES);
                }else if(id==R.id.pastpapers){
                    actionBarTitle("pastpapers");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,pastPaperFragment).commit();
                    PreferenceStorage.addWindowInfo(getBaseContext(),WindowInfo.PASTPAPER);
                }else if(id==R.id.articles){
                    actionBarTitle("Articles");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,articleFragment).commit();
                    PreferenceStorage.addWindowInfo(getBaseContext(),WindowInfo.ARTICLE);
                }else if(id==R.id.books){
                    actionBarTitle("Books");
                    PreferenceStorage.addWindowInfo(getBaseContext(),WindowInfo.BOOK);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,bookFragment).commit();
                }

            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
            bottomBar.mapColorForTab(0,"#292B2C");
            bottomBar.mapColorForTab(1,"#292B2C");
            bottomBar.mapColorForTab(2,"#292B2C");
            bottomBar.mapColorForTab(3,"#292B2C");
            bottomBar.mapColorForTab(4,"#292B2C");

            /*BottomBarBadge articles=bottomBar.makeBadgeForTabAt(0,"#ff0000",12);
            articles.show();*/

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }else if(id==R.id.feedBackMenu){
            intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"info@desapoint.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from Android app user");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }else if(id==R.id.aboutMenu){
            intent=new Intent(getBaseContext(),AboutActivity.class);
            startActivity(intent);
        }else if(id==R.id.shareMenu){
            String shared_content="Hello am using "+getResources().getString(R.string.app_name)+" to access all course materials " +
                    "Get it on google play today. https://play.google.com/store/apps/details?id=com.desapoint.desapoint";
            intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,shared_content);
            Intent cooler_one=intent.createChooser(intent,"Complete process with:-");
            startActivity(cooler_one);

        }else if(id==R.id.rateUsMenu){

            Uri uri = Uri.parse("market://details?id=" + getBaseContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName())));
            }
        }else if(id==R.id.logOut){
            showLogOutDialog("Log out");
        }else if(id==R.id.profile){
            intent=new Intent(getBaseContext(),ProfileActivity.class);
            startActivity(intent);
        }else if(id==R.id.settings){

            User user=new Gson().fromJson(PreferenceStorage.getUserJson(getBaseContext()),User.class);
            if(PreferenceStorage.getSubjectJson(getBaseContext()).equalsIgnoreCase("none")){
                loadSubjects(getBaseContext(),user.getUser_id(), ConstantInformation.SUBJECT_LIST_URL);
            }else{
                intent=new Intent(MainActivity.this,SettingActivity.class);
                intent.putExtra(Subject.NAME,PreferenceStorage.getSubjectJson(getBaseContext()));
                startActivity(intent);
            }
        }
        return true;
    }

    public void actionBarTitle(String title){

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title, null);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.main_title)).setText(this.getTitle());
        //assign the view to the actionbar
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setCustomView(v);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.sub_title)).setText(title);
        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    //the dialog issues//
    public  void showLogOutDialog(String title){

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_logout);
        dialog.setTitle(title);

        TextView ok=(TextView)dialog.findViewById(R.id.ok);
        TextView cancel= (TextView)dialog.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PreferenceStorage.clearInformation(getBaseContext());
                Intent intent=new Intent(getBaseContext(),LoginActivity.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intent);
                finish();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        try{
            dialog.show();
        }catch (Exception ex){

        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!(ConnectionChecker.isInternetConnected(getBaseContext()))) {
            Intent intent=new Intent(getBaseContext(),ConnectionProblem.class);
            startActivity(intent);
        }
        if(PreferenceStorage.getStatus(getBaseContext()).equalsIgnoreCase(PreferenceStorage.STATUS_LOGOUT)){
            Toast.makeText(getBaseContext(),"We forced you to logout for the changes you made to occur",Toast.LENGTH_LONG).show();
            PreferenceStorage.clearInformation(getBaseContext());
            Intent intent=new Intent(getBaseContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    docPaths = new ArrayList<>();
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                }
                break;
        }

    }


    public void loadSubjects(final Context context, int user_id, String url){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id",user_id);

        progress= ProgressDialog.show(MainActivity.this,"Please wait",
                "loading colleges", false);

        progress.show();

        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                Snackbar.make(bottomBar,"Please try again later",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progress.dismiss();
                if(responseString.length()<8){
                    if(responseString.equalsIgnoreCase("none")){
                        Snackbar.make(bottomBar,"Your first registration through our website was not complete please login using website to complete",Snackbar.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(bottomBar,"Something is wrong , please use about information to inform us quickly",Snackbar.LENGTH_LONG).show();
                    }
                }else{
                    Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                    intent.putExtra(Subject.NAME,responseString);
                    startActivity(intent);
                }


            }
        });

    }



}
