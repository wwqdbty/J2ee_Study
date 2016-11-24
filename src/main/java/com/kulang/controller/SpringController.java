package com.kulang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wenqiang.wang on 2016/11/24.
 */
public class SpringController {
    @RequestMapping(value="/asynctask", method = RequestMethod.GET)
    public DeferredResult<ModelAndView> asyncTask(){
        DeferredResult<ModelAndView> deferredResult = new DeferredResult<ModelAndView>();
        System.out.println("/asynctask 调用！thread id is : " + Thread.currentThread().getId());
        LongTimeAsyncCallService longTimeAsyncCallService =
        longTimeAsyncCallService.makeRemoteCallAndUnknownWhenFinish(new LongTermTaskCallback() {
            @Override
            public void callback(Object result) {
                System.out.println("异步调用执行完成, thread id is : " + Thread.currentThread().getId());
                ModelAndView mav = new ModelAndView("remotecalltask");
                mav.addObject("result", result);
                deferredResult.setResult(mav);
            }
        });
    }
}

interface LongTermTaskCallback {
    void callback(Object result);
}

class LongTimeAsyncCallService {
    private final int CorePoolSize = 4;
    private final int NeedSeconds = 3;
    private Random random = new Random();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(CorePoolSize);
    public void makeRemoteCallAndUnknownWhenFinish(LongTermTaskCallback callback){
        System.out.println("完成此任务需要 : " + NeedSeconds + " 秒");
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                callback.callback("长时间异步调用完成.");
            }
        }, "这是处理结果:)", TimeUnit.SECONDS);
    }
}