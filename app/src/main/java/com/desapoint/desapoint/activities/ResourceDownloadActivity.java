package com.desapoint.desapoint.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.DownloadItemAdapter;
import com.desapoint.desapoint.pojos.DownloadableItem;
import com.desapoint.desapoint.pojos.RetryObject;
import com.desapoint.desapoint.pojos.UploadItem;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

import static com.desapoint.desapoint.pojos.WindowInfo.ARTICLE;
import static com.desapoint.desapoint.pojos.WindowInfo.BOOK;
import static com.desapoint.desapoint.pojos.WindowInfo.NOTES;
import static com.desapoint.desapoint.pojos.WindowInfo.PASTPAPER;
import static com.desapoint.desapoint.toolsUtilities.ConstantInformation.INTENTINFO;

public class ResourceDownloadActivity extends AppCompatActivity implements RetryObject.ReloadListener {

    private List<DownloadableItem> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private DownloadItemAdapter adapter;
    private RetryObject retryObject;
    private String title;
    private String parameter;
    private UploadItem uploadItem;
    private FloatingActionButton fab;
    private String uploadItemJson;
    private Activity activity;
    private List<String> docPaths=new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity=this;
        setContentView(R.layout.activity_resource_download);
        fab = (FloatingActionButton) findViewById(R.id.adder);
        parameter=getIntent().getStringExtra(INTENTINFO);
        title=PreferenceStorage.getWindowInfo(getBaseContext());

        if(title.equalsIgnoreCase(UploadItem.ARTICLE_TYPE)){

            uploadItem=new UploadItem();
            uploadItem.setType(UploadItem.ARTICLE_TYPE);
            uploadItem.setCategory(parameter);
            //fab.setVisibility(View.GONE);

        }else if(title.equalsIgnoreCase(UploadItem.BOOK_TYPE)){

            uploadItem=new UploadItem();
            uploadItem.setType(UploadItem.BOOK_TYPE);
            uploadItem.setCategory(parameter);
           // fab.setVisibility(View.GONE);

        }else if(title.equalsIgnoreCase(UploadItem.NOTES_TYPE)){

            uploadItem=new UploadItem();
            uploadItem.setType(UploadItem.NOTES_TYPE);
            uploadItem.setSubject(parameter);

        }else{
            fab.setVisibility(View.GONE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        String mytitle=title.toLowerCase();
        actionBarTitle(mytitle.substring(0,1).toUpperCase()+mytitle.substring(1)+" - "+parameter);
        retryObject=RetryObject.getInstance(this);
        retryObject.setListener(this);
        final Activity context=this;

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                FilePickerBuilder.getInstance().setMaxCount(2)
                        .setActivityTheme(R.style.filePicker)
                        .pickDocument(activity);
            }
        });


        //get the contents
        loadContents(getBaseContext());

        //list=testDataGenerator();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new DownloadItemAdapter(getBaseContext(),list);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onReloaded(String message) {
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
        //get the contents
        loadContents(getBaseContext());
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


    @Override
    protected void onResume() {
        super.onResume();
        String title=PreferenceStorage.getWindowInfo(getBaseContext());

        if(title.equals(ARTICLE)){

        }else if(title.equals(BOOK)){

        }else if(title.equals(NOTES)){

        }else if(title.equals(PASTPAPER)){

        }
    }

    public void loadContents(final Context context){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();

        String url=null;

        if(title.equals(ARTICLE)){

            params.put("category",parameter);
            url= ConstantInformation.ARTICLE_URL;

        }else if(title.equals(BOOK)){

            params.put("category",parameter);
            url= ConstantInformation.BOOKS_URL;


        }else if(title.equals(NOTES)){

            params.put("subject",parameter);
            url= ConstantInformation.NOTES_URL;

        }else if(title.equals(PASTPAPER)){

            params.put("subject",parameter);
            url= ConstantInformation.PASTPAPER_URL;
        }


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                retryObject.hideMessage();
                retryObject.hideName();
                retryObject.showProgress();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    retryObject.hideProgress();
                    retryObject.showName();
                    retryObject.getMessage().setText("Could not connect!!");
                    retryObject.showMessage();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                  retryObject.hideProgress();

                if(responseString.length()<8){
                    retryObject.getMessage().setText("No content");
                    retryObject.showMessage();
                    retryObject.showName();
                }else{
                    Log.e("the response",responseString);
                    retryObject.hideMessage();
                    retryObject.hideName();
                    Type listType = new TypeToken<List<DownloadableItem>>() {}.getType();
                    list=new Gson().fromJson(responseString,listType);
                    adapter=new DownloadItemAdapter(getBaseContext(),list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    if(docPaths.size()>1){
                        docPaths.clear();
                        Toast.makeText(getBaseContext(),"This version only support single file upload",Toast.LENGTH_LONG).show();
                    }else{
                        String filePath=docPaths.get(0);
                        Intent intent=new Intent(getBaseContext(),FileUploadActivity.class);
                        intent.putExtra("FILE",filePath);
                        intent.putExtra(UploadItem.HOLDING_NAME,new Gson().toJson(uploadItem,UploadItem.class));
                        startActivity(intent);
                    }

                }
                break;
        }

    }

}
