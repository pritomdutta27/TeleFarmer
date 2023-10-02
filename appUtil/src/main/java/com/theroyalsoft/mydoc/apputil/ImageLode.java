package com.theroyalsoft.mydoc.apputil;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.skh.hkhr.util.NullRemoveUtil;
import com.skh.hkhr.util.thread.AppHandler;

public class ImageLode {


    public static void lodeImage(ImageView imageView, String url) {
        lodeImage(imageView, url, 0);
    }

    public static void lodeImage(ImageView imageView, String url, int placeHolder) {
        AppHandler.getUiHandlerNew().post(new Runnable() {
            @Override
            public void run() {
                Glide.with(AppUtilConfig.getAppContext()).applyDefaultRequestOptions(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(placeHolder))
                        .load(NullRemoveUtil.getNotNull(url))
                        .into(imageView);
            }
        });

    }

}
