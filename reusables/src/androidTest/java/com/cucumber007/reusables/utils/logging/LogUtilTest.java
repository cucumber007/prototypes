package com.cucumber007.reusables.utils.logging;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LogUtilTest {
    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Before
    public void before() {
        Context instrumentationCtx = InstrumentationRegistry.getContext();
        LogUtil.setContext(instrumentationCtx);
    }

    @Test
    public void logError() throws Exception {

    }

    @Test
    public void makeToast() throws Exception {
        try {
            uiThreadTestRule.runOnUiThread(() -> {
                LogUtil.makeToast("Text");
            });
        } catch (Throwable throwable) {
            Assert.assertTrue(false);
            throwable.printStackTrace();
        }
    }

}