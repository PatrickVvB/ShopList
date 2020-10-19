package ru.patrickvb.shopslist.ui.fragments

import android.content.Intent
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

class PromotionVpFragment(private val shopId: Int, private val promotionId: Int) : BaseFragment() {

    private lateinit var binding: FragmentVpPromotionsBinding
    private lateinit var promotionAdapter: PromotionVpAdapter
    private val vm: PromotionViewModel by activityViewModels()
    private val promotionFragment = PromotionFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vp_promotions, container, false)
        observeImage()
        observePromotions()
        vm.getPromotions(shopId)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        promotionFragment.onActivityResult(requestCode, resultCode, data)
    }

    private fun observePromotions() {
        vm.getPromotionsList().observe(viewLifecycleOwner, { list ->
            list?.let { initAdapter(it) }
        })
    }

    private fun observeImage() {
        vm.getPromotionImage().observe(viewLifecycleOwner, {
            //собрать картинку
        })
    }

    private fun initAdapter(promotionsList: ArrayList<Promotion>) {
        promotionAdapter = PromotionVpAdapter(childFragmentManager, promotionsList)
        binding.vpPromotions.apply {
            adapter = promotionAdapter
            setCurrentItem(promotionId, true)
        }
        vm.getPromotionImage(promotionsList[promotionId].image)
    }

    inner class PromotionVpAdapter(
        fm: FragmentManager,
        private val promotionsList: ArrayList<Promotion>
    ) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return promotionsList.size
        }

        override fun getItem(position: Int): Fragment {
            return getInstance(shopId, position)
        }
    }

    companion object {
        fun getInstance(shopId: Int, position: Int): PromotionVpFragment {
            return PromotionVpFragment(shopId, position)
        }
    }
}