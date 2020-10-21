package ru.patrickvb.shopslist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseFragment
import ru.patrickvb.shopslist.databinding.FragmentMainBinding
import ru.patrickvb.shopslist.ui.adapters.ShopRvAdapter
import ru.patrickvb.shopslist.view_models.MainViewModel
import ru.patrickvb.shopslist.view_models.MapViewModel

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
        observeShopList()
        observeToast()
        vm.getShops()

        binding.fabMap.apply {
            visibility = View.GONE
            setOnClickListener {
                vm.getShopList().value?.let {
                    ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
                        .setShopList(it)
                }

                val fragment = MapFragment().apply { setFromFragment(true) }
                addFragment(fragment)
            }
        }
        initAdapter()
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
                mainAdapter.setShopList(it, requireContext())
                binding.fabMap.visibility = View.VISIBLE
                binding.pbMain.visibility = View.GONE
            }
        })
    }

    private fun observeToast() {
        vm.getToast().observe(viewLifecycleOwner, {
            it?.let { showToast(it) }
        })
    }
}