package com.desapoint.desapoint.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.desapoint.desapoint.pojos.College;
import com.desapoint.desapoint.pojos.Course;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.LineNumberReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivityscreenTwo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private LinearLayout nextButton;

    // Spinner elements
    private Spinner collegeSpinner;
    private Spinner semisterSpinner;
    private Spinner yearSpinner;
    private List<College> collegeList=new ArrayList<>();
    private ProgressDialog progress;
    private String university="0";
    private String college="0";
    private String year="0";
    private String semester="0";
    private List<String> colleges=new ArrayList<>();
    private List<String> years=new ArrayList<>();
    private List<String> semisters = new ArrayList<String>();;


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId()==R.id.college){

            if(position==0){
                college="0";
            }else{
                college=colleges.get(position);
                university=collegeList.get(position-1).getUniversity();
                Toast.makeText(getBaseContext(),university,Toast.LENGTH_LONG).show();
            }

        }else if(parent.getId()==R.id.semester){
            if(position==0){
                semester="0";
            }else{
                semester=semisters.get(position);
            }
        }else if(parent.getId()==R.id.year){
            if(position==0){
                year="0";
            }else{
                year=years.get(position);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_activityscreen_two);
        nextButton=(LinearLayout)findViewById(R.id.nextButton);


        String listContent=getIntent().getStringExtra(College.JSON_VARIABLE);
        try{
            Type listType = new TypeToken<List<College>>() {}.getType();
            collegeList=new Gson().fromJson(listContent,listType);
        }catch (Exception ex){

        }

        collegeSpinner=(Spinner) findViewById(R.id.college);
        collegeSpinner.setOnItemSelectedListener(this);
        semisterSpinner=(Spinner) findViewById(R.id.semester);
        semisterSpinner.setOnItemSelectedListener(this);
        yearSpinner=(Spinner) findViewById(R.id.year);
        yearSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        colleges.add("SELECT COLLEGE");
        for(int i=0;i<collegeList.size();i++){
           colleges.add(collegeList.get(i).getName());
        }

        //spinner years
        years.add("SELECT YEAR");
        years.add("1");years.add("2");
        years.add("3");years.add("4");years.add("5");
        semisters.add("SELECT SEMESTER");
        semisters.add("1");semisters.add("2");


        // Creating adapter for spinner
        ArrayAdapter<String> semisterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,semisters);
        semisterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semisterSpinner.setAdapter(semisterAdapter);

        // Creating adapter for spinner
        ArrayAdapter<String> yearsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,years);
        yearsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearsadapter);


        // Creating adapter for spinner
        ArrayAdapter<String> collegeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,colleges);
        collegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collegeSpinner.setAdapter(collegeAdapter);


        actionBarTitle("Registration");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Toast.makeText(getBaseContext(),university+" "+college,Toast.LENGTH_LONG).show();
                getCourses(getBaseContext(),
                        ConstantInformation.COLLEGE_COURSE_LIST,
                        university,college);

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

    public void getCourses(final Context context, String url, String param,String param1){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("university",param);
        params.put("college",param1);
        progress= ProgressDialog.show(RegistrationActivityscreenTwo.this,"Please wait",
                "loading colleges", false);

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
                    Toast.makeText(context,"Could not load courses",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(getBaseContext(),RegistrationFinalActivity.class);
                    intent.putExtra(Course.JSON_VARIABLE,responseString);
                    startActivity(intent);
                    finish();
                }


            }
        });

    }
}
