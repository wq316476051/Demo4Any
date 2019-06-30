package com.wang.demo4any.fragments.navigation;

import com.wang.demo4any.Fragments;

import java.util.List;

public class FunctionFragment extends NavigationFragment {

    public static FunctionFragment newInstance() {
        return new FunctionFragment();
    }

    protected List<Fragments.NavigationItem> getNavigationFragments() {
        return Fragments.getFunctionFragments();
    }
}
