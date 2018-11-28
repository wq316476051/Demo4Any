package com.wang.view.listview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
public class ListViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoadMoreListView listView = new LoadMoreListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData()));
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
        });
        setContentView(listView);


        TextView emptyView = new TextView(this);
        emptyView.setTextColor(Color.RED);
        emptyView.setText("Nothing");

//        ((ViewGroup) listView.getParent()).addView(emptyView);
//        listView.setEmptyView(emptyView);

        listView.setLoadMoreView(emptyView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.clearChoices();
    }

    private List<String> datas;
    private List<String> getData() {
        if (datas == null) {
            datas = new ArrayList<>();
            datas.add("Android");
            datas.add("Java");
            datas.add("Go");
            datas.add("Android");
            datas.add("Java");
            datas.add("Go");
            datas.add("Android");
            datas.add("Java");
            datas.add("Go");
            datas.add("Android");
            datas.add("Java");
            datas.add("Go");
            datas.add("Android");
            datas.add("Java");
            datas.add("Go");
            datas.add("Android");
            datas.add("Java");
            datas.add("Go");
            datas.add("Android");
            datas.add("Java");
            datas.add("Go");
            datas.add("Android");
            datas.add("Java");
            datas.add("Go");
        }
        return datas;
    }
}
