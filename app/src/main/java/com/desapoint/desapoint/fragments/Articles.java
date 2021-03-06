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
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.CategoryItemAdapter;
import com.desapoint.desapoint.pojos.Category;
import com.desapoint.desapoint.pojos.RetryObjectFragment;
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

public class Articles extends Fragment implements RetryObjectFragment.ReloadListener {

    private RecyclerView recyclerView;
    private CategoryItemAdapter adapter;
    private List<Category> items =new ArrayList<>();
    private RetryObjectFragment retryObject;

    public Articles() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onReloaded(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
        loadContents(getContext(), ConstantInformation.CATEGORY_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_articles, container, false);
        retryObject=RetryObjectFragment.getInstance(view);
        retryObject.setListener(this);


        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CategoryItemAdapter(getContext(), items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if(items.size() < 1){

            if(PreferenceStorage.getCategoriesJson(getContext()).equalsIgnoreCase("none")){
                loadContents(getContext(), ConstantInformation.CATEGORY_URL);
            }else{
                retryObject.hideProgress();
                retryObject.hideMessage();
                retryObject.hideName();
                try{
                    Type listType = new TypeToken<List<Category>>() {}.getType();
                    items=new Gson().fromJson(PreferenceStorage.getCategoriesJson(getContext()),listType);

                }catch (Exception ex){

                }

                adapter = new CategoryItemAdapter(getContext(), items);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }else{
            retryObject.hideProgress();
            retryObject.hideMessage();
            retryObject.hideName();
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
                retryObject.hideMessage();
                retryObject.hideName();
                retryObject.showProgress();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                retryObject.hideProgress();
                retryObject.showName();
                Log.e("message",responseString);
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

                    try{
                        Type listType = new TypeToken<List<Category>>() {}.getType();
                        items=new Gson().fromJson(responseString,listType);
                        PreferenceStorage.addCategoriesJson(getContext(),responseString);

                    }catch (Exception ex){

                    }

                    adapter=new CategoryItemAdapter(getContext(), items);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }
        });

    }

}

