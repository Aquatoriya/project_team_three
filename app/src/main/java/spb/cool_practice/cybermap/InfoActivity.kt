package spb.cool_practice.cybermap


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info.*
import android.graphics.Typeface
import android.text.Html
import spb.cool_practice.cybermap.R


class InfoActivity : AppCompatActivity() {

    private lateinit var nameData: String
    private lateinit var addressData: String
    private lateinit var phoneData: String
    private lateinit var siteData: String
    private lateinit var hoursData: String
    private var isAvailableOnlineBookingData = 0
    private lateinit var coordinates: String
    private lateinit var images : IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        nameData = intent.getStringExtra("name")
        addressData = intent.getStringExtra("address")
        phoneData = intent.getStringExtra("phone")
        siteData = intent.getStringExtra("site")
        hoursData = intent.getStringExtra("hours")
        isAvailableOnlineBookingData = intent.getIntExtra("isAvailableOnlineBooking", 0)
        //coordinates = intent.getStringExtra("coordinates")
        images = intent.getIntArrayExtra("images")

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(nameData)

        val imgAdapter = ImageAdapter(this, images)
        viewPager.adapter = imgAdapter
        //For better perfomance
        viewPager.offscreenPageLimit = images.size - 1

        val typeface = Typeface.createFromAsset(assets, "font/Roboto-ThinItalic.ttf")
        name.text = Html.fromHtml("<b>Name of the club: </b> $nameData")
        phone.text = Html.fromHtml("<b>The phone number: </b> $phoneData")
        address.text = Html.fromHtml("<b>Club's address: </b> $addressData")
        site.text = Html.fromHtml("<b>Club's site: </b> $siteData")
        hours.text = Html.fromHtml("<b>Working hours: </b> $hoursData")
        booking.text = Html.fromHtml("<b>Is online Booking available: </b> $isAvailableOnlineBookingData")

        name.typeface = typeface
        phone.typeface = typeface
        address.typeface = typeface
        site.typeface = typeface
        hours.typeface = typeface
        booking.typeface = typeface

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
