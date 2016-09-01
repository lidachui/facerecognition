package com.work.leeds.facerecognition.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.work.leeds.facerecognition.MyApplication;
import com.work.leeds.facerecognition.R;
import com.work.leeds.facerecognition.bean.Apartment;
import com.work.leeds.facerecognition.bean.User;
import com.work.leeds.facerecognition.callback.onBackClickedListener;
import com.work.leeds.facerecognition.sqlite.DatabaseOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leeds on 2016/8/29.
 * 添加fragment
 */
public class AddFragment extends Fragment implements View.OnClickListener {

    LinearLayout mLinearLayout;
    Context mContext;

    private EditText mNameText;
    private EditText mNumberText;
    private TextView mAprtmentText;
    private TextView mPictureText;
    private ImageView mChooseApartBtn;
    private ImageView mChoosePictureBtn;
    private Button mAddBtn;
    private ImageView mBackBtn;


    private List<Apartment> ApartList = new ArrayList<>();
    private String[] aparts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        //查询部门信息 并保存
        ApartList = getApartInfo();
        aparts = new String[ApartList.size()];
        for (int i = 0; i < ApartList.size(); i++) {
            aparts[i] = ApartList.get(i).getApartmentName();
        }

    }

    private List<Apartment> getApartInfo() {
        List<Apartment> mList = new ArrayList<>();
        //查询Apartment表中的所有数据
        SQLiteDatabase db = MyApplication.myDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query("Apartment", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //遍历cursor对象
                int apartnumber = cursor.getInt(cursor.getColumnIndex("apartid"));
                String apartname = cursor.getString(cursor.getColumnIndex("apartname"));
                Apartment apart = new Apartment(apartnumber, apartname);
                mList.add(apart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.add_fragment_layout, container, false);
        initView();
        initEvent();
        return mLinearLayout;
    }

    private void initEvent() {
        mChooseApartBtn.setOnClickListener(this);
        mChoosePictureBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }


    private void initView() {
        mBackBtn = (ImageView) findViewById(R.id.id_add_back_btn);
        mNameText = (EditText) findViewById(R.id.id_name_text);
        mNumberText = (EditText) findViewById(R.id.id_job_number_text);
        mAprtmentText = (TextView) findViewById(R.id.id_apartment_text);
        mChooseApartBtn = (ImageView) findViewById(R.id.id_apartment_btn);
        mPictureText = (TextView) findViewById(R.id.id_picture_text);
        mChoosePictureBtn = (ImageView) findViewById(R.id.id_picture_btn);
        mAddBtn = (Button) findViewById(R.id.id_add_user_btn);
    }

    private View findViewById(int viewId) {
        return mLinearLayout.findViewById(viewId);
    }

    public static final int CHOOSE_PHOTO = 3;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_add_user_btn:
                //添加用户信息
                if (isInfoComplete()) {
                    AddUserInfo();
                }
                break;
            case R.id.id_apartment_btn:
                //创建dialog选取部门
                new AlertDialog.Builder(mContext)
                        .setTitle("请选择部门")
                        .setItems(aparts, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String select_item = aparts[which].toString();
                                mAprtmentText.setText(select_item);
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.id_add_back_btn:
                //TODO 返回主界面
                if (getActivity() instanceof onBackClickedListener) {
                    ((onBackClickedListener) getActivity()).onBackClicked();
                }
                break;
            case R.id.id_picture_btn:
                //选取图片
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
                break;
        }
    }

    /**
     * 添加用户信息
     */
    private void AddUserInfo() {
        User user = new User();
        user.setName(mNameText.getText().toString().trim());
        user.setId(mNumberText.getText().toString().trim());
        user.setImageUri(mPictureText.getText().toString().trim());
        user.setApartId(DatabaseOperation.queryApartIdByName(mAprtmentText.getText().toString().trim()));
        //将数据添加到数据库
        if (DatabaseOperation.queryUserByName(user.getId())) {
            Toast.makeText(mContext, "User has exsited", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseOperation.UploadUserData(user);
            Toast.makeText(mContext, "Add user succeed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 信息填写是否完整
     *
     * @return
     */
    private boolean isInfoComplete() {
        if (mNameText.getText().toString().equals("")
                || mNumberText.getText().toString().equals("")
                || mAprtmentText.getText().toString().equals("")
                || mPictureText.getText().toString().equals("")) {
            Toast.makeText(mContext, "有内容为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mNumberText.getText().toString().trim().length() != 8) {
            Toast.makeText(mContext, "工号长度为8", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                //4.4及以上系统处理图片
                handleImageOnKitKat(data);
                break;
        }
    }

    /**
     * 处理选择图片
     *
     * @param data
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(mContext, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }
        //将路径显示出来
        mPictureText.setText(imagePath);

    }

    /**
     * 获取图片真实路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = mContext.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //TODO 目前用的是绝对路径，以后迁移到远程数据库的时候改为将图片上传至数据库
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            }
            cursor.close();
        }
        return path;
    }


}
