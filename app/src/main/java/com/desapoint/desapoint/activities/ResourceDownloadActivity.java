package com.desapoint.desapoint.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.DownloadItemAdapter;
import com.desapoint.desapoint.pojos.DownloadableItem;
import com.desapoint.desapoint.pojos.RetryObject;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_download);
        parameter=getIntent().getStringExtra(INTENTINFO);
        title=PreferenceStorage.getWindowInfo(getBaseContext());
        actionBarTitle(title,"hafksfjks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retryObject=RetryObject.getInstance(this);
        retryObject.setListener(this);

        if(title.equals(ARTICLE)){

        }else if(title.equals(BOOK)){

        }else if(title.equals(NOTES)){

        }else if(title.equals(PASTPAPER)){

        }

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

   public List<DownloadableItem> testDataGenerator(){
        List<DownloadableItem> list=new ArrayList<>();
        for(int i=0;i<12;i++){
            DownloadableItem note=new DownloadableItem();
            note.setName("Database manage.ppt");
            note.setDescription("Notes for lecture 2");
            note.setFile_url("https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=3&cad=rja&uact=8&ved=0ahUKEwjKh9uswdPSAhVkK8AKHW2NDosQFgglMAI&url=http%3A%2F%2Fcodex.cs.yale.edu%2Favi%2Fdb-book%2Fdb6%2Fslide-dir%2FPPT-dir%2Fch1.ppt&usg=AFQjCNFdBYesZZnqpEEaaQ7_-s7kVF93DQ&sig2=xqcJiOcOWUMlkXOmVRplrQ&bvm=bv.149397726,d.d24");
            list.add(note);
        }
        return list;
    }


    public void actionBarTitle(String title,String category){

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title, null);

        //assign the view to the actionbar
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.sub_title)).setText(category);
        ((TextView)v.findViewById(R.id.main_title)).setText(title);
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
}
