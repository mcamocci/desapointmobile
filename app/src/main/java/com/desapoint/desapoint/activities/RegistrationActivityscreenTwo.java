package com.desapoint.desapoint.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.desapoint.desapoint.R;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivityscreenTwo extends AppCompatActivity {

    private LinearLayout nextButton;
    
    // Spinner elements
    private Spinner collegeSpinner;
    private Spinner semisterSpinner;
    private Spinner yearSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_activityscreen_two);
        nextButton=(LinearLayout)findViewById(R.id.nextButton);

        collegeSpinner=(Spinner) findViewById(R.id.college);
        semisterSpinner=(Spinner) findViewById(R.id.semester);
        yearSpinner=(Spinner) findViewById(R.id.year);

        // Spinner Drop down elements
        List<String> colleges = new ArrayList<String>();

        List<String> years = new ArrayList<String>();
        years.add("1");years.add("2");
        years.add("3");years.add("4");years.add("5");
        List<String> semister = new ArrayList<String>();
        semister.add("1");semister.add("2");


        // Creating adapter for spinner
        ArrayAdapter<String> semisterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,semister);
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
                Intent intent=new Intent(getBaseContext(),RegistrationFinalActivity.class);
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
}
