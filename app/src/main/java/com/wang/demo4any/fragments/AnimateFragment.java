package com.wang.demo4any.fragments;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.IntProperty;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.demo4any.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 1. AnimationDrawable
 * 2. Animation
 * 3. Animator
 * 4. Transition
 * 5. Share Element
 */
public class AnimateFragment extends Fragment {

    private static final String TAG = "AnimateFragment";



    public static final String TITLE = "Animate";
    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    public final IntProperty<View> BACKGROUND_COLOR = new IntProperty<View>("backgroundColor") {
        @Override
        public void setValue(View object, int value) {
            object.setBackgroundColor(value);
        }

        @Override
        public Integer get(View object) {
            Drawable background = object.getBackground();
            return background instanceof ColorDrawable ? ((ColorDrawable)background).getColor() : 0;
        }
    };

    public static Fragment newInstance() {
        return new AnimateFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animate, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObjectAnimator animator = ObjectAnimator.ofInt(view, BACKGROUND_COLOR, RED, BLUE);
        animator.setDuration(3000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }
}
