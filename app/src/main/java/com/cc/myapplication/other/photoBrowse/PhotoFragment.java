package com.cc.myapplication.other.photoBrowse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cc.myapplication.R;
import com.cc.myapplication.net.GlideUtils;
import com.cc.myapplication.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 注释：图片浏览 Fragment
 * 作者：菠菜 on 2016/5/1 14:27
 * 邮箱：971859818@qq.com
 */
public class PhotoFragment extends Fragment {

    @Bind(R.id.image)
    PhotoView image;
    @Bind(R.id.loading)
    ProgressBar loading;
    private String imageUrl;

    private PhotoViewAttacher attacher;

    public static PhotoFragment newInstance(String imageUrl) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("imageUrl", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.photo_item, container, false);
        ButterKnife.bind(this, root);
        loadImage();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            imageUrl = args.getString("imageUrl");
        }
    }

    private void loadImage() {

        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {
            if (loading != null) {
                loading.setVisibility(View.VISIBLE);
            }
            GlideUtils.display(this.getActivity(), image, imageUrl, new RequestListener() {
                @Override
                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                    if (loading != null) {
                        loading.setVisibility(View.GONE);
                    }
                    ToastUtil.showToastLong(getActivity(), "加载图片失败");
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                    if (loading != null) {
                        loading.setVisibility(View.GONE);
                    }
                    if (image != null) {
                        attacher = new PhotoViewAttacher(image);
                        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                            @Override
                            public void onPhotoTap(View view, float v, float v2) {
                                getActivity().finish();
                            }
                        });
                        attacher.update();
                    }
                    return false;
                }
            });

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

