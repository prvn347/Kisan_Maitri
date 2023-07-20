package com.example.gecp
import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.gecp.daos.PostDao
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class CreatePostActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val postButton = findViewById<Button>(R.id.postButton)
        val postInput = findViewById<EditText>(R.id.postInput)
        val locationButton = findViewById<Button>(R.id.locationButton)
        val locationTextView = findViewById<TextView>(R.id.locationTextView)
        postDao = PostDao()
        postButton.setOnClickListener {
            val input = postInput.text.toString().trim()
            val location = locationTextView.text.toString().trim()

            if(input.isNotEmpty()) {
                postDao.addPost(input)
                finish()
            }

        }


        locationButton.setOnClickListener(){
            getCurrentLocation()
            requestLocationPermission()
        }

    }
    private val locationPermissionCode = 1

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {


            // Permission granted, handle location retrieval
            // Implement the logic to get the user's current location here
        } else {
            // Permission denied, handle accordingly
        }
    }
    private fun getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Get the latitude and longitude from the location object
                val latitude = location?.latitude
                val longitude = location?.longitude
                val locationTextView = findViewById<TextView>(R.id.locationTextView)

                // Perform reverse geocoding
                val geocoder = Geocoder(this, Locale.getDefault())
                val addressList = geocoder.getFromLocation(latitude!!, longitude!!, 1)

                if (addressList != null) {
                    if (addressList.isNotEmpty()) {
                        val address = addressList?.get(0)
                        val actualLocation = address?.getAddressLine(0)

                        // Update the locationTextView with the actual location
                        locationTextView.text = "Location: $actualLocation"
                    } else {
                        locationTextView.text = "Location: Not Available"
                    }
                }
            }
            .addOnFailureListener { exception: Exception ->
                val locationTextView = findViewById<TextView>(R.id.locationTextView)
                // Handle any errors that occurred while retrieving the location
                locationTextView.text = "Location: Not Available"
            }
    }
}