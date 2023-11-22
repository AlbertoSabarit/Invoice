package com.murray.customer.ui

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.murray.customer.databinding.FragmentCustomerDetailBinding

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.Locale

class CustomerDetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentCustomerDetailBinding? = null
    private var address:String = "España"
    private val binding
        get() = _binding!!

    lateinit var gMap: GoogleMap
    lateinit var mapView: MapView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerDetailBinding.inflate(inflater, container, false)
        address = binding.txtAddress.text.toString()
        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        //var address:String = Address
        var zoom:Float = 16f
        if(address.equals("España"))
            zoom = 4.5f
        val latLng = getLatLngFromAddress(address)

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng!!, zoom))
        gMap.addMarker(MarkerOptions().position(latLng!!).title("Casa"))
    }

    private fun getLatLngFromAddress(address: String): LatLng? {

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocationName(address, 1)
            if (addresses?.isNotEmpty() == true) {
                val latitude = addresses?.get(0)?.latitude
                val longitude = addresses?.get(0)?.longitude
                return longitude?.let { latitude?.let { it1 -> LatLng(it1, it) } }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}


