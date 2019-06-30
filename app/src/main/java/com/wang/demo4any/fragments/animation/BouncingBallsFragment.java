package com.wang.demo4any.fragments.animation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.IntProperty;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class BouncingBallsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new MyAnimateView(getActivity());
    }

    private class MyAnimateView extends View {

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

        public MyAnimateView(Context context) {
            super(context);
            ObjectAnimator animator = ObjectAnimator.ofInt(this, BACKGROUND_COLOR, RED, BLUE);
            animator.setDuration(300);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.setRepeatMode(ObjectAnimator.RESTART);
            animator.setEvaluator(new ArgbEvaluator());
            animator.start();
        }


    }
}
