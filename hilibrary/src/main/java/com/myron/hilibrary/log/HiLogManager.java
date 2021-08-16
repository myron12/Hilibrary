package com.myron.hilibrary.log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * HiLog控制类
 */
public class HiLogManager {
    private HiLogConfig config;
    private static HiLogManager INSTANCE;
    private List<HiLogPrinter> printers = new ArrayList<>();

    private HiLogManager(HiLogConfig config, HiLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static HiLogManager getInstance() {
        return INSTANCE;
    }

    public static void init(@NonNull HiLogConfig config, HiLogPrinter... printers) {
        INSTANCE = new HiLogManager(config, printers);
    }


    public HiLogConfig getConfig() {
        return config;
    }

    public List<HiLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(HiLogPrinter printer) {
        if (printers != null) {
            printers.remove(printer);
        }
    }
}
