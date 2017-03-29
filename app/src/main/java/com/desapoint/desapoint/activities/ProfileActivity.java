package com.desapoint.desapoint.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.ProfileItemAdapter;
import com.desapoint.desapoint.pojos.College;
import com.desapoint.desapoint.pojos.ProfileObject;
import com.desapoint.desapoint.pojos.RegistrationObject;
import com.desapoint.desapoint.pojos.User;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
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


public class ProfileActivity extends AppCompatActivity {

    AvatarView avatarView;
    IImageLoader imageLoader;
    private LinearLayout updateCourse;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProfileItemAdapter itemAdapter;
    private ProgressDialog progress;
    private List<ProfileObject> profileObjectList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        updateCourse=(LinearLayout)findViewById(R.id.updateButton);
        updateCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateProcess(getBaseContext(), ConstantInformation.USER_UNIVERSITY);
            }
        });

        User user=new Gson().fromJson(
                PreferenceStorage.getUserJson(getBaseContext()),User.class);

        ProfileObject object=new ProfileObject();
        object.setContent(user.getFirstName());
        object.setStringType("First name");
        profileObjectList.add(object);

        ProfileObject object1=new ProfileObject();
        object1.setContent(user.getGender());
        object1.setStringType("Gender");
        profileObjectList.add(object1);

        ProfileObject object2=new ProfileObject();
        object2.setContent(user.getUsername());
        object2.setStringType("Username");
        profileObjectList.add(object2);

        ProfileObject object3=new ProfileObject();
        object3.setContent(user.getEmail());
        object3.setStringType("Email");
        profileObjectList.add(object3);

        ProfileObject object4=new ProfileObject();
        object4.setContent(user.getPhone());
        object4.setStringType("Phone");
        profileObjectList.add(object4);

        layoutManager=new LinearLayoutManager(getBaseContext());
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);



        itemAdapter=new ProfileItemAdapter(getBaseContext(),profileObjectList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);



        avatarView = (AvatarView) findViewById(R.id.avatar_view);
        imageLoader = new GlideLoader();
        imageLoader.loadImage(avatarView,ConstantInformation.PROFILE_IMAGE_URL+user.getImage(),user.getFirstName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarTitle(user.getFirstName().toUpperCase());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
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

    public void updateProcess(final Context context, String url){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        User user=new Gson().fromJson(PreferenceStorage.getUserJson(getBaseContext()),User.class);
        params.put("user_id",user.getUser_id());
        progress= ProgressDialog.show(ProfileActivity.this,"Please wait",
                "performing reset action", false);

        progress.show();


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                Toast.makeText(context,"Please try again later",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                progress.dismiss();
                Log.e("response",responseString);
                if(responseString.length()<8){
                    Snackbar.make(updateCourse,
                            "Could not prepare course update please try again later!!"
                            ,Snackbar.LENGTH_LONG).show();
                }else{

                    RegistrationObject registrationObject=new RegistrationObject();
                    String content=new Gson().toJson(registrationObject);
                    PreferenceStorage.addRegInfo(context,content);
                    Intent intent=new Intent(getBaseContext(),CollegeUPdate.class);
                    intent.putExtra(College.JSON_VARIABLE,responseString);
                    startActivity(intent);

                }


            }
        });

    }

}
