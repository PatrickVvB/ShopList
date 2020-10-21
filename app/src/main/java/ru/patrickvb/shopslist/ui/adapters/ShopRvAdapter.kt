package ru.patrickvb.shopslist.ui.adapters

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.text.format.DateFormat.format
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ru.patrickvb.shopslist.R
import ru.patrickvb.shopslist.databinding.ItemShopBinding
import ru.patrickvb.shopslist.getLocation
import ru.patrickvb.shopslist.models.Shop
import ru.patrickvb.shopslist.models.SortShops
import ru.patrickvb.shopslist.ui.activities.MainActivity
import ru.patrickvb.shopslist.ui.fragments.ShopInfoFragment
import ru.patrickvb.shopslist.view_models.ShopInfoViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ShopRvAdapter : RecyclerView.Adapter<ShopRvAdapter.ShopViewHolder>() {

    private val shopList = ArrayList<Shop>()
    private lateinit var locationListener: LocationListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        return ShopViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_shop, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(shopList[position])
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    fun setShopList(shops: ArrayList<Shop>, context: Context) {
        locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location) {

                Collections.sort(shops, SortShops(p0.latitude, p0.longitude))

                shopList.apply {
                    clear()
                    addAll(shops)
                }
                notifyDataSetChanged()
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                //super.onStatusChanged(provider, status, extras) - супер реализация приводит в вылету
            }
        }

        getLocation(context, locationListener)
    }

    inner class ShopViewHolder(private val binding: ItemShopBinding)
        : RecyclerView.ViewHolder(binding.root) {

        private var sdf: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
        private val nowTime: Date = sdf.parse(format("HH:mm", Date()) as String)
        private var workTime =""

        fun bind(shop: Shop) {

            shop.name?.let { binding.tvShopName.text = it }

            shop.opening?.let {
                workTime = "Открыт с $it до ${shop.closing}"

                binding.tvShopState.apply {
                    val openTime: Date? = sdf.parse(it)
                    val closeTime: Date? = sdf.parse(shop.closing)
                    if (nowTime.after(openTime) && nowTime.before(closeTime)) {
                        text = "Открыто"
                        setTextColor(resources.getColor(R.color.colorOpen, null))
                    } else {
                        text = "Закрыто"
                        setTextColor(resources.getColor(R.color.colorClose, null))
                    }
                }
            }

            binding.tvShopWorkTime.text = workTime

            binding.ivShopCover.setImageResource(
                when (shop.type) {
                    1 -> R.drawable.ic_md_24
                    2 -> R.drawable.ic_gm_24
                    else -> R.drawable.ic_mk_24
                }
            )

            binding.root.setOnClickListener {
                shop.name?.let {
                    ViewModelProvider((binding.root.context as MainActivity)).get(ShopInfoViewModel::class.java).setShop(shop)
                    val fragment = ShopInfoFragment()
                    (binding.root.context as MainActivity).addFragment(fragment)
                }
            }
        }
    }
}