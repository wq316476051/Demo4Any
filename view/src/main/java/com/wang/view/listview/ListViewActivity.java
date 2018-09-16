package com.wang.view.listview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView:
 * ListAdapter (Interface)
 * BaseAdapter (基础: 用于自定义扩展)
 *     ArrayAdapter (每条一个字符串的情况)
 *     SimpleAdapter (每条多个%s的情况, { Checkable, TextView, ImageView })
 *     CursorAdapter
 */
public class ListViewActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData()));
        listView.setOnItemClickListener(this);
        setContentView(listView);

        ArrayAdapter arrayAdapter;
        SimpleAdapter simpleAdapter;
        FragmentPagerAdapter fragmentPagerAdapter;
    }

    private List<String> datas;
    private List<String> getData() {
        if (datas == null) {
            datas = new ArrayList<>();
            datas.add("Android");
            datas.add("Java");
            datas.add("Go");
        }
        return datas;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s = getData().get(position);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
