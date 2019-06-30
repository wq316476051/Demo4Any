package com.wang.demo4any.fragments.navigation;

import com.wang.demo4any.Fragments;

import java.util.List;

public class TestFragment extends NavigationFragment {

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    protected List<Fragments.NavigationItem> getNavigationFragments() {
        return Fragments.getTestFragments();
    }
}
