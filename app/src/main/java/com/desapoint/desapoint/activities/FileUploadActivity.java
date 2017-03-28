package com.desapoint.desapoint.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
public class FileUploadActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private String filePath=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        filePath=getIntent().getStringExtra("FILE");
        name=(EditText)findViewById(R.id.upload_name);
        description=(EditText)findViewById(R.id.upload_description);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        actionBarTitle("Upload content");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }else if(id==R.id.upload_button){
            if(name.getText().toString().trim().length()>5
                    && description.getText().toString().trim().length()>10){

                if(filePath==null){
                    Toast.makeText(getBaseContext(),"Please select the file to upload",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(),"We are good emmanuel",Toast.LENGTH_SHORT).show();
                }

            }else{

                if(name.getText().toString().trim().length()<5){
                    Toast.makeText(getBaseContext(),"name too short",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(),"description too short",Toast.LENGTH_SHORT).show();
                }

            }

        }
        return true;
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
}
