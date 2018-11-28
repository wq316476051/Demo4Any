package com.wang.unittest;

import android.util.Log;
import android.widget.Button;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.UiDevice;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

public class UiActivityTest {

    private static final String TAG = "UiActivityTest";

    private UiDevice mUiDevice;

    @Before
    public void setUp() throws UiObjectNotFoundException {
        Log.d(TAG, "setUp: ");
        // Initialize UiDevice instance
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @After
    public void tearDown() throws UiObjectNotFoundException {
        Log.d(TAG, "tearDown: ");
    }

    @Test
    public void testChangeText() throws UiObjectNotFoundException {
        Log.d(TAG, "testChangeText: ");
        UiObject okButton = mUiDevice.findObject(new UiSelector().className(Button.class).text("OK"));
        Log.d(TAG, "testChangeText: okButton = " + okButton);
        Assert.assertTrue("Button exist", okButton.exists());
        if(okButton.exists() && okButton.isEnabled()) {
            Log.d(TAG, "testChangeText: ");
            okButton.click();
        }
    }
}