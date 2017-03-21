package com.desapoint.desapoint.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;

public class FileUploadActivity extends AppCompatActivity {

    private LinearLayout chooserButton;
    private LinearLayout publishButton;
    private EditText name;
    private EditText description;
    private String filePath=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        chooserButton=(LinearLayout)findViewById(R.id.attach_button);
        publishButton=(LinearLayout)findViewById(R.id.button_publish);

        name=(EditText)findViewById(R.id.upload_name);
        description=(EditText)findViewById(R.id.upload_description);

        publishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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
        });
        chooserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                DialogProperties properties = new DialogProperties();
                properties.selection_mode = DialogConfigs.SINGLE_MODE;
                properties.selection_type = DialogConfigs.FILE_SELECT;
                properties.root = new File(DialogConfigs.DEFAULT_DIR);
                properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
                properties.offset = new File(DialogConfigs.DEFAULT_DIR);
                properties.extensions = null;

                FilePickerDialog dialog = new FilePickerDialog(FileUploadActivity.this,properties);
                dialog.setTitle("Select a File");

                dialog.setDialogSelectionListener(new DialogSelectionListener() {
                    @Override
                    public void onSelectedFilePaths(String[] files) {
                        //files is the array of the paths of files selected by the Application User.
                        filePath=files[0];
                        Toast.makeText(getBaseContext(),files[0],Toast.LENGTH_LONG).show();
                    }
                });

                dialog.show();

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        actionBarTitle("Upload content");
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
