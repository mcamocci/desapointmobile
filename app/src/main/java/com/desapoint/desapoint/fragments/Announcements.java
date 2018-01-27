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
import com.desapoint.desapoint.adapters.AnnouncementItemAdapter;
import com.desapoint.desapoint.kotlindata.AnnouncementDataObject;
import com.desapoint.desapoint.pojos.RetryObjectFragment;
import com.desapoint.desapoint.pojos.User;
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

public class Announcements extends Fragment implements RetryObjectFragment.ReloadListener {

    private RecyclerView recyclerView;
    private AnnouncementItemAdapter adapter;
    private List<AnnouncementDataObject> items = new ArrayList<>();
    private RetryObjectFragment retryObject;
    private User user;

    public Announcements() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new Gson().fromJson(PreferenceStorage.getUserJson(getContext()), User.class);
    }

    @Override
    public void onReloaded(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        loadContents(getContext(), ConstantInformation.ANNOUNCEMENT_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        retryObject = RetryObjectFragment.getInstance(view);
        retryObject.setListener(this);


        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new AnnouncementItemAdapter(getContext(), items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (items.size() < 1) {

            loadContents(getContext(), ConstantInformation.ANNOUNCEMENT_URL);

        } else {
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

    public void loadContents(final Context context, String url) {

        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("university", user.getUniversity());
        params.put("college", user.getCollege());

        httpClient.post(context, url, params, new TextHttpResponseHandler() {

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
                Log.e("message", responseString);
                retryObject.getMessage().setText("Could not connect!!");
                retryObject.showMessage();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                retryObject.hideProgress();


                if (responseString.length() < 8) {
                    retryObject.getMessage().setText("No content");
                    retryObject.showMessage();
                    retryObject.showName();
                } else {

                    try {
                        Type listType = new TypeToken<List<AnnouncementDataObject>>() {
                        }.getType();
                        items = new Gson().fromJson(responseString, listType);
                        //PreferenceStorage.addCategoriesJson(getContext(),responseString);

                    } catch (Exception ex) {
                        Log.e("error message", "caught");
                    }

                    adapter = new AnnouncementItemAdapter(getContext(), items);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }
        });

    }

}

