package com.myron.hilibrary.log;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myron.hilibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: myron
 * Created: 2021/8/19
 * Description:将log显示到我们的界面上
 */
public class HiViewPrinter implements HiLogPrinter {
    private RecyclerView mRecyclerView;
    private LogAdapter mAdapter;
    private HiViewPrinterProvider hiViewPrinterProvider;

    public HiViewPrinter(Activity activity) {
        FrameLayout frameLayout = activity.findViewById(android.R.id.content);
        mRecyclerView = new RecyclerView(activity);
        mAdapter = new LogAdapter(LayoutInflater.from(mRecyclerView.getContext()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        hiViewPrinterProvider = new HiViewPrinterProvider(frameLayout, mRecyclerView);
    }

    /**
     * 对外暴漏获取provider帮助类的方法
     */
    public HiViewPrinterProvider getHiViewPrinterProvider() {
        return hiViewPrinterProvider;
    }

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, String printString) {
        HiLogMo hiLogMo = new HiLogMo(System.currentTimeMillis(), level, tag, printString);
        mAdapter.addItem(hiLogMo);
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
        private LayoutInflater inflater;

        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        private List<HiLogMo> logs = new ArrayList<>();

        public void setList(List<HiLogMo> list) {
            this.logs.addAll(list);
            notifyDataSetChanged();
        }

        public void addItem(HiLogMo logMoItem) {
            logs.add(logMoItem);
            notifyItemInserted(logs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.hilog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            HiLogMo logItem = logs.get(position);
            int color = getHighLightColor(logItem.getLevel());
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);
            holder.tagView.setText(logItem.getFlattened());
            holder.messageView.setText(logItem.getLog());
        }


        /**
         * 根据不同的日志级别获取不同的高亮的颜色
         *
         * @param logLevel 日志级别
         * @return 高亮的颜色
         */
        private int getHighLightColor(int logLevel) {
            int highLight;
            switch (logLevel) {
                case HiLogType.V:
                    highLight = 0xffbbbbbb;
                    break;
                case HiLogType.D:
                    highLight = 0xffffffff;
                    break;
                case HiLogType.I:
                    highLight = 0xff6a8759;
                    break;
                case HiLogType.W:
                    highLight = 0xffbbb529;
                    break;
                case HiLogType.E:
                    highLight = 0xffff6b68;
                    break;
                default:
                    highLight = 0xffffff00;
                    break;
            }
            return highLight;
        }

        @Override
        public int getItemCount() {
            return logs.isEmpty() ? 0 : logs.size();
        }


    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        private TextView tagView;
        private TextView messageView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tv_tag);
            messageView = itemView.findViewById(R.id.tv_log);
        }
    }
}
