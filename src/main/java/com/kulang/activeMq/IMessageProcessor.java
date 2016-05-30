package com.kulang.activeMq;

/**
 * Created by wenqiang.wang on 2016/5/30.
 */

import java.io.Serializable;

/**
 * 消息处理回调统一接口
 */
interface IMessageProcessor {
    void messageProcess(Serializable serializable);
}