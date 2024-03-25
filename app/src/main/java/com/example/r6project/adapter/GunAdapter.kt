package com.example.r6project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.r6project.R
import com.example.r6project.data.Gun
import com.example.r6project.databinding.GunItemBinding

class GunAdapter(private var listener:(String, Int, Int, Int, Int, Int) -> Unit): RecyclerView.Adapter<GunAdapter.GunHodler>() {

    var gunList = mutableListOf<Gun>()

    inner class GunHodler(item: View): RecyclerView.ViewHolder(item) {
        val binding = GunItemBinding.bind(item)
        fun bind(gun: Gun) = with(binding) {
            imgOperRCV.setImageResource(gun.imgGun)
            nameOperText.text = gun.nameGun
            counterBuy.text = "AutoClick: " + gun.click
            buttonBuyText.text = "Buy: " + gun.sell.toString()
            buttonBuyText.setOnClickListener {
                listener.invoke(gun.nameGun, gun.imgGun, gun.click, gun.sell, gun.sec, adapterPosition)
            }
        }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GunHodler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gun_item, parent, false)
        return GunHodler(view)
    }

    override fun getItemCount(): Int {
        return gunList.size
    }

    override fun onBindViewHolder(holder: GunHodler, position: Int) {
        holder.bind(gunList[position])
    }

    fun addOper(gun: Gun) {
        gunList.add(gun)
        notifyDataSetChanged()
    }

    fun setAdapter(list: List<Gun>) {
        gunList = list as ArrayList<Gun>
        notifyDataSetChanged()
    }
}