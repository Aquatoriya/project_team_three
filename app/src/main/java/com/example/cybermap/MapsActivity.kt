package com.example.cybermap




import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View

import android.widget.Toast


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    val positiveMarkerClickListener = {dialog:DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.yes, Toast.LENGTH_SHORT).show()
    }
    val negativeMarkerClickListener = {dialog:DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }
    val neutralMarkerClickListener = {dialog:DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Maybe", Toast.LENGTH_SHORT).show()
    }
    fun basicAlert(view: View){

        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Androidly Alert")
            setMessage("We have a message")
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveMarkerClickListener))
            setNegativeButton(android.R.string.no, negativeMarkerClickListener)
            setNeutralButton("Maybe", neutralMarkerClickListener)
            show()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("List of Clubs")
            setItems(computerClubs.coolToArray()) { dialog: DialogInterface, which:Int ->

                //
                Toast.makeText(applicationContext, "${computerClubs[which]}", Toast.LENGTH_SHORT).show()
            }

            setPositiveButton("OK", positiveMarkerClickListener)
            show()
        }
    }

    private lateinit var mMap: GoogleMap

    private lateinit var localDB: DBHandler

    private var countOfClubs = 24

    private lateinit var computerClubs: ArrayList<ComputerClubData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        localDB = DBHandler(this)
        computerClubs = localDB.listComputerClubs("%")
        if (computerClubs.size != 24) {
            localDB.addAllComputerClubs()
            computerClubs = localDB.listComputerClubs("%")
            Log.d("111111", "In if")
        }

        button1.setOnClickListener {
            for (i in computerClubs.indices) {
                val club = LatLng(computerClubs[i].coordinates[0], computerClubs[i].coordinates[1])
                mMap.addMarker(MarkerOptions().position(club).title(computerClubs[i].name))
            }
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMarkerClickListener {
            showDialog()
            return@setOnMarkerClickListener true
        }
            val stPetersburg = LatLng(59.93863, 30.31413)
            mMap.addMarker(MarkerOptions().position(stPetersburg).title("Saint Petersburg"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stPetersburg, 12.0f))

            //val gamer = LatLng(60.04015, 30.334697)
            //mMap.addMarker(MarkerOptions().position(gamer).title("Computer club 'Gamer' "))
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(club))

        }


    }

fun ArrayList<ComputerClubData>.coolToArray() : Array<String> = Array<String>(this.size){x -> this[x].toString()}
