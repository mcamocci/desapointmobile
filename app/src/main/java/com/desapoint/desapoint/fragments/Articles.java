package com.desapoint.desapoint.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.CategoryItemAdapter;
import com.desapoint.desapoint.pojos.Category;
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

public class Articles extends Fragment {

    private TextView retryLabel;
    private RecyclerView recyclerView;
    private CategoryItemAdapter adapter;
    private List<Category> items =new ArrayList<>();
    private ProgressBar progressBar;

    public Articles() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Category categ=new Category();



        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_articles, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        adapter=new CategoryItemAdapter(getContext(), items);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        retryLabel=(TextView)view.findViewById(R.id.retryLabel);
        retryLabel.setVisibility(View.INVISIBLE);

        retryLabel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                retryLabel.setVisibility(View.INVISIBLE);
                loadContents(getContext(), ConstantInformation.CATEGORY_URL);
            }
        });



       /* if(!(items.size()>0)){
            for(int i=1;i<8;i++){
                if(i%2==0){
                    categ.setNumber_books(12);
                    categ.setCategory("ECONOMICS");
                }else{
                    categ.setNumber_books(12);
                    categ.setCategory("ECONOMICS");
                }
                items.add(categ);
            }
        }*/

        if(items.size()<1){
            loadContents(getContext(), ConstantInformation.CATEGORY_URL);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void loadContents(final Context context,String url){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressBar.setVisibility(View.INVISIBLE);
                retryLabel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Type listType = new TypeToken<List<Category>>() {}.getType();
                items=new Gson().fromJson(responseString,listType);


                if(responseString.length()<8){
                    retryLabel.setVisibility(View.VISIBLE);
                    Log.e("failed","hey there");
                    Log.e("message",responseString);
                    //progressBar.setVisibility(View.INVISIBLE);
                }else{
                    retryLabel.setVisibility(View.INVISIBLE);
                    adapter=new CategoryItemAdapter(getContext(), items);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }


            }
        });

    }

}

