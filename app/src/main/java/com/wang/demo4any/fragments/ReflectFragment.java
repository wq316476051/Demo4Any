package com.wang.demo4any.fragments;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.ContentValues.TAG;

public class ReflectFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Method method = null;
        try {
            method = ReflectFragment.class.getDeclaredMethod("test");
            int modifiers = method.getModifiers();
            boolean isStatic = Modifier.isStatic(modifiers);
            boolean isPublic = Modifier.isPublic(modifiers);
            boolean isPrivate = Modifier.isPrivate(modifiers);
            Log.d(TAG, "onCreate: isStatic = " + isStatic);
            Log.d(TAG, "onCreate: isPublic = " + isPublic);
            Log.d(TAG, "onCreate: isPrivate = " + isPrivate);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void test() {

    }
}
