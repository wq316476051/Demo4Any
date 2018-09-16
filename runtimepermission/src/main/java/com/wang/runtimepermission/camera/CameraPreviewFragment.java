package com.wang.runtimepermission.camera;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wang.runtimepermission.R;

public class CameraPreviewFragment extends Fragment {

    private static final String TAG = "CameraPreviewFragment";
    /**
     * Id of the camera to access. 0 is the first camera.
     */
    private static final int CAMERA_ID = 0;
    private Camera mCamera;
    private CameraPreview mPreview;

    public static CameraPreviewFragment newInstance() {
        return new CameraPreviewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCamera = getCameraInstance(CAMERA_ID);
        Camera.CameraInfo cameraInfo = null;
        if (mCamera != null) {
            cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(CAMERA_ID, cameraInfo);
        }

        if (mCamera == null || cameraInfo == null) {
            Toast.makeText(getActivity(), "Camera is not available.", Toast.LENGTH_SHORT).show();
            return inflater.inflate(R.layout.fragment_camera_unavailable, null);
        }

        View root = inflater.inflate(R.layout.fragment_camera, null);

        int displayRotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        mPreview = new CameraPreview(getActivity(), mCamera, cameraInfo, displayRotation);
        FrameLayout preview = (FrameLayout) root.findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    private Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId);
        } catch (Exception e) {
            Log.d(TAG, "Camera " + cameraId + " is not available: " + e.getMessage());
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
