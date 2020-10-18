package ru.patrickvb.shopslist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseFragment
import ru.patrickvb.shopslist.databinding.FragmentMainBinding
import ru.patrickvb.shopslist.ui.adapters.ShopRvAdapter
import ru.patrickvb.shopslist.view_models.MainViewModel

class MainFragment : BaseFragment() {

    lateinit var binding: FragmentMainBinding
    private val vm: MainViewModel by activityViewModels()
    private val mainAdapter = ShopRvAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        initAdapter()
        observeShopList()
        vm.getShops()

        binding.fabMap.setOnClickListener {
            //Открытие карты
        }
        return binding.root
    }

    private fun initAdapter() {
        binding.rvShopsList.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun observeShopList() {
        vm.getShopList().observe(viewLifecycleOwner, {
            it?.let {
                mainAdapter.setShopList(it)
            }
        })
    }
}