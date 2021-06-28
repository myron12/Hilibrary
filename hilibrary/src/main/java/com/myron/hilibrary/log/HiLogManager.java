package com.myron.hilibrary.log;

import androidx.annotation.NonNull;

/**
 * HiLog控制类
 */
public class HiLogManager {
    private HiLogConfig config;
    private static HiLogManager INSTANCE;

    private HiLogManager(HiLogConfig config) {
        this.config = config;
    }

    public static HiLogManager getInstance() {
        return INSTANCE;
    }

    public static void init(@NonNull HiLogConfig config) {
        INSTANCE = new HiLogManager(config);
    }

    public HiLogConfig getConfig() {
        return config;
    }
}
