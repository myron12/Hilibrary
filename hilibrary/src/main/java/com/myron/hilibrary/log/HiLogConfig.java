package com.myron.hilibrary.log;

/**
 * log的配置类
 */
public abstract class HiLogConfig {
    static int MAX_LEN = 512;
    static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();
    static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();

    /**
     * 初始化json序列化器
     */
    public JsonParse injectJsonParse() {
        return null;
    }

    /**
     * 全局的变量
     */
    public String getGlobleTag() {
        return "HiLog";
    }

    /**
     * Hilog是否可用
     */
    public boolean enable() {
        return true;
    }

    /**
     * 是否打印线程信息
     */
    public boolean includeThread() {
        return false;
    }

    /**
     * 堆栈信息的深度
     */
    public int stackTraceDepth() {
        return 5;
    }

    /**
     * 添加打印器
     */
    public HiLogPrinter[] printers() {
        return null;
    }

    public interface JsonParse {
        String toJson(Object o);
    }

}
