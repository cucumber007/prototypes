package com.cucumber007.reusables.utils;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class DpUtilTest {

    @Test
    public void test() throws Exception {
        float initPx = 200;
        float dp = DpUtil.pxToDp(initPx, InstrumentationRegistry.getContext());
        float endPx = DpUtil.dpToPx(dp, InstrumentationRegistry.getContext());
        assertEquals(initPx, endPx);
    }


}