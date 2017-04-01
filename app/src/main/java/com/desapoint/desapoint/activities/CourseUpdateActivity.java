package com.desapoint.desapoint.activities;

import android.support.design.widget.Snackbar;
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
import com.desapoint.desapoint.pojos.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CourseUpdateActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private List<Course> courseList=new ArrayList<>();
    private List<String> courseStringList=new ArrayList<>();
    private LinearLayout registeButton;
    private String course=null;


    private Spinner courseSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_update);

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
                register();
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void register(){

            if(course!=null){

            }else{
                Snackbar.make(courseSpinner,"Please select course",Snackbar.LENGTH_LONG).show();
            }
    }
}
