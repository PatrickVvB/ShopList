package ru.patrickvb.shopslist.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseFragment
import ru.patrickvb.shopslist.databinding.FragmentShopInfoBinding
import ru.patrickvb.shopslist.view_models.MapViewModel
import ru.patrickvb.shopslist.view_models.ShopInfoViewModel
import java.text.SimpleDateFormat
import java.util.*

class ShopInfoFragment : BaseFragment() {

    lateinit var binding: FragmentShopInfoBinding
    private lateinit var vm: ShopInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_info, container, false)
        binding.shop = vm.getShop().value
        observeTypes()
        vm.getAllShopTypes()
        initButtons()
        binding.tvShopStatus.text = if (calculateShopStatus()) "Открыт" else "Закрыт"
        return binding.root
    }

    private fun initButtons() {
        binding.btnPromotions.setOnClickListener {
            vm.getShop().value?.let { addFragment(PromotionVpFragment(it.id, 0)) }
        }

        binding.btnMap.setOnClickListener {
            val mvm = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
            vm.getShop().value?.let { mvm.setCurrentShop(it) }

            val fragment = MapFragment().apply {
                setFromFragment(false)
                setMapVM(mvm)
            }
            addFragment(fragment)
        }
    }

    private fun observeTypes() {
        vm.getShopTypes().observe(viewLifecycleOwner, {types ->
            types?.let {shopTypes ->
                shopTypes.forEach {
                    if (it.id == vm.getShop().value?.type) {
                        binding.tvShopType.text = it.name
                        return@observe
                    }
                    else binding.tvShopType.text = "Без типа"
                }
            }
        })
    }

    private fun calculateShopStatus(): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.ROOT)
        val today = DateFormat.format("HH:mm", Date()) as String
        val nowTime: Date = sdf.parse(today)
        val openTime: Date? = sdf.parse(vm.getShop().value?.opening)
        val closeTime: Date? = sdf.parse(vm.getShop().value?.closing)
        return nowTime.after(openTime) && nowTime.before(closeTime)
    }

    fun setShopInfoVM(vm: ShopInfoViewModel) {
        this.vm = vm
    }
}