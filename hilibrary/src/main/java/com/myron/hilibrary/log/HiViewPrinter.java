package com.myron.hilibrary.log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
    public HiViewPrinter() {
    }

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, String printString) {

    }

    private static class ViewAdapter extends RecyclerView.Adapter<LogViewHolder> {
        private LayoutInflater inflater;

        public ViewAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        private List<HiLogMo> list = new ArrayList<>();

        public void setList(List<HiLogMo> list) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        public void add(HiLogMo logMoItem) {
            list.add(logMoItem);
            notifyItemInserted(list.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.hilog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return list.isEmpty() ? 0 : list.size();
        }


    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
