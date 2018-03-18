package com.example.murodjonrahimov.wecare.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.murodjonrahimov.wecare.PatientPostForm;
import com.example.murodjonrahimov.wecare.R;

public class PatientMyPostFragment extends Fragment {


    public PatientMyPostFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.p_fragment_posts, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PatientPostForm.class);
                startActivity(intent);
            }
        });
    }
}
