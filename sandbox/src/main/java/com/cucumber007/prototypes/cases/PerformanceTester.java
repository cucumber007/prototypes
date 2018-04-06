package com.cucumber007.prototypes.cases;

import com.cucumber007.reusables.utils.logging.LogUtil;

public abstract class PerformanceTester implements Runnable {


    public void test() {
        long start = System.currentTimeMillis();
        run();
        LogUtil.logDebug("****** Test ******", getName(), System.currentTimeMillis() - start);
    }

    public abstract String getName();

}
