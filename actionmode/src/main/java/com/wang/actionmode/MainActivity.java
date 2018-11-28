package com.wang.actionmode;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private ActionMode mActionMode;
    private boolean mAddMoreMenu;

    private Button mBtnActionModeInvalidate;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mBtnActionModeInvalidate = findViewById(R.id.btn_invalidate);
        mBtnActionModeInvalidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionMode != null) {
                    mAddMoreMenu = true;
                    mActionMode.invalidate();
                }
            }
        });
        mListView = findViewById(R.id.list_view);
        mListView.setOnItemLongClickListener(mItemLongClickListener);

        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] {
                "item -1", "item-2"
        }));
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        Log.d(TAG, "onActionModeStarted: ");
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        Log.d(TAG, "onActionModeFinished: ");
        mActionMode = null;
    }

    private AdapterView.OnItemLongClickListener mItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemLongClick: ");
            if (mActionMode != null) {
                return false;
            }
            mActionMode = startActionMode(mActionModeCallback);
            view.setSelected(true);
            return true;
        }
    };

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Log.d(TAG, "onCreateActionMode: ");
            menu.add(0, 123, 0, "titile");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            Log.d(TAG, "onPrepareActionMode: ");
            if (mAddMoreMenu) {
                menu.add(0, 456, 0, "more");
                return true;
            }
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Log.d(TAG, "onActionItemClicked: item = " + item);
            switch (item.getItemId()) {
                case 123:
                    Toast.makeText(MainActivity.this, "menu clicked", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            Log.d(TAG, "onDestroyActionMode: ");
        }
    };

}
