package com.myron.hilibrary.log;

/**
 * Author: myron
 * Created: 2021/8/10
 * Description: 堆栈信息格式化器
 */
public class HiStackTraceFormatter implements HiLogFormatter<StackTraceElement[]> {
    @Override
    public String format(StackTraceElement[] stacks) {
        StringBuilder sb = new StringBuilder(128);
        if (stacks == null || stacks.length == 0) {//堆栈信息为空
            return null;
        } else if (stacks.length == 1) {//堆栈信息只有1条
            return "\t-" + stacks[0].toString();
        } else {
            for (int i = 0, len = stacks.length; i < len; i++) {
                if (i == 0) {//第一条信息
                    sb.append("stackTrace:\n");
                }
                if (i != len - 1) {//不是最后一条信息
                    sb.append("\t");
                    sb.append(stacks[i].toString());
                    sb.append("\n");
                } else {
                    sb.append("\t");
                    sb.append(stacks[i].toString());
                }
            }
        }
        return sb.toString();
    }
}
