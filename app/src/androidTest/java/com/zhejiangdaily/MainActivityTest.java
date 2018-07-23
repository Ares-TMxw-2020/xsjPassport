package com.zhejiangdaily;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

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

    private UiDevice uiDevice;

    @Before
    public void setUp() throws Exception {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        if (!uiDevice.isScreenOn()) {
            uiDevice.wakeUp();
        }

        uiDevice.pressHome();
    }

    @Test
    public void onClick() {
    }
}