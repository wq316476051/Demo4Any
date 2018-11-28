package com.wang.demo4any

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class TestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_click).setOnClickListener {
            it.isEnabled = false

            it.postDelayed({
                it.isEnabled = true
            }, 1000L)
        }
    }
}
