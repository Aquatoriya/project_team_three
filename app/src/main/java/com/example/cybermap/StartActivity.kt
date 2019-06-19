package com.example.cybermap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar!!.hide()
        button_start.setOnClickListener() {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}
