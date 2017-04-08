package com.desapoint.desapoint.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.AddSubjectAdapter;
import com.desapoint.desapoint.pojos.Subject;
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

public class AddSubjectActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private AddSubjectAdapter adapter;
    private List<Subject> subjectList;
    private ProgressDialog progress;
    private LinearLayout searchButton;
    private EditText searchedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        searchedWord=(EditText)findViewById(R.id.search_word);
        searchButton=(LinearLayout)findViewById(R.id.search_button);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        subjectList=new ArrayList<>();
        adapter=new AddSubjectAdapter(getBaseContext(),subjectList);
        recyclerView.setAdapter(adapter);


        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                View view=AddSubjectActivity.this.getCurrentFocus();
                subjectList=new ArrayList<Subject>();
                if(view!=null){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                }

                String word=searchedWord.getText().toString().trim();
                if(word.length()<2){
                    Snackbar.make(searchButton,"Too short keyword",Snackbar.LENGTH_LONG).show();
                }else{
                    searchContent(getBaseContext(), ConstantInformation.SEARCH_SUBJECTS_URL,word);
                }

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        actionBarTitle("Adding Subject");
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

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title_other, null);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.title)).setText(title);
        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);

    }

    public void searchContent(final Context context,String url,String search){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("keyword",search);
        progress= ProgressDialog.show(AddSubjectActivity.this,"Please wait",
                "searching in progress", false);
        progress.show();


        httpClient.post(context,url,params, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getBaseContext(),"Please try again later",Toast.LENGTH_LONG).show();
                progress.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progress.dismiss();
                if(responseString.equalsIgnoreCase("none")){
                    Snackbar.make(recyclerView,"No results found",Snackbar.LENGTH_LONG).show();
                }else{

                    Type listType = new TypeToken<List<Subject>>() {}.getType();
                    subjectList=new ArrayList<Subject>();
                    subjectList=new Gson().fromJson(responseString,listType);
                    adapter=new AddSubjectAdapter(getBaseContext(),subjectList);
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


}
