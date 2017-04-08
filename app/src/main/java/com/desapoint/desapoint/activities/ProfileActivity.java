package com.desapoint.desapoint.activities;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.ProfileItemAdapter;
import com.desapoint.desapoint.pojos.Subject;
import com.desapoint.desapoint.pojos.User;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.desapoint.desapoint.toolsUtilities.StringUpperHelper;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


import java.util.ArrayList;
import java.util.List;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.views.AvatarView;
import agency.tango.android.avatarviewglide.GlideLoader;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    AvatarView avatarView;
    IImageLoader imageLoader;
    private ProfileItemAdapter itemAdapter;
    private TextView label;
    private ProgressDialog progress;
    private Spinner genderSpinner;

    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText password;

    private String gender;
    private EditText email;
    private EditText phone;

    private LinearLayout updateProfile;

    private List<String> genderValues=new ArrayList<>();


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender=genderValues.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        updateProfile=(LinearLayout)findViewById(R.id.updateProfile);

        User user=new Gson().fromJson(PreferenceStorage.getUserJson(getBaseContext()),User.class);

        genderValues.add("Male");
        genderValues.add("Female");
        genderSpinner=(Spinner)findViewById(R.id.genderSpinner);
        genderSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> genderAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,genderValues);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        if(user.getGender().equalsIgnoreCase("Male")){
            genderSpinner.setSelection(0);
        }else{
            genderSpinner.setSelection(1);
        }


        firstName=(EditText)findViewById(R.id.firstName);
        firstName.setText(user.getFirstName());

        username=(EditText)findViewById(R.id.userName);
        username.setText(user.getUsername());

        lastName=(EditText)findViewById(R.id.lastName);
        String name[]=user.getFullname().split(" ");
        lastName.setText(name[1]);

        password=(EditText)findViewById(R.id.password);
        ///

        email=(EditText)findViewById(R.id.email);
        email.setText(user.getEmail());

        phone=(EditText)findViewById(R.id.phone);
        phone.setText(user.getPhone());


        avatarView = (AvatarView) findViewById(R.id.avatar_view);
        imageLoader = new GlideLoader();
        imageLoader.loadImage(avatarView,ConstantInformation.PROFILE_IMAGE_URL+user.getImage(),user.getFirstName());
        label=(TextView)findViewById(R.id.label);
        label.setText(StringUpperHelper.doUpperlization(user.getFullname()));

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameValue=firstName.getText().toString().trim();
                String lastNameValue=lastName.getText().toString().trim();
                String emailValue=email.getText().toString().trim();
                String phoneValue=phone.getText().toString().trim();
                if(firstNameValue.length()<2 || lastNameValue.length()<2|| phoneValue.length()<10||
                        emailValue.length()<6){
                    Toast.makeText(getBaseContext(),"We suspect invalid information",Toast.LENGTH_LONG).show();
                }else{
                    approveUpdate(ProfileActivity.this,firstNameValue,lastNameValue,gender,emailValue,phoneValue);
                }


            }
        });

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


    public void updateProfile(final Context context,
        String firstName,String lastName,String gender,String email,String phone){

        User user=new Gson().fromJson(PreferenceStorage.getUserJson(getBaseContext()),User.class);

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id",user.getUser_id());
        params.put("firstName",firstName);
        params.put("lastName",lastName);
        params.put("gender",gender);
        params.put("email",email);
        params.put("phone",phone);

        httpClient.setEnableRedirects(true);

        progress= ProgressDialog.show(ProfileActivity.this,"Please wait",
                "Updating profile..", false);
        progress.show();

        httpClient.post(context,ConstantInformation.UPDATE_PROFILE_URL, params,new TextHttpResponseHandler() {

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
                        Snackbar.make(avatarView,"We were not able to complete update",Snackbar.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(avatarView,"Information update ,You will be taken out for a while for the changes to occur",Snackbar.LENGTH_LONG).show();
                        PreferenceStorage.addStatus(getBaseContext());
                    }
                }


            }
        });

    }

    //the dialog issues//
    public  void approveUpdate(final Context context,final String firstName,final String lastName
            ,final String gender,final String email,final String phone)
    {

        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_update_profile);
        dialog.setTitle("Update Information");

        TextView ok=(TextView)dialog.findViewById(R.id.ok);
        TextView cancel= (TextView)dialog.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateProfile(getBaseContext(),firstName,lastName,gender,email,phone);
                dialog.dismiss();
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
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }


    }



}
