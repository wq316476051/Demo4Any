package com.wang.demo4any.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.wang.demo4any.R;
import com.wang.demo4any.fragments.record.PlaybackFragment;
import com.wang.demo4any.fragments.record.RecordFragment;
import com.wang.demo4any.record.RecordViewModel;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class RecordListFragment extends Fragment {

    private static final String TAG = "RecordListFragment";

    public static final String TITLE = "Record";

    private Button mBtnStartRecord;

    private ListView mListView;

    private RecordViewModel mRecordViewModel;

    private RecordListAdapter mRecordListAdapter;

    public static Fragment newInstance() {
        return new RecordListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_list, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 1. views
        mBtnStartRecord = view.findViewById(R.id.btn_start_record);
        mListView = view.findViewById(R.id.list_view);

        // 2. data
        mRecordListAdapter = new RecordListAdapter();
        mListView.setAdapter(mRecordListAdapter);

        // 3. listeners
        mBtnStartRecord.setOnClickListener(button -> {
            FragmentActivity activity = getActivity();
            if (activity == null) {
                return;
            }
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecordFragment.newInstance())
                    .addToBackStack("record")
                    .commit();
        });

        mListView.setOnItemClickListener((parent, listView, position, id) -> {
            File record = mRecordListAdapter.getRecord(position);
            if (record == null) {
                return;
            }
            FragmentActivity activity = getActivity();
            if (activity == null) {
                return;
            }
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlaybackFragment.newInstance())
                    .addToBackStack("playback")
                    .commit();
        });

        mRecordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        mRecordViewModel.loadRecords(true);
        mRecordViewModel.getRecords().observe(this, records -> {
            mRecordListAdapter.setRecords(records);
            mRecordListAdapter.notifyDataSetChanged();
        });
    }

    private class RecordListAdapter extends BaseAdapter {

        private List<File> mRecords;

        public void setRecords(List<File> records) {
            mRecords = records;
        }

        public File getRecord(int position) {
            return mRecords != null && position >= 0 && position < mRecords.size() ? mRecords.get(position) : null;
        }

        @Override
        public int getCount() {
            return mRecords != null ? mRecords.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mRecords != null ? mRecords.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
