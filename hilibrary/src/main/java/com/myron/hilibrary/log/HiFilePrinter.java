package com.myron.hilibrary.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import androidx.annotation.NonNull;

/**
 * tips:
 * 1、BlockingQueue的使用，防止频繁的创建线程;
 * 2、线程同步
 * 3、文件操作，BufferedWriter的使用
 */
public class HiFilePrinter implements HiLogPrinter {
    private static ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private LogWriter mLogWriter;
    private String mLogPath;
    private PrintWorker mWorker;
    private final long retentionTime;
    private static HiFilePrinter instance;

    /**
     * 创建HiFilePrinter
     *
     * @param logPath       log保存路径，如果是外部路径需要确保已经有外部存储的读写权限
     * @param retentionTime log文件的有效时长，单位毫秒，<=0表示一直有效
     */
    public static synchronized HiFilePrinter getInstance(String logPath, long retentionTime) {
        if (instance == null) {
            instance = new HiFilePrinter(logPath, retentionTime);
        }
        return instance;
    }

    private HiFilePrinter(String logPath, long retentionTime) {
        this.mLogPath = logPath;
        this.retentionTime = retentionTime;
        this.mLogWriter = new LogWriter();
        this.mWorker = new PrintWorker();
        cleanExpiredLog();
    }

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, String printString) {
        long timeMillis = System.currentTimeMillis();
        if (!mWorker.isRunning()) {
            mWorker.start();
        }
        mWorker.put(new HiLogMo(timeMillis, level, tag, printString));
    }

    private void doPrint(HiLogMo logMo) {
        String lastFileName = mLogWriter.getPreFileName();
        if (lastFileName == null) {
            String newFileName = genFileName();
            if (mLogWriter.isReady()) {
                mLogWriter.close();
            }
            if (!mLogWriter.ready(newFileName)) {
                return;
            }
        }
        mLogWriter.append(logMo.flattenedLog());
    }

    /**
     * 清除过期log
     */
    private void cleanExpiredLog() {
        if (retentionTime <= 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        File logDir = new File(mLogPath);
        File[] files = logDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                file.delete();
            }
        }
    }

    private String genFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 打印的工作线程
     */
    private class PrintWorker implements Runnable {
        private BlockingQueue<HiLogMo> logs = new LinkedBlockingQueue<>();//打印阻塞队列
        private boolean running;

        /**
         * 将log放到打印队列中
         */
        private void put(HiLogMo logMo) {
            try {
                logs.put(logMo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 判断工作线程是否已经开启
         */
        private boolean isRunning() {
            synchronized (this) {
                return running;
            }
        }

        /**
         * 开启工作线程
         */
        void start() {
            synchronized (this) {
                EXECUTOR.execute(this);
                running = true;
            }
        }

        @Override
        public void run() {
            HiLogMo logMo;
            try {
                while (true) {
                    logMo = logs.take();
                    doPrint(logMo);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }

    /**
     * 基于BufferedWriter将log写入到本地文件中
     */
    private class LogWriter {
        private BufferedWriter bufferedWriter;
        private File logFile;
        private String preFileName;

        boolean isReady() {
            return bufferedWriter != null;
        }

        public String getPreFileName() {
            return preFileName;
        }

        /**
         * log写入前的一些准备
         *
         * @param newFileName 要保存log的名称
         * @return true 表示准备就绪
         */
        private boolean ready(String newFileName) {
            preFileName = newFileName;
            logFile = new File(mLogPath, newFileName);
            //当log文件不存在时创建log文件
            if (!logFile.exists()) {
                try {
                    File parent = logFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    preFileName = null;
                    logFile = null;
                    return false;
                }
            }

            try {
                bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));
            } catch (IOException e) {
                e.printStackTrace();
                preFileName = null;
                logFile = null;
                return false;
            }
            return true;
        }

        /**
         * 关闭bufferedWriter
         */
        boolean close() {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    bufferedWriter = null;
                    logFile = null;
                    preFileName = null;
                }
            }
            return true;
        }

        /**
         * 将log写入文件
         *
         * @param flattedLog 格式化之后的log
         */
        void append(String flattedLog) {
            try {
                bufferedWriter.append(flattedLog);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
