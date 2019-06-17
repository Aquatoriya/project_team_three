package com.example.cybermap


import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var localDB : DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        localDB = DBHandler(this)

        var tempId = 0
        var tempName = "Gamer"
        var tempAddress = "ул. Есенина, 9к2, Санкт-Петербург, 194354"
        var tempPhone = "8 (812) 599-39-53"
        var tempSite = ""
        var tempHours = "круглосуточно"
        var tempIsAvailable = 0
        var tempCoordinates = "60.040150, 30.334697"

        var values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        localDB.addComputerClub(values)
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

        var computerClubs = localDB.listComputerClubs("%")

        // Add a marker in Sydney and move the camera
        val stPetersburg = LatLng(59.93863, 30.31413)
        mMap.addMarker(MarkerOptions().position(stPetersburg).title("Saint Petersburg"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stPetersburg))

        //val gamer = LatLng(60.04015, 30.334697)
        //mMap.addMarker(MarkerOptions().position(gamer).title("Computer club 'Gamer' "))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(club))

        val gamer = LatLng(computerClubs[0].coordinates[0], computerClubs[0].coordinates[1])
        mMap.addMarker(MarkerOptions().position(gamer).title(computerClubs[0].name))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gamer))
    }

}