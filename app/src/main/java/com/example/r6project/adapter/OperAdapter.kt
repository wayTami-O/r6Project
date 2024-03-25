package com.example.r6project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.r6project.R
import com.example.r6project.data.Oper
import com.example.r6project.databinding.OperItemBinding

class OperAdapter(private var listener:(Int, String, Int, Int, Int, Int) -> Unit): RecyclerView.Adapter<OperAdapter.OperHodler>() {

    var operList = mutableListOf<Oper>()

    inner class OperHodler(item: View): RecyclerView.ViewHolder(item) {
        val binding = OperItemBinding.bind(item)
        fun bind(oper: Oper) = with(binding) {
            imgOperRCV.setImageResource(oper.img)
            nameOperText.text = oper.name
            counterBuy.text = "One Click: " + oper.oneClick
            buttonBuyText.text = "Buy: " + oper.price.toString()
            buttonBuyText.setOnClickListener {
                listener.invoke(oper.img, oper.name, oper.oneClick, oper.price, oper.rang, adapterPosition)
            }
        }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperHodler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.oper_item, parent, false)
        return OperHodler(view)
    }

    override fun getItemCount(): Int {
        return operList.size
    }

    override fun onBindViewHolder(holder: OperHodler, position: Int) {
        holder.bind(operList[position])
    }

    fun addOper(oper: Oper) {
        operList.add(oper)
        notifyDataSetChanged()
    }

    fun setAdapter(list: List<Oper>) {
        operList = list as ArrayList<Oper>
        notifyDataSetChanged()
    }
}