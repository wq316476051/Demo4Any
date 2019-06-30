package com.wang.demo4any;

import com.wang.demo4any.fragments.AccelerometerPlayFragment;
import com.wang.demo4any.fragments.AnimateFragment;
import com.wang.demo4any.fragments.HelpFragment;
import com.wang.demo4any.fragments.JetpackFragment;
import com.wang.demo4any.fragments.NoteFragment;
import com.wang.demo4any.fragments.NotificationFragment;
import com.wang.demo4any.fragments.RecordListFragment;
import com.wang.demo4any.fragments.ShareFragment;
import com.wang.demo4any.fragments.StorageFragment;
import com.wang.demo4any.fragments.VoiceRecognitionFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import androidx.fragment.app.Fragment;

public class Fragments {

    private static final List<NavigationItem> mFunctionFragments = new ArrayList<>();

    private static final List<NavigationItem> mTestFragments = new ArrayList<>();

    static {
        mFunctionFragments.add(new NavigationItem(RecordListFragment.TITLE, RecordListFragment::newInstance));
        mFunctionFragments.add(new NavigationItem(AccelerometerPlayFragment.TITLE, AccelerometerPlayFragment::newInstance));

        mTestFragments.add(new NavigationItem(HelpFragment.TITLE, HelpFragment::newInstance));
        mTestFragments.add(new NavigationItem(JetpackFragment.TITLE, JetpackFragment::newInstance));
        mTestFragments.add(new NavigationItem(ShareFragment.TITLE, ShareFragment::newInstance));
        mTestFragments.add(new NavigationItem(NoteFragment.TITLE, NoteFragment::newInstance));
        mTestFragments.add(new NavigationItem(NotificationFragment.TITLE, NotificationFragment::newInstance));
        mTestFragments.add(new NavigationItem(AnimateFragment.TITLE, AnimateFragment::newInstance));
        mTestFragments.add(new NavigationItem(StorageFragment.TITLE, StorageFragment::newInstance));
        mTestFragments.add(new NavigationItem(VoiceRecognitionFragment.TITLE, VoiceRecognitionFragment::newInstance));
    }

    private Fragments() {
    }

    public static List<NavigationItem> getFunctionFragments() {
        return mFunctionFragments;
    }

    public static List<NavigationItem> getTestFragments() {
        return mTestFragments;
    }

    public static class NavigationItem {
        public String title;
        public Supplier<Fragment> fragment;

        public NavigationItem(String title, Supplier<Fragment> fragment) {
            this.title = title;
            this.fragment = fragment;
        }
    }
}
