package com.myron.hilibrary.log;

import android.text.TextUtils;

/**
 * Author: myron
 * Created: 2021/8/19
 * Description:堆栈信息处理帮助类
 */
public class HiStackTraceUtil {

    /**
     * @param stackTraces   原始堆栈信息
     * @param ignorePackage 忽略的包名
     * @param maxDepth      最终堆栈的最大深度
     * @return 获取裁剪好真实的堆栈信息
     */
    public static StackTraceElement[] getCroppedRealStackTrace(StackTraceElement[] stackTraces, String ignorePackage, int maxDepth) {
        return cropStackTraces(getRealStackTraces(stackTraces, ignorePackage), maxDepth);
    }

    /**
     * 获取除忽略包名之外的堆栈
     */
    private static StackTraceElement[] getRealStackTraces(StackTraceElement[] stackTraces, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTraces.length;
        if (TextUtils.isEmpty(ignorePackage)) {
            return stackTraces;
        }

        String className;
        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTraces[i].getClassName();
            if (className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStacks = new StackTraceElement[realDepth];
        System.arraycopy(stackTraces, ignoreDepth, realStacks, 0, realDepth);
        return realStacks;
    }

    /**
     * 裁剪堆栈信息
     *
     * @param stackTraces
     * @param maxDepth
     */
    private static StackTraceElement[] cropStackTraces(StackTraceElement[] stackTraces, int maxDepth) {
        int realDepth = stackTraces.length;
        //如果当前堆栈信息的深度没有达到最大深度，直接返回即可
        if (maxDepth <= 0 || realDepth < maxDepth) {
            return stackTraces;
        }
        realDepth = maxDepth;
        StackTraceElement[] realStacks = new StackTraceElement[realDepth];
        System.arraycopy(stackTraces, 0, realStacks, 0, realDepth);
        return realStacks;
    }
}
