package com.wang.audio;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.wang.audio.player.PlaybackActivity;
import com.wang.audio.recorder.RecordActivity;
import com.wang.audio.test.AudioTrackActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private MainBinding mBinding;
    private RecordFileAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.btnRecord.setOnClickListener(mOnClickListener);

        mBinding.btnPlay.setOnClickListener(mOnClickListener);

//        mBinding.btnTestRecord.setOnClickListener(view -> {
//            startActivityInternal(AudioTrackActivity.class);
//        });

        mAdapter = new RecordFileAdapter();
        if (Permissions.isStorage(this)) {
//            File[] files = Storage.getInstance().getDirectory().listFiles();
//            List<RecordFile> list = Arrays.stream(files).map(RecordFile::new).collect(Collectors.toList());
//            mAdapter.setData(list);
        } else {
            Permissions.requestStorage(this, 101);
        }

        mBinding.listView.setAdapter(mAdapter);
        mBinding.listView.setOnItemClickListener((parent, view, position, id) -> {
//            RecordFile recordFile = mAdapter.mFiles.get(position);
//            recordFile.sync();
//            mAdapter.notifyDataSetChanged();
        });
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (view == mBinding.btnRecord) {
                startActivityInternal(RecordActivity.class);
            } else if (view == mBinding.btnPlay) {
                startActivityInternal(PlaybackActivity.class);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0])) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    File[] files = Storage.getInstance().getDirectory().listFiles();
//                    List<RecordFile> list = Arrays.stream(files).map(RecordFile::new).collect(Collectors.toList());
//                    mAdapter.setData(list);
//                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private static class RecordFileAdapter extends BaseAdapter {
        private List<RecordFile> mFiles;
        public void setData(List<RecordFile> files) {
            mFiles = files;
        }
        @Override
        public int getCount() {
            return mFiles == null ? 0 : mFiles.size();
        }

        @Override
        public RecordFile getItem(int position) {
            return mFiles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
                convertView.setTag(holder = new ViewHolder());
                holder.tvName = convertView.findViewById(R.id.tv_name);
                holder.tvCreateTime = convertView.findViewById(R.id.tv_create_time);
                holder.btnMore = convertView.findViewById(R.id.btn_more);
            } else {
                Object tag = convertView.getTag();
                if (tag instanceof ViewHolder) {
                    holder = (ViewHolder) tag;
                } else {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
                    convertView.setTag(holder = new ViewHolder());
                    holder.tvName = convertView.findViewById(R.id.tv_name);
                    holder.tvCreateTime = convertView.findViewById(R.id.tv_create_time);
                }
            }
            holder.tvName.setText(getItem(position).mFile.getName());

            RecordFile.Extra extra = getItem(position).getExtra();
            if (extra != null && extra.createTimeBox != null && extra.createTimeBox.createTime > 0) {
                String format = new SimpleDateFormat("yyyyMMdd-HHmmss").format(extra.createTimeBox.createTime);
                holder.tvCreateTime.setText(format);
            }
            holder.btnMore.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(parent.getContext(), view);
                popupMenu.getMenu().add(0, 0, 0, "add tail");
                popupMenu.getMenu().add(0, 1, 0, "get tail");
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case 0:
                            getItem(position).writeAtTail(getItem(position).mFile);
                            break;
                        case 1:
                            break;
                    }
                    return true;
                });
                popupMenu.show();
            });
            return convertView;
        }

        private class ViewHolder {
            TextView tvName;
            TextView tvCreateTime;
            Button btnMore;
        }
    }

    private void startActivityInternal(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
