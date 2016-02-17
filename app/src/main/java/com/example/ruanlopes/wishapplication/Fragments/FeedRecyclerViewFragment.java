package com.example.ruanlopes.wishapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruanlopes.wishapplication.Adapters.RecyclerAdapter;
import com.example.ruanlopes.wishapplication.Model.Person;
import com.example.ruanlopes.wishapplication.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedRecyclerViewFragment extends Fragment {


    public final static String ITEMS_COUNT_KEY = "FeedRecyclerViewFragment$ItemsCount";

    private List<Person> mPersons;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public FeedRecyclerViewFragment() {
        // Required empty public constructor
    }


    public static FeedRecyclerViewFragment createInstance(int itemsCount) {

        FeedRecyclerViewFragment feedRecyclerViewFragment = new FeedRecyclerViewFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        feedRecyclerViewFragment.setArguments(bundle);

        return feedRecyclerViewFragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        setupRecyclerView(view);

    }


    private List<Person> createData() {


        Bundle bundle = getArguments();


        if (bundle != null) {

            mPersons = new ArrayList<>();

            int itemsCount = bundle.getInt(ITEMS_COUNT_KEY);

            for (int i = 0; i < itemsCount; i++) {

                mPersons.add(new Person("Emma Wilson", "23 years old", R.drawable.ic_launcher));
            }
        }

        return mPersons;
    }

    private void setupRecyclerView(View view) {

        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), createData());
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(recyclerAdapter);

    }

}
