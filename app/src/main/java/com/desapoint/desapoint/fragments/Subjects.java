package com.desapoint.desapoint.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.SubjectItemAdapter;
import com.desapoint.desapoint.pojos.Subject;

import java.util.ArrayList;
import java.util.List;


public class Subjects extends Fragment {

    private RecyclerView recyclerView;
    private SubjectItemAdapter adapter;
    private List<Subject> subjects=new ArrayList<>();

    public Subjects() {
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
                    subject.setCode("ITU");
                    subject.setTitle("Information technology");
                }else{
                    subject.setCode("CSU");
                    subject.setTitle("Communication Skills");
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

}
