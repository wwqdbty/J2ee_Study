package com.kulang.service.impl;

import com.kulang.service.ICallback;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wenqiang.wang on 2016/11/25.
 */
@Service
public class AsyncCallService {
    private final int CorePoolSize = 4;
    private final int NeedSeconds = 3;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(CorePoolSize);

    public void asyncCall(final ICallback callback) {
        System.out.println("完成此任务需要 : " + NeedSeconds + " 秒");
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                callback.callback("长时间异步调用完成.");
            }
        }, NeedSeconds, TimeUnit.SECONDS);
    }
}
