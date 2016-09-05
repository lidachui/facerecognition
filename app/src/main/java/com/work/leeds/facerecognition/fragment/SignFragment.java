package com.work.leeds.facerecognition.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.work.leeds.facerecognition.R;
import com.work.leeds.facerecognition.callback.onBackClickedListener;

import java.util.List;

/**
 * Created by leeds on 2016/9/1.
 * 签到类
 */
public class SignFragment extends Fragment implements View.OnClickListener {

    Camera mCamera;
    SurfaceView mSurfaceView;
    private Button mButton;
    private ImageView mBackBtn;
    private SurfaceHolder surfaceHolder;
    private ProgressDialog mProgressDialog;
    boolean previewing;
    int mCurrentCamIndex = 0;

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
        }
    };

    Camera.PictureCallback rawPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
        }
    };

    Camera.PictureCallback jpegPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
             //TODO  拿到图像转成bitmap 进行比较

            previewing = false;

        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initEvent();
    }

    private void initEvent() {
        mButton.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    private void initView(View view) {
        //初始化按钮
        mButton=(Button)view.findViewById(R.id.id_take_pic_btn);
        mBackBtn =(ImageView)view.findViewById(R.id.id_add_back_btn);

        //初始化前置摄像头
        mSurfaceView = (SurfaceView) view.findViewById(R.id.id_surfaceview);
        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceViewCallback());
        //surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //todo 初始化提示框,在比对头像的时候显示提示框
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("正在提交，请稍后...");
        mProgressDialog.setTitle("提示");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_take_pic_btn:
                if(previewing){
                    mCamera.takePicture(shutterCallback, rawPictureCallback,
                            jpegPictureCallback);
                    //TODO 进行对比

                }
                break;
            case R.id.id_add_back_btn:
                if (getActivity() instanceof onBackClickedListener) {
                    ((onBackClickedListener) getActivity()).onBackClicked();
                }
                break;
        }
    }


    private class SurfaceViewCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            //打开前置摄像头
            mCamera = openFrontFacingCameraGingerbread();
            Camera.Parameters params = mCamera.getParameters();
            List<String> focusModes = params.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (previewing) {
                mCamera.stopPreview();
                previewing = false;
            }
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
                previewing = true;
                setCameraDisplayOrientation(getActivity(), mCurrentCamIndex, mCamera);
            } catch (Exception e) {
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            previewing = false;
        }
    }

    /**
     * 根据横竖屏自动调节preview方向
     * starting from API 14 this method can be called when preview is active
     *
     * @param activity
     * @param CamIndex
     * @param camera
     */
    private void setCameraDisplayOrientation(Activity activity, int CamIndex, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(CamIndex, info);

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        //degrees  the angle that the picture will be rotated clockwise. Valid values are 0, 90, 180, and 270.
        //The starting position is 0 (landscape).
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 90;
                break;
            case Surface.ROTATION_90:
                degrees = 180;
                break;
            case Surface.ROTATION_180:
                degrees = 270;
                break;
            case Surface.ROTATION_270:
                degrees = 0;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    /**
     * 打开摄像头
     *
     * @return
     */
    private Camera openFrontFacingCameraGingerbread() {

        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        //如果只有一个摄像头 则打开后置，否则打开前置
        if (cameraCount == 1) {
            cam = Camera.open(0);
            mCurrentCamIndex = 0;
        } else {
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    try {
                        cam = Camera.open(camIdx);
                        mCurrentCamIndex = camIdx;
                    } catch (RuntimeException e) {
                        Log.e("TAG", "Camera failed to open: " + e.getLocalizedMessage());
                    }
                }
            }
        }
        return cam;
    }


}
