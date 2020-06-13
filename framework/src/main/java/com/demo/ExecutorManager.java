package com.demo;

import android.os.AsyncTask;

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

    public <Params, Progress, Result> void execute(AsyncTask<Params, Progress, Result> task, Params... params) {
        task.executeOnExecutor(sService, params);
    }

    private ExecutorManager() {
        sService = new ThreadPoolExecutor(5, 10, 30,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    }

    private static class ExecutorManagerHolder {
        private static final ExecutorManager INSTANCE = new ExecutorManager();
    }

}
