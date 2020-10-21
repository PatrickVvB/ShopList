package ru.patrickvb.shopslist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.PagerAdapter
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
        observeToast()
        vm.getPromotions()
        return binding.root
    }

    private fun initAdapter(list: ArrayList<Promotion>) {
        promotionAdapter = PromotionVpAdapter(childFragmentManager).apply {
            setPromotionsList(list)
        }
        binding.vpPromotions.apply { adapter = promotionAdapter }
    }

    private fun observePromotions() {
        vm.getPromotionsList().observe(viewLifecycleOwner, {
            it?.let { initAdapter(it) }
        })
    }

    private fun observeToast() {
        vm.getToast().observe(viewLifecycleOwner, {
            it?.let { showToast(it) }
        })
    }

    inner class PromotionVpAdapter(fm: FragmentManager)
        : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val promotionsList = ArrayList<Promotion>()

        override fun getCount(): Int {
            return promotionsList.size
        }

        override fun getItem(position: Int): Fragment {
            return promotionInstance(position + 1)
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
        }

        override fun getItemPosition(`object`: Any): Int {
            val index = promotionsList.indexOf(`object`)

            return if (index == -1)
                PagerAdapter.POSITION_NONE
            else index
        }

        fun setPromotionsList(list: ArrayList<Promotion>) {
            promotionsList.clear()
            promotionsList.addAll(list)
            notifyDataSetChanged()
        }
    }

    companion object {
        const val PROMOTION_FRAGMENT_TAG = "promotionTag"

        fun promotionInstance(position: Int): PromotionFragment {
            val fragment = PromotionFragment()
            val bundle = Bundle()
            bundle.putInt(PROMOTION_FRAGMENT_TAG, position)
            fragment.arguments = bundle
            return fragment
        }
    }
}