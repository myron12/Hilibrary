package com.myron.hilog

import android.app.Application
import com.google.gson.Gson
import com.myron.hilibrary.log.HiConsolePrinter
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
            override fun injectJsonParse(): JsonParse {
                return JsonParse { src -> Gson().toJson(src) }
            }

            override fun getGlobleTag(): String {
                return "App"
            }

            override fun enable(): Boolean {
                return true
            }
        }, HiConsolePrinter())
    }
}