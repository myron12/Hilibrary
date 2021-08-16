package com.myron.hilibrary.log;

/**
 * Author: myron
 * Description:日志格式化
 */
interface HiLogFormatter<T> {
    String format(T data);
}
