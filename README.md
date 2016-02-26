# CustomToast
a custom Toast dome
# 自定义Toast

android系统默认toast非常呆板，在这个追求“颜值”的世界里，它已经严重拖累了应用界面的美观，那么如何才能美化toast，使之与应用的界面相协调呢。不多说了，现在开始介绍。

先来上图看看系统默认toast和本例要实现的自定义toast的效果对比




######系统自带的toast

![系统自带toast](http://img.blog.csdn.net/20160226213940569)

######本文要实现的toast效果

![本文要实现的toast效果](http://img.blog.csdn.net/20160226214336962)

要想实现自定义的toast 就得先了解系统的toast是怎么实现的。使用系统默认的toast，想必大家都会：
``` 
Toast.makeText(MainActivity.this,"normaltoast",Toast.LENGTH_SHORT).show();
``` 
那么我们就先从makeText()方法看起：
``` 
public static Toast makeText(Context context, CharSequence text, @Duration int duration) {
    Toast result = new Toast(context);

    LayoutInflater inflate = (LayoutInflater)
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = inflate.inflate(com.android.internal.R.layout.transient_notification, null);
    TextView tv = (TextView)v.findViewById(com.android.internal.R.id.message);
    tv.setText(text);
    
    result.mNextView = v;
    result.mDuration = duration;

    return result;
}
```
上面是Toast类的makeText()方法的源码，我们可以看到，在这个方法中，系统先实例化了一个Toast对象，然后加载了一个布局，拿到布局里的TextView，然后把要Toast的内容设置到这个TextView上面。最后给Toast对象的两个成员变量赋值：一个是Toast内容所在的TextView,另一个是Toast的时间。然后返回Toast对象。

所以要想改变toast的样式，就可以通过修改inflate填充的布局来实现。可以自定义一个布局，通过修改布局文件的样式来实现toast样式的不同。

另外，我们卡伊看到Toast源码中有如下方法：
```
/**
 * Set the location at which the notification should appear on the screen.
 * @see android.view.Gravity
 * @see #getGravity
 */
public void setGravity(int gravity, int xOffset, int yOffset) {
    mTN.mGravity = gravity;
    mTN.mX = xOffset;
    mTN.mY = yOffset;
}
```
Toast对象可以通过这个方法来设置show的具体位置。

最后，我们可已经上面的设置思路在一个utils中实现：

```
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
```
你看通过简单的阅读源码，就可以成功设置出自己想要的toast效果了。是不是很简单呢？
