package com.example.cybermap


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap

    private lateinit var localDB : DBHandler

    private var countOfClubs = 24

    private var arrayOfMarkers = arrayListOf<Marker>()

    private lateinit var computerClubs : ArrayList<ComputerClubData>

    private var nameOfComputerClubs = listOf<String>()

    //Permisions
    private var FINE_LOCATOIN = android.Manifest.permission.ACCESS_FINE_LOCATION
    private var COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private var mLocationPermissionGranted = false
    private var LOCATION_PERMISSION_REQUEST_CODE = 1234


    fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(input_search.getWindowToken(), 0)
    }

    fun geoLocateToComputerClub() {
        val searchString = input_search.text.toString()

        val club = arrayOfMarkers.find { it -> it.title == searchString }

        if (club != null) {
            Log.d("geoLocator found: ", club.title)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(club.position, 12.0f))
        }
        else {
            Log.d("geoLocator didnt found", "NOO")
            Toast.makeText(this, "Did not found a computer club", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        supportActionBar!!.hide()

        getLocationPermission()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        localDB = DBHandler(this)
        localDB.sqlObj.delete("computerClubsTable", null, null)
        localDB.addAllComputerClubs()
        computerClubs = localDB.listComputerClubs("%")
        computerClubs.sortBy { it._id }

        for (x in computerClubs) {
            nameOfComputerClubs = nameOfComputerClubs + x.name
        }

        val adapter = ArrayAdapter<String>(this, R.layout.custom_list_item, nameOfComputerClubs)
        input_search.setAdapter(adapter)

    }

    private fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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


        input_search.setOnEditorActionListener { text: TextView, actionId:Int, keyEvent: KeyEvent?? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                geoLocateToComputerClub()
                input_search.setText("")
                this.hideKeyboard()
                input_search.clearFocus()
            }
            else {
            }
            return@setOnEditorActionListener true
        }

        for (i in computerClubs.indices){
            val club = LatLng(computerClubs[i].coordinates[0], computerClubs[i].coordinates[1])
            arrayOfMarkers.add(mMap.addMarker(MarkerOptions().position(club).title(computerClubs[i].name)))

        }

        mMap.setOnMarkerClickListener {it ->
            val intent = Intent(this, InfoActivity::class.java)
            val ind = arrayOfMarkers.indexOf(it)

            intent.putExtra("name", computerClubs[ind].name)
            intent.putExtra("address", computerClubs[ind].address)
            intent.putExtra("phone", computerClubs[ind].phone)
            intent.putExtra("site", computerClubs[ind].site)
            intent.putExtra("hours", computerClubs[ind].hours)
            intent.putExtra("isAvailableOnlineBooking", computerClubs[ind].isAvailableOnlineBooking)
            //intent.putExtra("coordinates", computerClubs[ind].coordinates)
            intent.putExtra("images", computerClubs[ind].images.toIntArray())

            startActivity(intent)
            return@setOnMarkerClickListener true

        }




        val stPetersburg = LatLng(59.93863, 30.31413)
        mMap.addMarker(MarkerOptions().position(stPetersburg).title("Saint Petersburg"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stPetersburg, 12.0f))


        //val gamer = LatLng(60.04015, 30.334697)
        //mMap.addMarker(MarkerOptions().position(gamer).title("Computer club 'Gamer' "))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(club))

    }



    fun getLocationPermission() {
        val permissions = arrayOf<String>(FINE_LOCATOIN, COURSE_LOCATION)

        if (ContextCompat.checkSelfPermission(this.applicationContext,
                FINE_LOCATOIN) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.applicationContext,
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true
            }
            else {
                ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
        else {
            ActivityCompat.requestPermissions(this, permissions,
                LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mLocationPermissionGranted = false

        when(requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                   for (i in 0 until grantResults.size) {
                       if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                           mLocationPermissionGranted = false
                           return
                       }
                   }
                    mLocationPermissionGranted = true
                    initMap()
                }
            }
        }
    }
}
