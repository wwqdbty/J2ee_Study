package com.kulang.controller;

import com.kulang.service.ICallback;
import com.kulang.service.impl.AsyncCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.Executors;

/**
 * Created by wenqiang.wang on 2016/11/24.
 */
public class SpringController {
    @Autowired
    AsyncCallService asyncCallService;

    @RequestMapping(value="/asynctask", method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<ModelAndView> asyncTask(){
        final DeferredResult<ModelAndView> deferredResult = new DeferredResult<ModelAndView>();
        System.out.println("/asynctask 调用！thread id is : " + Thread.currentThread().getId());

        asyncCallService.asyncCall(new ICallback() {
            @Override
            public void callback(Object result) {
                System.out.println("异步调用执行完成, thread id is : " + Thread.currentThread().getId());
                ModelAndView mav = new ModelAndView("view/asyncCall");
                mav.addObject("result", result);
                deferredResult.setResult(mav);
            }
        });

        return deferredResult;
    }
}