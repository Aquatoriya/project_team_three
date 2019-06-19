package com.example.cybermap


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    //Vars
    private lateinit var mMap: GoogleMap
    private lateinit var localDB : DBHandler
    private var countOfClubs = 24
    private var arrayOfMarkers = arrayListOf<Marker>()
    private lateinit var computerClubs : ArrayList<ComputerClubData>
    private var nameOfComputerClubs = listOf<String>()
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var location : Location
    private var mLocationPermissionGranted = false

//    //Permisions
//    private var FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
//    private var COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
//    private var mLocationPermissionGranted = false
//    private var LOCATION_PERMISSION_REQUEST_CODE = 1234


    fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(input_search.windowToken, 0)
    }


    fun geolocateToComputerClub() {
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

        initMap()

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        placeMyLocationButton()

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

    private fun placeMyLocationButton() {
        val mapView = mapFragment.view!!
        val locationButton =
            (mapView.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(Integer.parseInt("2"))
        val rlp = locationButton.layoutParams as (RelativeLayout.LayoutParams)
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.setMargins(0, 0, 15, 15);
    }


    private fun initMap() {
        mapFragment = supportFragmentManager
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
                geolocateToComputerClub()
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

        enableMyLocation()

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

    private fun enableMyLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true;
            Log.d("WHY PLS", mFusedLocationProviderClient.lastLocation.isComplete.toString())
            if (mFusedLocationProviderClient.lastLocation.isSuccessful) {
                location = mFusedLocationProviderClient.lastLocation.result!!
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.longitude, location.latitude), 12.0f))
            }
        }
        else {
            Toast.makeText(this, "Do not have a permission to get current location", Toast.LENGTH_SHORT).show()
        }
    }
}
