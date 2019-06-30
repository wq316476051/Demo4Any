package com.wang.demo4any.fragments.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.wang.demo4any.Fragments;
import com.wang.demo4any.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public abstract class NavigationFragment extends DialogFragment {

    private GridView mGridView;
    private NavigationListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = view.findViewById(R.id.grid_view);
        mGridView.setOnItemClickListener((parent, gridView, position, id) -> {
            FragmentActivity activity = getActivity();
            if (activity instanceof NavigationCallback) {
                NavigationCallback callback = (NavigationCallback) activity;
                callback.onNavigationClick(getNavigationFragments().get(position).fragment.get());
            }
        });

        view.findViewById(R.id.navigation_background).setOnClickListener(bg -> {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.getSupportFragmentManager().beginTransaction()
                        .hide(this)
                        .commit();
            }
        });

        mAdapter = new NavigationListAdapter();
        mGridView.setAdapter(mAdapter);
    }

    protected abstract List<Fragments.NavigationItem> getNavigationFragments();

    private class NavigationListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return getNavigationFragments().size();
        }

        @Override
        public Fragments.NavigationItem getItem(int position) {
            return getNavigationFragments().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_navigation, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvNavigation.setText(getItem(position).title);
            return convertView;
        }

        private class ViewHolder {

            private TextView tvNavigation;

            private ViewHolder(View view) {
                tvNavigation = view.findViewById(R.id.tv_navigation);
            }
        }
    }

    public interface NavigationCallback {
        void onNavigationClick(Fragment fragment);
    }
}
