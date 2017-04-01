package com.desapoint.desapoint.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.desapoint.desapoint.adapters.SubjectSettingAdapter;
import com.desapoint.desapoint.pojos.College;
import com.desapoint.desapoint.pojos.RegistrationObject;
import com.desapoint.desapoint.pojos.Subject;
import com.desapoint.desapoint.pojos.User;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SettingActivity extends AppCompatActivity {

    private LinearLayout universitySettingsButton;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private SubjectSettingAdapter adapter;
    private List<Subject> subjectList;
    private ProgressDialog progress;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        universitySettingsButton=(LinearLayout)findViewById(R.id.university_setting);

        Type listType = new TypeToken<List<Subject>>() {}.getType();
        subjectList=new Gson().fromJson(getIntent().getStringExtra(Subject.NAME),listType);
        PreferenceStorage.addSubjectJson(getBaseContext(),getIntent().getStringExtra(Subject.NAME));
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new SubjectSettingAdapter(getBaseContext(),subjectList);
        recyclerView.setAdapter(adapter);

        universitySettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateProcess(getBaseContext(), ConstantInformation.USER_UNIVERSITY);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        actionBarTitle("Settings");

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
        progress= ProgressDialog.show(SettingActivity.this,"Please wait",
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
                    Snackbar.make(universitySettingsButton,
                            "Could not prepare course update please try again later!!"
                            , Snackbar.LENGTH_LONG).show();
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
