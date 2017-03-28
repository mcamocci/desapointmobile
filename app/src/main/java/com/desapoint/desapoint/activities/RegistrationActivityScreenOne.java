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
import com.desapoint.desapoint.pojos.University;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivityScreenOne extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Spinner element
    private Spinner spinner;
    private LinearLayout nextButton;
    private List<University> universityList=new ArrayList<>();
    private String UNIVERSITY_SELECTOR="SELECT UNIVERSITY";
    private ProgressDialog progress;
    private String universityName;
    // Spinner Drop down elements
    List<String> categories = new ArrayList<String>();
    private int universityPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen_one);

        String listContent=getIntent().getStringExtra(University.JSON_VARIABLE);
        final String university=getIntent().getStringExtra(University.NAME);
        try{
            Type listType = new TypeToken<List<University>>() {}.getType();
            universityList=new Gson().fromJson(listContent,listType);
        }catch (Exception ex){

        }

        spinner = (Spinner) findViewById(R.id.university);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);


        categories.add(UNIVERSITY_SELECTOR);
        for(int i=0;i<universityList.size();i++){
            categories.add(universityList.get(i).getName());
        }

        nextButton=(LinearLayout)findViewById(R.id.nextButton);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        actionBarTitle("Registration");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(universityPosition==0){
                    Toast.makeText(getBaseContext(),"Please select university",Toast.LENGTH_LONG).show();
                }else{
                    getColleges(getBaseContext(), ConstantInformation.UNIVERSITY_COLLEGE_URL,universityName);
                }

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
        universityPosition=position;
        universityName=categories.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getColleges(final Context context, String url,String param){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("university",param);
        progress= ProgressDialog.show(RegistrationActivityScreenOne.this,"Please wait",
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
                    Toast.makeText(context,"Could not load colleges",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(getBaseContext(),RegistrationActivityscreenTwo.class);
                    intent.putExtra(College.JSON_VARIABLE,responseString);
                    startActivity(intent);
                    finish();
                }


            }
        });

    }
}
