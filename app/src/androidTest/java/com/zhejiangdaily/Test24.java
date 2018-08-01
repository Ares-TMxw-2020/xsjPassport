package com.zhejiangdaily;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.zhejiangdaily.IUtil.log;
import static org.junit.Assert.assertEquals;

/**
 * Function: Test24
 * <p>
 * Author: chen.h
 * Date: 2018/7/26
 */
@RunWith(AndroidJUnit4.class)
public class Test24 {

    private UiDevice mUiDevice;
    private String app_name = "com.cmstop.qjwb";

    @Before
    public void setUp() throws Exception {
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context mContext = InstrumentationRegistry.getContext();

        if (!mUiDevice.isScreenOn()) {
            mUiDevice.wakeUp();
        }

        mUiDevice.pressHome();
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(app_name);
        mContext.startActivity(intent);
        IUtil.wait(mUiDevice, app_name, 10);
    }


    @Test
    public void scroll() throws Exception {
        UiScrollable os_list = new UiScrollable(new UiSelector().resourceId("com.cmstop.qjwb:id/rv_content"));
        os_list.scrollToEnd(10);
    }


    @Test
    public void scroll2() throws Exception {
        UiScrollable os = new UiScrollable(new UiSelector().resourceId("com.cmstop.qjwb:id/banner_view"));
        int i = 0;
        os.setAsHorizontalList();
        while (i < 5) {
            Thread.sleep(1000);
            os.flingBackward();
            i++;
        }
    }


    @Test
    public void listTest() throws Exception {
        Thread.sleep(1000);
        UiCollection oc_content = new UiCollection(new UiSelector().resourceId("com.cmstop.qjwb:id/rv_content"));

        log("rv count" + oc_content.getChildCount());

        UiObject o_item = oc_content.getChild(new UiSelector().className("android.widget.LinearLayout"));
        UiObject o_text = o_item.getChild(new UiSelector().resourceId("com.cmstop.qjwb:id/tv_item_feed_column_name"));

        log(o_text.getText());

        assertEquals("抑郁症诊疗所", o_text.getText());
    }

}
