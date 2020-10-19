package ru.patrickvb.shopslist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseFragment
import ru.patrickvb.shopslist.databinding.FragmentVpPromotionsBinding
import ru.patrickvb.shopslist.models.Promotion
import ru.patrickvb.shopslist.view_models.PromotionViewModel

class PromotionVpFragment(private val shopId: Int, private val shopImage: String) : BaseFragment() {

    lateinit var binding: FragmentVpPromotionsBinding
    private lateinit var promotionAdapter: PromotionVpAdapter
    private val vm: PromotionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vp_promotions, container, false)
        observeImage()
        observePromotions()
        vm.getPromotions(shopId)
        vm.getPromotionImage(shopImage)
        return binding.root
    }

    private fun observePromotions() {
        vm.getPromotionsList().observe(viewLifecycleOwner, { list ->
            list?.let { initAdapter(it) }
        })
    }

    private fun observeImage() {
        vm.getPromotionImage().observe(viewLifecycleOwner, {
            it?.let { initAdapter(vm.getPromotionsList().value!!) }
        })
    }

    private fun initAdapter(promotionsList: ArrayList<Promotion>) {
        if (!vm.getPromotionsList().value.isNullOrEmpty() &&
            vm.getPromotionImage().value != null) {
            promotionAdapter = PromotionVpAdapter(childFragmentManager, promotionsList)
            binding.vpPromotions.apply {
                adapter = promotionAdapter
                setCurrentItem(0, true)
            }
            //собрать картинку
        }
    }

    inner class PromotionVpAdapter(
        fm: FragmentManager,
        private val promotionsList: ArrayList<Promotion>
    ) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return promotionsList.size
        }

        override fun getItem(position: Int): Fragment {
            return PromotionVpFragment(position, "asd")
        }
    }
}