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
import com.desapoint.desapoint.pojos.RetryObjectFragment;
import com.desapoint.desapoint.pojos.Subject;
import com.desapoint.desapoint.pojos.User;
import com.desapoint.desapoint.pojos.WindowInfo;
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


public class PastPaperFragment extends Fragment implements RetryObjectFragment.ReloadListener{

    private RecyclerView recyclerView;
    private SubjectItemAdapter adapter;
    private List<Subject> subjects=new ArrayList<>();
    private RetryObjectFragment retryObject;
    private User user;
    public PastPaperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onReloaded(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
        loadContents(getContext(),user.getUser_id(), ConstantInformation.SUBJECT_LIST_URL);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_subjects, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);


        retryObject= RetryObjectFragment.getInstance(view);
        retryObject.setListener(this);


        adapter=new SubjectItemAdapter(getContext(),subjects,WindowInfo.PASTPAPER);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Subject subject=new Subject();

        user=new Gson().fromJson(
                PreferenceStorage.getUserJson(getContext()),User.class);

        if(subjects.size()<1){
            loadContents(getContext(),user.getUser_id(), ConstantInformation.SUBJECT_LIST_URL);
            recyclerView.setAdapter(adapter);
        }else{
            retryObject.hideProgress();
            retryObject.hideMessage();
            retryObject.hideName();
        }

   /*     if(!(subjects.size()>0)){
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
        }*/


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

    public void loadContents(final Context context,int user_id, String url){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id",user_id);


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

                    if(responseString.equalsIgnoreCase("none")){
                        retryObject.getMessage().setText(
                                user.getFirstName()
                                        +" Please make sure you have completed registration through your computer" +
                                        " or try again later");
                    }else{
                        retryObject.getMessage().setText("No content");
                    }
                    retryObject.showMessage();
                    retryObject.showName();
                }else{
                    Type listType = new TypeToken<List<Subject>>() {}.getType();
                    subjects=new Gson().fromJson(responseString,listType);
                    adapter=new SubjectItemAdapter(getContext(),subjects, WindowInfo.PASTPAPER);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }
        });

    }

}
