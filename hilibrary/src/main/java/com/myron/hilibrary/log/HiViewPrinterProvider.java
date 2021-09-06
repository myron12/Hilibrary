package com.myron.hilibrary.log;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.PasswordAuthentication;

import androidx.recyclerview.widget.RecyclerView;

/**
 * HiViewPrinter的帮助类，控制ViewPrinter的显示和隐藏
 */
public class HiViewPrinterProvider {

    private FrameLayout mRootView;
    private RecyclerView mRecyclerView;
    private View mFloatingView;
    private boolean isOpen;
    private FrameLayout mLogView;
    private static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    private static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";

    public HiViewPrinterProvider(FrameLayout rootView, RecyclerView recyclerView) {
        this.mRootView = rootView;
        this.mRecyclerView = recyclerView;
    }

    /**
     * 显示悬浮框
     */
    public void showFloatingView() {
        if (mRootView.findViewWithTag(TAG_FLOATING_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        View floatingView = genFloatingView();
        floatingView.setTag(TAG_FLOATING_VIEW);
        floatingView.setBackgroundColor(Color.BLACK);
        floatingView.setAlpha(0.8f);
        params.bottomMargin = HiDisplayUtil.dp2px(100, mRootView.getResources());
        mRootView.addView(floatingView, params);
    }

    /**
     * 隐藏悬浮创
     */
    public void hideFloatingView() {
        mRootView.removeView(mFloatingView);
    }

    private View genFloatingView() {
        if (mFloatingView != null) {
            return mFloatingView;
        }
        TextView textView = new TextView(mRootView.getContext());
        textView.setOnClickListener(view -> {
            if (!isOpen) {
                showOpenView();
            }
        });
        textView.setText("HiLog");
        return mFloatingView = textView;
    }

    private void showOpenView() {
        if (mRootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2px(160, mRootView.getResources()));
        params.gravity = Gravity.BOTTOM;
        View logView = genLogView();
        mLogView.setTag(TAG_LOG_VIEW);
        mRootView.addView(logView, params);
    }

    private View genLogView() {
        if (mLogView != null) {
            return mLogView;
        }

        FrameLayout frameLayout = new FrameLayout(mRootView.getContext());
        frameLayout.setBackgroundColor(Color.BLACK);
        frameLayout.addView(mRecyclerView);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        TextView closeView = new TextView(mRootView.getContext());
        closeView.setOnClickListener(view -> {
            closeLogView();
        });
        closeView.setText("close");
        frameLayout.addView(closeView, params);
        return mLogView = frameLayout;
    }

    /**
     * 关闭logview
     */
    private void closeLogView() {
        isOpen = false;
        mRootView.removeView(mLogView);
    }
}
