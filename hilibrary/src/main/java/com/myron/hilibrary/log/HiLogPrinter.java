package com.myron.hilibrary.log;

import androidx.annotation.NonNull;

/**
 * Description:日志打印
 */
public interface HiLogPrinter {
    void print(@NonNull HiLogConfig config, int level, String tag, String printString);
}
