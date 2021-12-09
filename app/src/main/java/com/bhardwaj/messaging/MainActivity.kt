package com.bhardwaj.messaging

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bhardwaj.messaging.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("Aditya", "onCreate: ${binding.editCookies.hint.toString()}")
    }
}