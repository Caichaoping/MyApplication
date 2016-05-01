package com.cc.myapplication.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cc.myapplication.R;
import com.cc.myapplication.dialog.LightProgressDialog;
import com.cc.myapplication.utils.FileUtil;
import com.cc.myapplication.utils.ImageUtils;
import com.cc.myapplication.utils.Logger;
import com.cc.myapplication.utils.StringUtils;
import com.cc.myapplication.utils.ToastUtil;
import com.cc.myapplication.widget.ActionSheet;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 注释：个人信息
 * 作者：菠菜 on 2016/5/1 15:48
 * 邮箱：971859818@qq.com
 */
public class MyInfoDetailActivity extends AppCompatActivity {

    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/CCP/Git/Portrait/";


    @Bind(R.id.profile_image)
    CircleImageView profileImage;
    @Bind(R.id.btn_chose)
    Button btnChose;

    public static final int LOCAL_PIC_CODE = 0;
    public static final int PHOTO_CODE = 1;

    private final static int CROP = 400;
    private Uri origUri;
    private Uri cropUri;

    private Bitmap protraitBitmap;  // 裁剪的头像
    private File protraitFile;  // 裁剪后头像的文件
    private String protraitPath; // 裁剪后头像的绝对路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.profile_image, R.id.btn_chose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
            case R.id.btn_chose:
                CharSequence[] items = {"本地相册","拍照"};
                imageChooseItem(items);
                break;
        }
    }

    private void imageChooseItem(CharSequence[] items) {
        setTheme(R.style.ActionSheetStyleIOS7);
        ActionSheet.createBuilder(getApplicationContext(), getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("本地相册","拍照")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet,
                                                   int index) {
                        switch (index) {
                            case LOCAL_PIC_CODE: // 本地相册.
                                Logger.d("本地相册");
                                startImagePick();
                                break;
                            case PHOTO_CODE: // 照相.
                                Logger.d("拍照");
                                startActionCamera();
                                break;
                        }
                    }

                    @Override
                    public void onDismiss(ActionSheet actionSheet,
                                          boolean isCancel) {
                    }
                }).show();
    }

    /**
     * 选择相册中的图片
     */
    private void startImagePick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"),
                ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
    }

    /**
     * 相机拍照
     */
    private void startActionCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    // 拍照保存的绝对路径
    private Uri getCameraTempFile() {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            ToastUtil.showToastShort(MyInfoDetailActivity.this,"无法保存上传的头像，请检查SD卡是否挂载");
            return null;
        }
        String timeStamp = System.currentTimeMillis() + "";
        // 照片命名
        String cropFileName = "osc_camera_" + timeStamp + ".jpeg";
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);
        cropUri = Uri.fromFile(protraitFile);
        this.origUri = this.cropUri;
        return this.cropUri;
    }

    // 裁剪头像的绝对路径
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            ToastUtil.showToastShort(MyInfoDetailActivity.this, "无法保存上传的头像，请检查SD卡是否挂载");
            return null;
        }
        String timeStamp = System.currentTimeMillis() + "";
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(this, uri);
        }
        String ext = FileUtil.getFileFormat(thePath);
        ext = StringUtils.isEmpty(ext) ? "jpeg" : ext;
        // 照片命名
        String cropFileName = "osc_crop_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);

        cropUri = Uri.fromFile(protraitFile);
        return this.cropUri;
    }

    /**
     * 拍照后裁剪
     *
     * @param data 原始图片
     *             裁剪后图片
     */
    private void startActionCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// 输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                Logger.d("拍照后裁剪");
                startActionCrop(origUri);// 拍照后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                Logger.d("拍照后裁剪");
                startActionCrop(data.getData());// 选图后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                Logger.d("上传照片");
                uploadNewPhoto();// 上传新照片
                break;
        }
    }

    private void uploadNewPhoto() {
        final AlertDialog loading = LightProgressDialog.create(this, "正在上传头像...");
        loading.setCanceledOnTouchOutside(false);
        //loading.show();
        profileImage.setImageBitmap(ImageUtils.getBitmapByFile(protraitFile));
//        try {
//           GitOSCApi.upLoadFile(protraitFile, new HttpCallback() {
//                @Override
//                public void onSuccess(Map<String, String> headers, byte[] t) {
//                    super.onSuccess(headers, t);
//                    UpLoadFile upLoadFile = JsonUtils.toBean(UpLoadFile.class, t);
//                    if (upLoadFile != null && upLoadFile.isSuccess()) {
//                        final String protraitUrl = upLoadFile.getFiles().get(0).getUrl();
//                        GitOSCApi.updateUserProtrait(protraitUrl, new HttpCallback() {
//                            @Override
//                            public void onSuccess(Map<String, String> headers, byte[] t) {
//                                super.onSuccess(headers, t);
//                                UIHelper.toastMessage(MyInfoDetailActivity.this, "头像已更新");
//                                ivPortrait.setImageBitmap(protraitBitmap);
//                                AppContext.getInstance().setProperty(Contanst
//                                        .PROP_KEY_NEWPORTRAIT, protraitUrl);
//                                BroadcastController.sendUserChangeBroadcase(MyInfoDetailActivity
//                                        .this);
//                            }
//
//                            @Override
//                            public void onFailure(int errorNo, String strMsg) {
//                                super.onFailure(errorNo, strMsg);
//                                UIHelper.toastMessage(MyInfoDetailActivity.this, errorNo +
//                                        "更新头像失败");
//                            }
//
//                            @Override
//                            public void onFinish() {
//                                super.onFinish();
//                                loading.dismiss();
//                            }
//
//                            @Override
//                            public void onPreStart() {
//                                super.onPreStart();
//                                loading.setMessage("正在更新头像...");
//                            }
//                        });
//                    } else {
//                        UIHelper.toastMessage(MyInfoDetailActivity.this, "头像上传失败");
//                    }
//                }
//
//                @Override
//                public void onFailure(int errorNo, String strMsg) {
//                    super.onFailure(errorNo, strMsg);
//                    UIHelper.toastMessage(MyInfoDetailActivity.this, "上传图片失败, 网络错误");
//                }
//
//                @Override
//                public void onPreStart() {
//                    super.onPreStart();
//                    loading.show();
//                }
//
//                @Override
//                public void onFinish() {
//                    super.onFinish();
//                    loading.dismiss();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
