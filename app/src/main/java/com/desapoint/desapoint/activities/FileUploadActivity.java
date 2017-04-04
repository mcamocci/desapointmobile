package com.desapoint.desapoint.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.UploadItem;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.google.gson.Gson;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.util.UUID;

public class FileUploadActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private String filePath=null;
    private UploadItem uploadItem;

    private EditText writter;
    private LinearLayout writterHolder;
    private TextView fileLabel;

    private LinearLayout fileUpload;
    private LinearLayout cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        writterHolder=(LinearLayout)findViewById(R.id.writterHolder);
        writter=(EditText)findViewById(R.id.writter);

        fileLabel=(TextView)findViewById(R.id.file_label);

        filePath=getIntent().getStringExtra("FILE");
        uploadItem=new Gson().fromJson(getIntent().getStringExtra(UploadItem.HOLDING_NAME),UploadItem.class);

        if(uploadItem.getType().equals(UploadItem.ARTICLE_TYPE)){
            writterHolder.setVisibility(View.VISIBLE);
        }

        File file=new File(filePath);
        fileLabel.setText(file.getName());

        name=(EditText)findViewById(R.id.name);
        description=(EditText)findViewById(R.id.description);

        fileUpload=(LinearLayout)findViewById(R.id.upload);
        cancel=(LinearLayout)findViewById(R.id.cancel);

        fileUpload.setOnClickListener(new View.OnClickListener(){
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

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        actionBarTitle("Upload content");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.upload,menu);
        return true;
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

    public void uploadBooks(){

            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                UploadNotificationConfig uploadNotificationConfig=new UploadNotificationConfig();
                uploadNotificationConfig.setTitle("Uploading Book");
                uploadNotificationConfig.setCompletedIconColor(android.R.color.holo_red_dark);
                uploadNotificationConfig.setCompletedMessage("The selected book was uploaded successfully");
                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId,ConstantInformation.UPLOAD_BOOK_URL)

                        .addParameter("USER_ID","1") //Adding text parameter to the request
                      /*  .addFileToUpload(photoPaths.get(0),"RESOURCES[]")
                        .addParameter("CONTENT",writtenMessage.getText().toString().trim()) //Adding text parameter to the request
                        .setNotificationConfig(uploadNotificationConfig)*/
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }

    public void uploadNotes(){

        try {
            String uploadId = UUID.randomUUID().toString();
            UploadNotificationConfig uploadNotificationConfig=new UploadNotificationConfig();
            uploadNotificationConfig.setTitle("Uploading Note");
            uploadNotificationConfig.setCompletedIconColor(android.R.color.holo_red_dark);
            uploadNotificationConfig.setCompletedMessage("The selected File has been uploaded");

            new MultipartUploadRequest(this, uploadId, ConstantInformation.UPLOAD_NOTES_URL)

                    .addParameter("USER_ID","1") //Adding text parameter to the request
                 /*   .addFileToUpload(photoPaths.get(0),"RESOURCES[]")
                    .addParameter("CONTENT",writtenMessage.getText().toString().trim())
                     //Adding text parameter to the request
                    .setNotificationConfig(uploadNotificationConfig)
                    .setMaxRetries(5)*/
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadArticle(){

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            UploadNotificationConfig uploadNotificationConfig=new UploadNotificationConfig();
            uploadNotificationConfig.setTitle("Uploading Article");
            uploadNotificationConfig.setCompletedIconColor(android.R.color.holo_red_dark);
            uploadNotificationConfig.setCompletedMessage("The selected Article file was uploaded successfully");
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId,ConstantInformation.UPLOAD_ARTICLE_URL)

                    .addParameter("USER_ID","1") //Adding text parameter to the request
                   /* .addFileToUpload(photoPaths.get(0),"RESOURCES[]")
                    .addParameter("CONTENT",writtenMessage.getText().toString().trim()) //Adding text parameter to the request
                    .setNotificationConfig(uploadNotificationConfig)*/
                    .setMaxRetries(5)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
