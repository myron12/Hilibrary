package com.myron.hilog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.myron.hilibrary.log.HiConsolePrinter
import com.myron.hilibrary.log.HiLog
import com.myron.hilibrary.log.HiLogConfig
import com.myron.hilibrary.log.HiLogType

class HiLogDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)
        findViewById<View>(R.id.btn_log).setOnClickListener {
            printLog()
        }
    }

    private fun printLog() {
        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, HiLogType.E, "-----", "5566")
        HiLog.e("9900")
    }
}