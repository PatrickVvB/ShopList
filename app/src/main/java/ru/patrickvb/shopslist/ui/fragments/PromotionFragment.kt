package ru.patrickvb.shopslist.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseFragment
import ru.patrickvb.shopslist.databinding.FragmentPromotionBinding
import ru.patrickvb.shopslist.ui.fragments.PromotionVpFragment.Companion.PROMOTION_FRAGMENT_TAG
import ru.patrickvb.shopslist.view_models.PromotionViewModel
import java.io.ByteArrayInputStream

class PromotionFragment : BaseFragment() {

    private lateinit var binding: FragmentPromotionBinding
    private val vm: PromotionViewModel by activityViewModels()
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_promotion, container, false)

        arguments?.let { position = it.getInt(PROMOTION_FRAGMENT_TAG) }

        observeImage()
        observePromotions()
        vm.getPromotionsList().value?.let {
            vm.getPromotionImage(it[position].image)
        }
        return binding.root
    }

    private fun observePromotions() {
        vm.getPromotionsList().observe(viewLifecycleOwner, {promotions ->
            promotions?.let {
                binding.apply {
                    tvStartDate.text = "Старт акции: ${it[position].startDate}"
                    tvEndDate.text = "Конец акции: ${it[position].endDate}"
                    tvOldPrice.text = "Старая цена: ${it[position].oldPrice}"
                    rvNewPrice.text = "Новая цена: ${it[position].price}"
                }
            }
        })
    }

    private fun observeImage() {
        vm.getPromotionImage().observe(viewLifecycleOwner, { bitmap ->
            bitmap?.let {
                val decodeString = Base64.decode(it, Base64.NO_WRAP)
                val inputStream = ByteArrayInputStream(decodeString)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                binding.ivPromotionCover.setImageBitmap(bitmap)
            }
        })
    }
}