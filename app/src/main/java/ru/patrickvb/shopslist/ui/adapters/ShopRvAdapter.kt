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
import ru.patrickvb.shopslist.ui.activities.MainActivity
import ru.patrickvb.shopslist.ui.fragments.ShopInfoFragment
import ru.patrickvb.shopslist.view_models.ShopInfoViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.*

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
        shopList.apply {
            clear()
            addAll(shops)
        }
        notifyDataSetChanged()

        locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                val lat2 = p0.latitude
                val lng2 = p0.longitude
                shopList.sortedWith(compareBy { distance(it.lat, it.lng, lat2, lng2)})
                notifyDataSetChanged()
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                //super.onStatusChanged(provider, status, extras) - супер реализация приводит в вылету
            }
        }

        getLocation(context, locationListener)
    }

    private fun distance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {

        val earthRadius = 3958.75

        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)

        val sindLat = sin(dLat / 2)
        val sindLng = sin(dLng / 2)

        val a = sindLat.pow(2) +
                sindLng.pow(2) *
                cos(Math.toRadians(lat1)) *
                cos(Math.toRadians(lat2))

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
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