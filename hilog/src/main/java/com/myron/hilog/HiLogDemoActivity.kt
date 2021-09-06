package com.myron.hilog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.myron.hilibrary.log.*

class HiLogDemoActivity : AppCompatActivity() {
    var viewPrinter: HiViewPrinter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)
        findViewById<View>(R.id.btn_log).setOnClickListener {
            printLog()
        }
        viewPrinter = HiViewPrinter(this)
        viewPrinter!!.hiViewPrinterProvider.showFloatingView()
    }

    private fun printLog() {
        HiLogManager.getInstance().addPrinter(viewPrinter)
        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, HiLogType.E, "-----", "5566")
        HiLog.i("9900")
    }
}