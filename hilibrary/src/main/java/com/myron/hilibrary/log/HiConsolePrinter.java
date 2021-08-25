package com.myron.hilibrary.log;

import android.util.Log;

import androidx.annotation.NonNull;

import static com.myron.hilibrary.log.HiLogConfig.MAX_LEN;

/**
 * Author: myron
 * Created: 2021/8/10
 * Description:控制台打印器
 */
public class HiConsolePrinter implements HiLogPrinter {
    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, String printString) {
        int len = printString.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {//超过了一行
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, printString.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {//最后一行无法整除
                Log.println(level, tag, printString.substring(index, len));
            }
        } else {
            Log.println(level, tag, printString);
        }

    }
}
