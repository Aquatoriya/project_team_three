package spb.cool_practice.cybermap

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_style_map.*

class StyleMapActivity : AppCompatActivity() {

    private var map_style = 0 // Themes: 1 - Standard; 2 - Dark; 3 - Retro; 4 - Night
    private val radioButtonOnClickListener = View.OnClickListener {v:View ->
        val rb = v as RadioButton
        when(rb.id) {
            R.id.radio_button_standard_theme -> map_style = 1
            R.id.radio_button_dark_theme -> map_style = 2
            R.id.radio_button_retro_theme -> map_style = 3
            R.id.radio_button_night_theme -> map_style = 4
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_style_map)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "CyberMap"

        map_style = intent.getIntExtra("map_style", 1)
        when(map_style) {
            1 -> radio_button_standard_theme.isChecked = true
            2 -> radio_button_dark_theme.isChecked = true
            3 -> radio_button_retro_theme.isChecked = true
            4 -> radio_button_night_theme.isChecked = true
        }


        radio_button_standard_theme.setOnClickListener(radioButtonOnClickListener)
        radio_button_dark_theme.setOnClickListener(radioButtonOnClickListener)
        radio_button_retro_theme.setOnClickListener(radioButtonOnClickListener)
        radio_button_night_theme.setOnClickListener(radioButtonOnClickListener)

        button_save.setOnClickListener {
            //It is supposed to save but it doesn't work yet :p
            Toast.makeText(this, "Saved (doesn't work yet)", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val returnIntent = Intent()
                returnIntent.putExtra("map_style", map_style)
                setResult(Activity.RESULT_OK, returnIntent)

                this.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("map_style", map_style)
        setResult(Activity.RESULT_OK, returnIntent)
        this.finish()
    }

}
