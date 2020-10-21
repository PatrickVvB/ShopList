package ru.patrickvb.shopslist.ui.fragments

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.base.BaseFragment
import ru.patrickvb.shopslist.databinding.FragmentPromotionBinding
import ru.patrickvb.shopslist.ui.fragments.PromotionVpFragment.Companion.PROMOTION_FRAGMENT_TAG
import ru.patrickvb.shopslist.view_models.PromotionViewModel

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
        observeToast()
        vm.getPromotionsList().value?.let {
            vm.getPromotionImage(it[position].image)
        }
        return binding.root
    }

    private fun observePromotions() {
        vm.getPromotionsList().observe(viewLifecycleOwner, { promotions ->
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

    //устанавливаем картинку
    private fun observeImage() {
        vm.getPromotionImage().observe(viewLifecycleOwner, { bitmap ->
            bitmap?.let {
                val circularProgressDrawable = CircularProgressDrawable(
                    requireContext()
                ).apply {
                    strokeWidth = 5f
                    centerRadius = 48f
                    start()
                }
//                val base64Image = it.split(",")[1]
//                val decodeString = Base64.decode(base64Image, Base64.DEFAULT)
//                val image = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.size)
//                binding.ivPromotionCover.setImageBitmap(image)
                val imageByteArray = Base64.decode(it, Base64.DEFAULT)
                Glide
                    .with(requireContext())
                    .asBitmap()
                    .load(imageByteArray)
                    .placeholder(circularProgressDrawable)
                    .into(binding.ivPromotionCover)
            }
        })
    }

    private fun observeToast() {
        vm.getToast().observe(viewLifecycleOwner, {
            it?.let { showToast(it) }
        })
    }
}