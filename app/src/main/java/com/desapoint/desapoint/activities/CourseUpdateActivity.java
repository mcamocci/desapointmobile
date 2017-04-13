package com.desapoint.desapoint.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.Course;
import com.desapoint.desapoint.pojos.RegistrationObject;
import com.desapoint.desapoint.pojos.User;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CourseUpdateActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private List<Course> courseList=new ArrayList<>();
    private List<String> courseStringList=new ArrayList<>();
    private LinearLayout registeButton;
    private String university=null;
    private String course=null;
    private RegistrationObject object;
    private ProgressDialog progress;
    private User user;


    private Spinner courseSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_update);

        user=new Gson().fromJson(PreferenceStorage.getUserJson(getBaseContext()),User.class);

        registeButton=(LinearLayout)findViewById(R.id.registerButton);

        String listContent=getIntent().getStringExtra(Course.JSON_VARIABLE);

        try{
            Type listType = new TypeToken<List<Course>>() {}.getType();
            courseList=new Gson().fromJson(listContent,listType);
        }catch (Exception ex){

        }

        actionBarTitle("university settings");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        courseSpinner=(Spinner)findViewById(R.id.course_spinner);

        courseSpinner.setOnItemSelectedListener(this);

        courseStringList.add("SELECT COURSE");


        for(int i=0;i<courseList.size();i++){
            courseStringList.add(courseList.get(i).getCourse());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,courseStringList);
        courseAdapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);

        registeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                register(getBaseContext());
            }
        });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position==0){
            course=null;
        }else{
            course=courseStringList.get(position);
            university=courseList.get(1).getUniversity();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void register(Context context){

            if(course!=null && university!=null){

                object=new Gson().fromJson(PreferenceStorage.getRegInfo(getBaseContext()),RegistrationObject.class);
                object.setCourse(course);
                object.setUniversity(university);
                updateCourse(context, ConstantInformation.UNIVERSITY_SETTINGS_URL,object);

            }else{
                Snackbar.make(courseSpinner,"Please select course",Snackbar.LENGTH_LONG).show();
            }
    }

    public void updateCourse(final Context context, String url,RegistrationObject object){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Log.e("user_id",Integer.toString(user.getUser_id()));
        Log.e("username",user.getUsername());
        Log.e("fullname",user.getFullname());
        Log.e("university",object.getUniversity());
        Log.e("college",object.getCollege());
        Log.e("course",object.getCourse());
        Log.e("year",object.getYear());
        Log.e("semester",object.getSemester());

        params.put("user_id",user.getUser_id());
        params.put("username",user.getUsername());
        params.put("fullname",user.getFullname());
        params.put("university",object.getUniversity());
        params.put("college",object.getCollege());
        params.put("course",object.getCourse());
        params.put("year",object.getYear());
        params.put("semester",object.getSemester());

        progress= ProgressDialog.show(CourseUpdateActivity.this,"Please wait",
                "loading colleges", false);

        progress.show();


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                Snackbar.make(courseSpinner,"Please try again later",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                progress.dismiss();
                Log.e("response",responseString);
                if(responseString.equalsIgnoreCase("success")){
                    Toast.makeText(getBaseContext(),"Information were updated , please re-login",Toast.LENGTH_LONG).show();
                    PreferenceStorage.addStatus(getBaseContext());
                    finish();
                }else{
                    Toast.makeText(getBaseContext(),"We were not able to update your information",Toast.LENGTH_LONG).show();
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
