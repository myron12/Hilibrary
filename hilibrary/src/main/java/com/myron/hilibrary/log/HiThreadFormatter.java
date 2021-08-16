package com.myron.hilibrary.log;

/**
 * Author: myron
 * Created: 2021/8/10
 * Description:线程格式化
 */
public class HiThreadFormatter implements HiLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "Thread:"+data.getName();
    }
}
