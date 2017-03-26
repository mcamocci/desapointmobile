package com.desapoint.desapoint.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.University;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RegistrationActivityScreenOne extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Spinner element
    private Spinner spinner;
    private LinearLayout nextButton;
    private List<University> universityList=new ArrayList<>();
    private String UNIVERSITY_SELECTOR="SELECT UNIVERSITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen_one);

        String listContent=getIntent().getStringExtra(University.JSON_VARIABLE);
        try{
            Type listType = new TypeToken<List<University>>() {}.getType();
            universityList=new Gson().fromJson(listContent,listType);
        }catch (Exception ex){

        }

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

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
                Intent intent=new Intent(getBaseContext(),RegistrationActivityscreenTwo.class);
                startActivity(intent);
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
