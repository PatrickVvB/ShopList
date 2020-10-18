package ru.patrickvb.shopslist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseFragment
import ru.patrickvb.shopslist.databinding.FragmentShopInfoBinding
import ru.patrickvb.shopslist.view_models.ShopInfoViewModel

class ShopInfoFragment : BaseFragment() {

    lateinit var binding: FragmentShopInfoBinding
    private lateinit var vm: ShopInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_info, container, false)
        observeTypes()
        vm.getAllShopTypes()
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        binding.btnPromotions.setOnClickListener {
            //открыть акции магазина
        }

        binding.btnMap.setOnClickListener {
            //открытие карты
        }
    }

    private fun observeTypes() {
        vm.getShopTypes().observe(viewLifecycleOwner, {types ->
            types?.let {shopTypes ->
                shopTypes.forEach {
                    if (it.id == vm.getShop().value?.type)
                        binding.tvShopType.text = it.name
                    else binding.tvShopType.text = "Без типа"
                }
            }
        })
    }

    fun setVM(vm: ShopInfoViewModel) {
        this.vm = vm
    }
}