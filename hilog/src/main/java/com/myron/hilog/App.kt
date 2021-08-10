package com.myron.hilog

import android.app.Application
import com.myron.hilibrary.log.HiLogConfig
import com.myron.hilibrary.log.HiLogManager

/**
 *
 * Author: maqihang
 * Email: maqihang@iyunxiao.com
 * Created: 2021/8/103:19 PM
 * Description:
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object : HiLogConfig() {
            override fun getGlobleTag(): String {
                return "App"
            }

            override fun enable(): Boolean {
                return true
            }
        })
    }
}