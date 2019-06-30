package com.wang.demo4any.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.ScriptIntrinsicConvolve5x5;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wang.demo4any.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RenderFragment extends Fragment {

    private static final String TAG = "RenderFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_render_script, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView  = view.findViewById(R.id.iv_render_script);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap inputBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flower1, options);

//        imageView.setImageBitmap(blurBitmap(this, inputBitmap));
        imageView.setImageBitmap(convolveBitmap(getActivity(), inputBitmap));
    }

    public static Bitmap blurBitmap(Context context, Bitmap source) {
        long start = SystemClock.currentThreadTimeMillis();
        Bitmap bitmapIn = source;
        Bitmap bitmapOut = Bitmap.createBitmap(bitmapIn.getWidth(), bitmapIn.getHeight(), bitmapIn.getConfig());

        RenderScript renderScript = RenderScript.create(context.getApplicationContext());

        Allocation allocationIn = Allocation.createFromBitmap(renderScript, bitmapIn);
        Allocation allocationOut = Allocation.createFromBitmap(renderScript, bitmapOut);

        ScriptIntrinsicBlur scriptBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptBlur.setRadius(25.0f); // // 0 < r <= 25
        scriptBlur.setInput(allocationIn);
        scriptBlur.forEach(allocationOut);

        allocationOut.copyTo(bitmapOut);
        long end = SystemClock.currentThreadTimeMillis();
        Log.d(TAG, "blurBitmap: deltaTime = " + ((end - start))); // 38ms
        return bitmapOut;
    }

    public static Bitmap convolveBitmap(Context context, Bitmap source) {
        long start = SystemClock.currentThreadTimeMillis();
        Bitmap bitmapIn = source;
        Bitmap bitmapOut = Bitmap.createBitmap(bitmapIn.getWidth(), bitmapIn.getHeight(), bitmapIn.getConfig());

        RenderScript renderScript = RenderScript.create(context.getApplicationContext());

        Allocation allocationIn = Allocation.createFromBitmap(renderScript, bitmapIn);
        Allocation allocationOut = Allocation.createFromBitmap(renderScript, bitmapOut);

        ScriptIntrinsicConvolve5x5 scriptConvolve = ScriptIntrinsicConvolve5x5.create(renderScript, Element.U8_4(renderScript));
        float f1 = 0.5f;
        float f2 = 1.0f - f1;

        // Emboss filter kernel
        float coefficients[] = {-f1 * 2, 0, -f1, 0, 0, 0, -f2 * 2, -f2, 0,
                0, -f1, -f2, 1, f2, f1, 0, 0, f2, f2 * 2, 0, 0, 0, f1, 0,
                f1 * 2,};
        // Set kernel parameter
        scriptConvolve.setCoefficients(coefficients);

        // Invoke filter kernel
        scriptConvolve.setInput(allocationIn);
        scriptConvolve.forEach(allocationOut);

        allocationOut.copyTo(bitmapOut);
        long end = SystemClock.currentThreadTimeMillis();
        Log.d(TAG, "convolveBitmap: deltaTime = " + ((end - start))); // 38ms
        return bitmapOut;
    }
}
