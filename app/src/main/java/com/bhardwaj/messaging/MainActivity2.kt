package com.bhardwaj.messaging

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        mContext = this@MainActivity2
        val numberOfCookies = intent.getStringExtra("cookies")

        Toast.makeText(mContext, "$numberOfCookies", Toast.LENGTH_SHORT).show()
    }
}