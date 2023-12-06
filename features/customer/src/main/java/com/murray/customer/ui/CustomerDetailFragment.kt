package com.murray.customer.ui

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.murray.customer.databinding.FragmentCustomerDetailBinding
import java.io.IOException
import java.util.Locale

class CustomerDetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentCustomerDetailBinding? = null
    private val binding
        get() = _binding!!

    lateinit var gMap: GoogleMap
    lateinit var mapView: MapView
    var address:String = "España"


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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cliente = arguments?.getString("name") ?: ""
        val email = arguments?.getString("email") ?: ""
        val phone = arguments?.getInt("phone") ?: ""
        val city = arguments?.getString("city") ?: ""
        val address = arguments?.getString("address") ?: "España"
        this.address = address
        binding.txtName.text = cliente
        binding.txtEmail.text = email
        binding.txtPhone.text = phone.toString()?: ""
        binding.txtCity.text = city
        binding.txtAddress.text = address.takeIf { it.isNotEmpty() }?: "España"

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)


        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.radio1.id -> gMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                binding.radio2.id-> gMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                binding.radio3.id -> gMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        //var address:String = Address
        var zoom:Float = 16f
        //Lat: 40.46366700000001 Lon: -3.7492199999999998
        val latLng = getLatLngFromAddress(address?: "España")
        if(latLng?.latitude == 40.46366700000001 && latLng?.longitude ==-3.7492199999999998)
            zoom = 4.8f
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
            else{
                val addresses = geocoder.getFromLocationName("España", 1)
                val latitude = addresses?.get(0)?.latitude
                val longitude = addresses?.get(0)?.longitude
                Log.i("TAG", "------------------------------------------")
                Log.i("Datos","Lat: " + latitude + " Lon: " + longitude)
                Log.i("TAG", "------------------------------------------")

                return longitude?.let { latitude?.let { it1 -> LatLng(it1, it) } }
            }
        } catch (e: IOException) {
            val addresses = geocoder.getFromLocationName("España", 1)
            val latitude = addresses?.get(0)?.latitude
            val longitude = addresses?.get(0)?.longitude
            Log.i("TAG", "------------------------------------------")
            Log.i("Datos","Lat: " + latitude + " Lon: " + longitude)
            Log.i("TAG", "------------------------------------------")

            return longitude?.let { latitude?.let { it1 -> LatLng(it1, it) } }
        }
        return null
    }

}


