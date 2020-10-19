package ru.patrickvb.shopslist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseFragment
import ru.patrickvb.shopslist.view_models.MapViewModel

const val AFTER_FRAGMENT = "afterFragment"

class MapFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var map: MapView
    private lateinit var vm: MapViewModel
    private var isFromMain = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        map = view.findViewById(R.id.mv_map)
        if (savedInstanceState != null)
            isFromMain = savedInstanceState.get(AFTER_FRAGMENT) as Boolean
        map.onCreate(savedInstanceState)
        map.getMapAsync(this)
        if (!::vm.isInitialized)
            vm = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        return view
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onMapReady(p0: GoogleMap?) {
        if (isFromMain) {
            vm.getShopList().value?.forEach {
                p0?.addMarker(MarkerOptions().position(LatLng(it.lat, it.lng)).title(it.name))
            }
        } else {
            vm.getCurrentShop().value?.let {
                p0?.addMarker(MarkerOptions().position(LatLng(it.lat, it.lng)).title(it.name))
            } ?: showToast("Магазин не найден")
        }
    }

    fun setMapVM(vm: MapViewModel) {
        this.vm = vm
    }

    fun setFromFragment(flag: Boolean) {
        isFromMain = flag
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        map.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map.onSaveInstanceState(outState)
        outState.putBoolean(AFTER_FRAGMENT, isFromMain)
    }
}