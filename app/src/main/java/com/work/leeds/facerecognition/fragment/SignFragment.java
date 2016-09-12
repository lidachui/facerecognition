package com.work.leeds.facerecognition.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.work.leeds.facerecognition.R;
import com.work.leeds.facerecognition.callback.FaceCompareCallback;
import com.work.leeds.facerecognition.callback.TakePicCallback;
import com.work.leeds.facerecognition.callback.onBackClickedListener;
import com.work.leeds.facerecognition.internet.GetDegreeOfSimilarity;
import com.work.leeds.facerecognition.uimanager.InfoViewManager;
import com.work.leeds.facerecognition.util.BitmapUtil;

import java.util.List;

/**
 * Created by leeds on 2016/9/1.
 * 签到类
 */
public class SignFragment extends Fragment implements FaceCompareCallback {

    private Context mContext;

    private ImageView mBackBtn;

    private SurfaceView mSurfaceView;
    private SurfaceHolder surfaceHolder;
    boolean previewing;

    public Camera mCamera;
    int mCurrentCamIndex = 0;

    LinearLayout mInfoLinearLayout;
    InfoViewManager mInfoViewManager;
    Bitmap mTestBitmap = null;
    Bitmap mUserBitmap = null;
    private ProgressDialog progressDialog;
    GetDegreeOfSimilarity gos;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();
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

    private void initView(View view) {

        //返回按钮
        mBackBtn = (ImageView) view.findViewById(R.id.id_add_back_btn);

        //初始化SurfaceView
        mSurfaceView = (SurfaceView) view.findViewById(R.id.id_surfaceview);
        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceViewCallback());
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //初始化信息栏
        mInfoLinearLayout = (LinearLayout) view.findViewById(R.id.id_info_layout);
        mInfoViewManager = new InfoViewManager(mContext, mInfoLinearLayout);

        //初始化dialog
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("正在处理数据中");
        progressDialog.setMessage("请稍后。。");

        //初始化GetDegreeeOfSimilarity
        gos = new GetDegreeOfSimilarity(this);

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
            mTestBitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
            previewing = false;
        }
    };

    private void initEvent() {

        //返回按钮
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof onBackClickedListener) {
                    ((onBackClickedListener) getActivity()).onBackClicked();
                }
            }
        });

        //拍照和重置
        mInfoViewManager.setmTakePicCalllback(new TakePicCallback() {
            @Override
            public void takePhoto() {
                if (previewing) {
                    mCamera.takePicture(shutterCallback, rawPictureCallback,
                            jpegPictureCallback);

                }
            }

            @Override
            public void resetCamera() {
                if (!previewing) {
                    mCamera.startPreview();
                    previewing = true;
                    mTestBitmap = null;
                }
            }

            @Override
            public void comparePic() {
                if (mInfoViewManager.getmUser() == null) {
                    Toast.makeText(mContext, "用户信息尚未获取", Toast.LENGTH_SHORT).show();
                } else if (mTestBitmap == null) {
                    Toast.makeText(mContext, "尚未拍照", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("imageUrl------>" + mInfoViewManager.getmUser().getImageUri());
                    //todo 根据对应位置加载本机的图片，因为目前本地sqlite中只存了图片在内存中的位置
                    mUserBitmap = BitmapFactory.decodeFile(mInfoViewManager.getmUser().getImageUri());
                    if (mUserBitmap != null && mTestBitmap != null) {
                        //转成Base64编码
                        String testBase64 = BitmapUtil.bitmaptoString(mTestBitmap);
                        String userBase64 = BitmapUtil.bitmaptoString(mUserBitmap);
                        //TODO 开始图片对比
                        compareFace(testBase64, userBase64);
                        //TODO 显示progessDialog
                        showDialog();
                    }
                }
            }
        });
    }


    @Override
    public void compareFace(String face1, String face2) {
        gos.getDegreeOfSimilarity(face1, face2);
    }

    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void signSucceed() {
        //TODO 提示签到成功
        Toast.makeText(mContext, "签到成功", Toast.LENGTH_SHORT).show();
        //TODO 提交数据到数据库
        mInfoViewManager.updateRecord();
        //TODO 修改相关数据
        mInfoViewManager.resetView();
        //TODO 释放相机
        if (!previewing) {
            mCamera.startPreview();
            previewing = true;
            mTestBitmap = null;
        }
    }

    @Override
    public void signFailed() {
        //TODO 提示签到失败
        Toast.makeText(mContext, "签到失败", Toast.LENGTH_SHORT).show();
        //TODO 释放相机
        if (!previewing) {
            mCamera.startPreview();
            previewing = true;
            mTestBitmap = null;
        }
    }

    @Override
    public void signError() {

    }

}
