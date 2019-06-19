package com.example.cybermap

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_info.*


class InfoActivity : AppCompatActivity() {

    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var site: String
    private lateinit var hours: String
    private var isAvailableOnlineBooking = 0
    private lateinit var coordinates: String
    private lateinit var images : IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        name = intent.getStringExtra("name")
        address = intent.getStringExtra("address")
        phone = intent.getStringExtra("phone")
        site = intent.getStringExtra("site")
        hours = intent.getStringExtra("hours")
        isAvailableOnlineBooking = intent.getIntExtra("isAvailableOnlineBooking", 0)
        //coordinates = intent.getStringExtra("coordinates")
        images = intent.getIntArrayExtra("images")

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(name)

        val imgAdapter = ImageAdapter(this, images)
        viewPager.adapter = imgAdapter

        val txt_name = findViewById<View>(R.id.info) as TextView
        txt_name.text = "Name of the club: $name \n" + "The phone number: $phone\n" + "Club's address: $address\n" +"Club's site: $site\n"+"Is online Booking available: ${isAvailableOnlineBooking.toString()}\n"+"Working hours: $hours\n"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
