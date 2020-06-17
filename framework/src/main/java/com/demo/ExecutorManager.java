package com.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorManager {

    private static ExecutorService sService;

    public static ExecutorManager getInstance() {
        return ExecutorManagerHolder.INSTANCE;
    }

    public void execute(Runnable runnable) {
        sService.execute(runnable);
    }

    public ExecutorService getService() {
        return sService;
    }

    private ExecutorManager() {
        sService = new ThreadPoolExecutor(5, 10, 30,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    }

    private static class ExecutorManagerHolder {
        private static final ExecutorManager INSTANCE = new ExecutorManager();
    }

}
