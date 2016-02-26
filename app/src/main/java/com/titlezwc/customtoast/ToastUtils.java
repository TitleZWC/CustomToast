package com.titlezwc.customtoast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ToastUtils {
    private static Toast mToast;

    /**
     * @param activity 上下文
     * @param content 吐司内容
     */
    public static void show(Activity activity,  String content) {
        show(activity, content, Gravity.BOTTOM, 0, getWidthPixels(activity) / 6);
    }

    private static int getWidthPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * @param activity 上下文
     * @param content 吐司内容
     * @param gravity 重心
     * @param xOffset X偏移量
     * @param yOffset Y偏移量
     */
    @SuppressLint("InflateParams")
    public static void show(Activity activity,  String content, int gravity,
                            int xOffset, int yOffset) {
       int  widthPixels = getWidthPixels(activity);
        if (mToast == null) {
            mToast = new Toast(activity);
            mToast.setGravity(gravity, xOffset, yOffset);

            View toastView = LayoutInflater.from(activity).inflate(
                    R.layout.toastview, null);
            mToast.setView(toastView);
            TextView tv = (TextView) toastView
                    .findViewById(R.id.textView_toastView_content);
            ImageView iv_iconb = (ImageView) toastView
                    .findViewById(R.id.imageView_toastView_toast_iconb);
            ImageView iv_icon = (ImageView) toastView
                    .findViewById(R.id.imageView_toastView_toast_icon);
            tv.setPadding((int) (0.056 * widthPixels),
                    (int) (0.0282 * widthPixels),
                    (int) (0.056 * widthPixels),
                    (int) (0.0282 * widthPixels));
            LayoutParams params_iv_b = (LayoutParams) iv_iconb
                    .getLayoutParams();
            LayoutParams params_iv = (LayoutParams) iv_icon.getLayoutParams();
            int margins_bottom = params_iv.height
                    + (int) (0.0282 * widthPixels * 3.5)
                    - params_iv_b.height;
            params_iv_b.setMargins(0, 0, 0, margins_bottom);
            iv_iconb.setVisibility(View.VISIBLE);
            iv_icon.setVisibility(View.VISIBLE);
            tv.setText(content);
        } else {
            ((TextView) (mToast.getView()
                    .findViewById(R.id.textView_toastView_content)))
                    .setText(content);
        }
        mToast.show();
    }
}
