 package com.myron.hilibrary.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class HiLogMo {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);
    private long timeMillis;
    private int level;
    private String tag;
    private String log;

    public HiLogMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    public String flattenedLog() {
        return getFlattened() + "\n" + log;
    }

    public String getFlattened() {
        return formate(timeMillis) + '|' + level + '|' + tag + "|:";
    }

    public String formate(long timeMillis) {
        return sdf.format(timeMillis);
    }
}
