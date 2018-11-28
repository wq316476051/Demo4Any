package com.wang.unittest;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = "MainActivityTest";

    public MainActivityTest() {
        super(MainActivity.class);
        // 此处启动  MainActivity
        /* 原理 in InstrumentationTestCase # launchActivityWithIntent()
        T activity = (T) getInstrumentation().startActivitySync(intent);
        getInstrumentation().waitForIdleSync();
        */
    }

    @Override
    public void setUp() throws Exception {
        Log.d(TAG, "setUp: threadName = " + Thread.currentThread().getName()); // Instr: android.support.test.runner.AndroidJUnitRunner
        Log.d(TAG, "setUp: getActivity() = " + getActivity());
    }

    @Override
    public void tearDown() throws Exception {
    }

    private static final String TEST_TEXT = "test_text";
    public void testSubmit() throws Throwable {
        assertNotNull(getActivity());

        TextView tvName = getActivity().findViewById(R.id.tv_name);
        assertNotNull(tvName);

        tvName.setText(TEST_TEXT);
        assertEquals(TEST_TEXT, tvName.getText());

        final Button btnClick = getActivity().findViewById(R.id.btn_click);
        assertNotNull(btnClick);

        // 发送到主线程中执行
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnClick.performClick();
            }
        });

        // 等待跳转目标 Activity 的启动
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SecondActivity.class.getName(), null, false);
        SecondActivity secondActivity = (SecondActivity) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(secondActivity);

        TextView tvSecondName = secondActivity.findViewById(R.id.tv_second_name);
        assertNotNull(tvSecondName);

        assertEquals(TEST_TEXT, tvSecondName.getText().toString());
    }
}