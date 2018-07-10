package passportdemo.zjrb.com.zjrbpassport.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 2018/7/9 下午3:30
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 基本工具类
 */
public class ZbUtil {

    private static Application sApplication;

    /**
     * 必须在Application里面调用
     * @param application
     */
    public static void init(Application application) {
        sApplication = application;
    }

    public static Context getContext() {
        return sApplication;
    }

    public static int getScreenW() {
        WindowManager windowManager = (WindowManager)
                getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenH() {
        WindowManager windowManager = (WindowManager)
                getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 判断是否是纯数字
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobileNum(String mobiles) {
        // 不为空,长度是11,数字类型
        if (!TextUtils.isEmpty(mobiles) && isNum(mobiles) && mobiles.length() == 11) {
            Pattern p = Pattern.compile("[1]\\d{10}"); // 1开头,匹配数字
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } else {
            return false;
        }

    }


    /**
     * dip转换px
     */
    public static int dip2px(float dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */
    public static float px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return px / scale;
    }

    /**
     * sp转px
     */
    public static int sp2px(float spVal) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getContext().getResources().getDisplayMetrics()) + 0.5f);
    }

    /**
     * px转sp
     */
    public static float px2sp(float pxVal) {
        return (pxVal / getContext().getResources().getDisplayMetrics().scaledDensity);
    }
}