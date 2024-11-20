package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentShopSelectBottomSheetDialogBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseBottomSheetDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopSelectBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentShopSelectBottomSheetDialogBinding>(R.layout.fragment_shop_select_bottom_sheet_dialog),
    OnMapReadyCallback {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var shopItemAdapter: ShopItemAdapter
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initRecyclerView()
        setFusedLocation()
        setMapFragment()

        lifecycleScope.launch {
            viewModel.shoppingUiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is ShoppingListUiEvent.OrderFail -> dismiss()

                    else -> {}
                }
            }
        }

//        binding.tvConfirmTOutDialog.setOnClickListener {
//            dismiss()
//            viewModel.onClickTakeOutFinish()
//        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (hasLocationPermission()) {
            enableMyLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun initRecyclerView() {
        shopItemAdapter = ShopItemAdapter(viewModel)
        binding.adapter = shopItemAdapter
    }

    private fun setFusedLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun setMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fcv_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun enableMyLocation() {
        try {
            if (hasLocationPermission()) {
                mMap.isMyLocationEnabled = true

                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        val currentLocation = LatLng(it.latitude, it.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
                        mMap.addMarker(MarkerOptions().position(currentLocation).title("현재 위치"))
                    }
                }

                addShopsMarkers()
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun addShopsMarkers() {
        lifecycleScope.launch {
            viewModel.shops.collectLatest { shops ->
                shops.forEach { shop ->
                    val position = LatLng(shop.latitude, shop.longitude)
                    mMap.addMarker(
                        MarkerOptions().position(position).title(shop.name)
                    )
                }
            }
        }
    }

    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}