package com.theroyalsoft.telefarmer.helper;

import android.os.Parcel;
import android.text.style.URLSpan;
import android.view.View;

/**
 * Created by Pritom Dutta on 28/11/23.
 */

public class CustomTabsURLSpan extends URLSpan {
    public CustomTabsURLSpan(String url) {
        super(url);
    }

    public CustomTabsURLSpan(Parcel src) {
        super(src);
    }

    @Override
    public void onClick(View widget) {
        String url = getURL();
        GeneralUtils.loadUrl(widget.getContext(), url);
    }
}