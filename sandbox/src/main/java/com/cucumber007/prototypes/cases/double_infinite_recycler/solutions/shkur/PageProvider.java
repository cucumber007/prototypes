package com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.shkur;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Project: EndlessScrollingBothSides
 * Follow me: @tonyshkurenko
 *
 * @author Anton Shkurenko
 * @since 3/27/18
 */
public final class PageProvider {

    private static final long SLEEP = 5000;
    private static final int PAGE_SIZE = 20;

    final Executor executor = Executors.newSingleThreadExecutor();

    public void request(final int page, final PageCallback callback) {
        executor.execute(() -> {

            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException ignored) {}

            final List<String> strings = new ArrayList<>(PAGE_SIZE);

            for (int i = 0; i < PAGE_SIZE; i++) {
                strings.add("Shk "+(page * PAGE_SIZE + i));
            }

            callback.onSuccess(strings);
        });
    }

    public interface PageCallback {
        void onSuccess(List<String> data);
    }
}