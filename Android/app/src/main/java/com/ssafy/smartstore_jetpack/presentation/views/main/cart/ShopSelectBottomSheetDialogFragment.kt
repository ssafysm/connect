package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentShopSelectBottomSheetDialogBinding
import com.ssafy.smartstore_jetpack.domain.model.Shop
import com.ssafy.smartstore_jetpack.presentation.config.BaseBottomSheetDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("ClickableViewAccessibility")
@AndroidEntryPoint
class ShopSelectBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentShopSelectBottomSheetDialogBinding>(R.layout.fragment_shop_select_bottom_sheet_dialog),
    OnMapReadyCallback {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var shopItemAdapter: ShopItemAdapter
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override lateinit var behavior: BottomSheetBehavior<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initRecyclerView()
        setFusedLocation()
        setMapFragment()
        setEditTextFocus()

        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
            binding.nsvShopSelect.isNestedScrollingEnabled = false
        }

        lifecycleScope.launch {
            viewModel.shoppingUiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is ShoppingListUiEvent.OrderFail -> dismiss()

                    is ShoppingListUiEvent.NeedTagging -> {
                        findNavController().navigate(R.id.action_shop_select_to_shop_dialog)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun expandBottomSheetAndDisableScroll() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
        binding.nsvShopSelect.isNestedScrollingEnabled = false
        Timber.d("맵 모드")
    }

    private fun enableScrollAndCollapseBottomSheet() {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.isDraggable = true
        binding.nsvShopSelect.isNestedScrollingEnabled = true
        Timber.d("맵 모드 아님")
    }

    override fun onStart() {
        super.onStart()

        dialog?.let { dialog ->
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = true

            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
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

    @SuppressLint("ClickableViewAccessibility")
    private fun enableMyLocation() {
        try {
            if (hasLocationPermission()) {
                mMap.isMyLocationEnabled = true
                mMap.setOnMarkerClickListener { marker ->
                    viewModel.shops.value.forEach { shop ->
                        if (marker.title == shop.id) {
                            viewModel.onClickShopSelectInMap(shop)
                        }
                    }
                    true
                }

                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        val currentLocation = LatLng(it.latitude, it.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))
                        mMap.addMarker(MarkerOptions().position(currentLocation).title("현재 위치"))
                        viewModel.sortSearchedShop(it)
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
            viewModel.searchedShops.collectLatest { shops ->
                mMap.clear()
                shops.forEach { shop ->
                    val position = LatLng(shop.latitude, shop.longitude)
                    mMap.addMarker(
                        MarkerOptions().position(position).title(shop.id)
                            .icon(createCustomMarkerIcon(shop))
                    )
                }

                updateCameraToShowAllMarkers(shops)
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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(singlePosition, 15f)) // 15f는 줌 레벨
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

        when (viewModel.appThemeName.value) {
            "봄" -> {
                imageView.setBackgroundResource(R.drawable.ic_shop_spring)
            }

            "여름" -> {
                imageView.setBackgroundResource(R.drawable.ic_shop_summer)
            }

            "가을" -> {
                imageView.setBackgroundResource(R.drawable.ic_shop_autumn)
            }

            "겨울" -> {
                imageView.setBackgroundResource(R.drawable.ic_shop_winter)
            }

            else -> {
                imageView.setBackgroundResource(R.drawable.ic_shop)
            }
        }
        imageView.load(shop.image) {
            transformations(CircleCropTransformation())
        }

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