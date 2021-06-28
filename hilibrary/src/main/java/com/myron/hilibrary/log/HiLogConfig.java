package com.myron.hilibrary.log;

/**
 * log的配置类
 */
public abstract class HiLogConfig {

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

}
