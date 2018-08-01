package com.zhejiangdaily;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Function: MainActivityTest
 * <p>
 * Author: chen.h
 * Date: 2018/7/23
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private UiDevice mUiDevice;
    private String app_name = "com.zhejiangdaily";

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
    }

    @Test
    public void onClick() {
        mUiDevice.waitForWindowUpdate(app_name, 5 * 1000);
        UiObject o_phone = mUiDevice.findObject(new UiSelector().resourceId("com.zhejiangdaily:id/et_phone"));
        UiObject o_send = mUiDevice.findObject(new UiSelector().text("发送验证码"));
        try {
            o_phone.setText("13758284975");
            o_send.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

}