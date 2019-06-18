package com.example.cybermap

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*


class Info : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        button_back.setOnClickListener() {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}
