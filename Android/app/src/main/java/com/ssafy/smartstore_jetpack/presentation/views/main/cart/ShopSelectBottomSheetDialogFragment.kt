package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentShopSelectBottomSheetDialogBinding
import com.ssafy.smartstore_jetpack.domain.model.Shop
import com.ssafy.smartstore_jetpack.presentation.config.BaseBottomSheetDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("InflateParams")
@AndroidEntryPoint
class ShopSelectBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentShopSelectBottomSheetDialogBinding>(R.layout.fragment_shop_select_bottom_sheet_dialog),
    OnMapReadyCallback {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var shopItemAdapter: ShopItemAdapter
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initRecyclerView()
        setFusedLocation()
        setMapFragment()
        setEditTextFocus()

        lifecycleScope.launch {
            viewModel.shoppingUiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is ShoppingListUiEvent.OrderFail -> dismiss()

                    is ShoppingListUiEvent.NeedTagging -> {
                        findNavController().navigate(R.id.action_shop_select_to_shop_dialog)
                    }

                    is ShoppingListUiEvent.LongDistance -> {
                        findNavController().navigate(R.id.action_shop_select_to_t_out_dialog)
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (hasLocationPermission()) {
            enableMyLocation()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPause() {
        super.onPause()

        fusedLocationClient.removeLocationUpdates(locationCallback)
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
            } else {
                Toast.makeText(requireContext(), "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
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
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
            .setMinUpdateIntervalMillis(2000L)
            .setWaitForAccurateLocation(true)
            .build()
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
            if (!::mMap.isInitialized) return
            if (hasLocationPermission()) {
                mMap.isMyLocationEnabled = true
                mMap.setOnMarkerClickListener { marker ->
                    if (viewModel.searchedShops.value.containsKey(marker.title)) {
                        viewModel.searchedShops.value[marker.title]?.let {
                            viewModel.onClickShopSelectInMap(it)
                        }
                    }
                    false
                }

                locationCallback = object : LocationCallback() {

                    override fun onLocationResult(locationResult: LocationResult) {
                        for (location in locationResult.locations) {
                            location?.let {
                                val currentLocation = LatLng(it.latitude, it.longitude)
                                mMap.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        currentLocation,
                                        15F
                                    )
                                )
                                mMap.addMarker(
                                    MarkerOptions().position(currentLocation).title("현재 위치")
                                )
                                viewModel.sortSearchedShop(it)
                            }
                        }
                    }
                }

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )

                addShopsMarkers()
            } else {
                requestLocationPermission() // 권한 요청
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun addShopsMarkers() {
        lifecycleScope.launch {
            viewModel.searchedShops.collectLatest { shops ->
                mMap.clear()
                shops.forEach { shop ->
                    val position = LatLng(shop.value.latitude, shop.value.longitude)
                    mMap.addMarker(
                        MarkerOptions().position(position).title(shop.key)
                            .icon(createCustomMarkerIcon(shop.value))
                    )
                }

                updateCameraToShowAllMarkers(shops.values.toList())
            }
        }
    }

    private fun updateCameraToShowAllMarkers(shops: List<Shop>) {
        if (shops.isEmpty()) return

        val boundsBuilder = LatLngBounds.Builder()

        shops.forEach { shop ->
            val position = LatLng(shop.latitude, shop.longitude)
            boundsBuilder.include(position)
        }

        val bounds = boundsBuilder.build()

        if (shops.size == 1) {
            val singlePosition = LatLng(shops[0].latitude, shops[0].longitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(singlePosition, 15f))
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }
    }

    private fun setEditTextFocus() {
        with(binding) {
            etShopSelect.setOnEditorActionListener { _, actionId, _ ->
                actionId == EditorInfo.IME_ACTION_DONE
            }
        }
    }

    private fun createCustomMarkerIcon(shop: Shop): BitmapDescriptor {
        val markerView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_marker, null)
        val textView = markerView.findViewById<TextView>(R.id.tv_marker)
        val imageView = markerView.findViewById<ImageView>(R.id.iv_marker)
        textView.text = shop.name

        imageView.setBackgroundResource(R.drawable.ic_map_marker)

        markerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        markerView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)
        val bitmap = Bitmap.createBitmap(
            markerView.measuredWidth,
            markerView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        markerView.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}