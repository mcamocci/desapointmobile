package com.desapoint.desapoint.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.ProfileItemAdapter;
import com.desapoint.desapoint.adapters.SubjectItemAdapter;
import com.desapoint.desapoint.pojos.Subject;
import com.desapoint.desapoint.pojos.User;
import com.desapoint.desapoint.pojos.WindowInfo;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.desapoint.desapoint.toolsUtilities.StringUpperHelper;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.views.AvatarView;
import agency.tango.android.avatarviewglide.GlideLoader;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    AvatarView avatarView;
    IImageLoader imageLoader;
    private ProfileItemAdapter itemAdapter;
    private TextView label;
    private ProgressDialog progress;

    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText password;

    private Spinner gender;
    private EditText email;
    private EditText phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user=new Gson().fromJson(PreferenceStorage.getUserJson(getBaseContext()),User.class);


        firstName=(EditText)findViewById(R.id.firstName);
        firstName.setText(user.getFirstName());

        username=(EditText)findViewById(R.id.userName);
        username.setText(user.getUsername());

        lastName=(EditText)findViewById(R.id.lastName);
        lastName.setText(user.getFullname());

        password=(EditText)findViewById(R.id.password);
        ///

        gender=(Spinner)findViewById(R.id.gender);
       // gender.setSelected();

        email=(EditText)findViewById(R.id.email);
        email.setText(user.getEmail());

        phone=(EditText)findViewById(R.id.phone);
        phone.setText(user.getPhone());


        avatarView = (AvatarView) findViewById(R.id.avatar_view);
        imageLoader = new GlideLoader();
        imageLoader.loadImage(avatarView,ConstantInformation.PROFILE_IMAGE_URL+user.getImage(),user.getFirstName());
        label=(TextView)findViewById(R.id.label);
        label.setText(StringUpperHelper.doUpperlization(user.getFullname()));

       /* updateCourse=(LinearLayout)findViewById(R.id.updateButton);
        updateCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateProcess(getBaseContext(), ConstantInformation.USER_UNIVERSITY);
            }
        });
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarTitle("Profile settings");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }else if(id==R.id.settings){
            User user=new Gson().fromJson(PreferenceStorage.getUserJson(getBaseContext()),User.class);

            if(PreferenceStorage.getSubjectJson(getBaseContext()).equalsIgnoreCase("none")){
                loadSubjects(getBaseContext(),user.getUser_id(),ConstantInformation.SUBJECT_LIST_URL);
            }else{
                Intent intent=new Intent(ProfileActivity.this,SettingActivity.class);
                intent.putExtra(Subject.NAME,PreferenceStorage.getSubjectJson(getBaseContext()));
                startActivity(intent);
            }

        }
        return true;
    }

    public void actionBarTitle(String title){

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title_other, null);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.title)).setText(title);
        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }

    public void loadSubjects(final Context context, int user_id, String url){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id",user_id);

        progress= ProgressDialog.show(ProfileActivity.this,"Please wait",
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
                Snackbar.make(avatarView,"Please try again later",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progress.dismiss();
               if(responseString.length()<8){
                    if(responseString.equalsIgnoreCase("none")){
                        Snackbar.make(avatarView,"Your first registration through our website was not complete please login using website to complete",Snackbar.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(avatarView,"Something is wrong , please use about information to inform us quickly",Snackbar.LENGTH_LONG).show();
                    }
                }else{
                   Intent intent=new Intent(ProfileActivity.this,SettingActivity.class);
                   intent.putExtra(Subject.NAME,responseString);
                   startActivity(intent);
                }


            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


}
