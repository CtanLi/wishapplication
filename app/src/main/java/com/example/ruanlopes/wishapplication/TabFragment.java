package com.example.ruanlopes.wishapplication;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruanlopes.wishapplication.Adapters.ViewPagerAdapter;
import com.example.ruanlopes.wishapplication.Fragments.FeedRecyclerViewFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);


        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new FeedRecyclerViewFragment().createInstance(20), "Story");
        adapter.addFragment(new FeedRecyclerViewFragment().createInstance(20), "Story");
        adapter.addFragment(new FeedRecyclerViewFragment().createInstance(20), "Story");

        viewPager.setAdapter(adapter);
    }


}