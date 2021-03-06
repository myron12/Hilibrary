package com.myron.hilog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_hiLog -> {
                startActivity(Intent(this, HiLogDemoActivity::class.java))
            }
        }
    }
}