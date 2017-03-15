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
import com.desapoint.desapoint.adapters.SubjectItemAdapter;
import com.desapoint.desapoint.pojos.Note;
import com.desapoint.desapoint.pojos.Subject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.Header;


public class Notes extends Fragment {

    private RecyclerView recyclerView;
    private SubjectItemAdapter adapter;
    private List<Subject> subjects=new ArrayList<>();

    public Notes() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Subject subject=new Subject();

        if(!(subjects.size()>0)){
            for(int i=1;i<8;i++){
                if(i%2==0){
                    subject.setSubject_code("ITU");
                    subject.setSubject("Information technology");
                }else{
                    subject.setSubject_code("CSU");
                    subject.setSubject("Communication Skills");
                }
                subjects.add(subject);
            }
        }


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_subjects, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);

        adapter=new SubjectItemAdapter(getContext(),subjects);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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

    public void loadContents(final Context context, String username, String password, String url){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",username);
        params.put("password",password);


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context,responseString,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Toast.makeText(context,responseString,Toast.LENGTH_SHORT).show();

                Type listType = new TypeToken<List<Note>>() {}.getType();
                subjects=new Gson().fromJson(responseString,listType);

                if(responseString.length()<8){
                    Log.e("failed","hey there");
                    Log.e("message",responseString);
                    //progressBar.setVisibility(View.INVISIBLE);
                }else{
                    Log.e("won","hey there");
                    Log.e("message",responseString);
                    //progressBar.setVisibility(View.INVISIBLE);
                }


            }
        });

    }
}
