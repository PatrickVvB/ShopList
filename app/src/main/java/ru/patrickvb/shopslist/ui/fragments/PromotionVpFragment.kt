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

class PromotionVpFragment : BaseFragment() {

    private lateinit var binding: FragmentVpPromotionsBinding
    private lateinit var promotionAdapter: PromotionVpAdapter
    private val vm: PromotionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vp_promotions, container, false)
        observePromotions()
        vm.getPromotions()
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PromotionFragment().onActivityResult(requestCode, resultCode, data)
    }

    private fun initAdapter(list: ArrayList<Promotion>) {
        promotionAdapter = PromotionVpAdapter(childFragmentManager).apply {
            setPromotionsList(list)
        }
        binding.vpPromotions.apply {
            adapter = promotionAdapter
            setCurrentItem(0, true)
        }
    }

    private fun observePromotions() {
        vm.getPromotionsList().observe(viewLifecycleOwner, {
            it?.let { initAdapter(it) }
        })
    }

    inner class PromotionVpAdapter(fm: FragmentManager)
        : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val promotionsList = ArrayList<Promotion>()

        override fun getCount(): Int {
            return promotionsList.size
        }

        override fun getItem(position: Int): Fragment {
            return PromotionFragment().apply { setPosition(position) }
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
        }

        fun setPromotionsList(list: ArrayList<Promotion>) {
            promotionsList.clear()
            promotionsList.addAll(list)
            notifyDataSetChanged()
        }
    }
}