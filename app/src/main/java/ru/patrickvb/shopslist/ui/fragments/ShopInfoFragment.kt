package ru.patrickvb.shopslist.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseFragment
import ru.patrickvb.shopslist.databinding.FragmentShopInfoBinding
import ru.patrickvb.shopslist.view_models.MapViewModel
import ru.patrickvb.shopslist.view_models.PromotionViewModel
import ru.patrickvb.shopslist.view_models.ShopInfoViewModel
import java.text.SimpleDateFormat
import java.util.*

class ShopInfoFragment : BaseFragment() {

    lateinit var binding: FragmentShopInfoBinding
    private val vm: ShopInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_info, container, false)
        observeTypes()
        observeShop()
        observeToast()
        vm.getAllShopTypes()
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        binding.btnPromotions.setOnClickListener {
            vm.getShop().value?.let {
                ViewModelProvider(requireActivity()).get(PromotionViewModel::class.java).setShopId(it.id)
                addFragment(PromotionVpFragment())
            }
        }

        binding.btnMap.setOnClickListener {
            vm.getShop().value?.let {
                ViewModelProvider(requireActivity()).get(MapViewModel::class.java).setCurrentShop(it)
            }

            val fragment = MapFragment().apply { setFromFragment(false) }
            addFragment(fragment)
        }
    }

    private fun observeShop() {
        vm.getShop().observe(viewLifecycleOwner, {shop ->
            shop?.let {
                binding.apply {
                    tvShopName.text = it.name
                    tvShopAddress.text = it.address
                    tvShopOpenTime.text = "Время открытия: ${it.opening}"
                    tvShopCloseTime.text = "Время закрытия: ${it.closing}"
                    tvShopStatus.text = if (calculateShopStatus()) "Открыт" else "Закрыт"
                }
            }
        })
    }

    //устанавливаем тип магазина
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

    private fun observeToast() {
        vm.getToast().observe(viewLifecycleOwner, {
            it?.let { showToast(it) }
        })
    }

    //магазин открыт или закрыт
    private fun calculateShopStatus(): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.ROOT)
        val today = DateFormat.format("HH:mm", Date()) as String
        val nowTime: Date = sdf.parse(today)
        val openTime: Date? = sdf.parse(vm.getShop().value?.opening)
        val closeTime: Date? = sdf.parse(vm.getShop().value?.closing)
        return nowTime.after(openTime) && nowTime.before(closeTime)
    }
}