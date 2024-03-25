package com.example.r6project.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.r6project.MAIN
import com.example.r6project.R
import com.example.r6project.adapter.OperAdapter
import com.example.r6project.data.DataModel
import com.example.r6project.data.Oper
import com.example.r6project.databinding.FragmentShopBinding
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File


class ShopFragment : Fragment() {

    lateinit var binding: FragmentShopBinding
    private val objectMapper = ObjectMapper()
    private lateinit var adapter: OperAdapter
    var first = false
    var score = 0
    private val dataModel: DataModel by activityViewModels()
    val allOper = mutableListOf(Oper(R.drawable.mute, "Mute", 2, 500, R.drawable.cooper4),
        Oper(R.drawable.rook, "Rook", 3, 1000, R.drawable.cooper3),
        Oper(R.drawable.jager, "Jager", 4, 1500, R.drawable.cooper2),
        Oper(R.drawable.bandit, "Bandit", 5, 2000, R.drawable.cooper1),
        Oper(R.drawable.sladge, "Sladge", 10, 4000, R.drawable.bronze5),
        Oper(R.drawable.glaz, "Glaz", 12, 5000, R.drawable.bronze4),
        Oper(R.drawable.termit, "Thermite", 15, 7500, R.drawable.bronze3),
        Oper(R.drawable.asha, "Ash", 17, 10000, R.drawable.bronze2),
        Oper(R.drawable.fuze, "Fuze", 20, 12500, R.drawable.bronze1),
        Oper(R.drawable.doki, "Dokkaebi", 22, 14000, R.drawable.silver5),
        Oper(R.drawable.ela, "Ela", 25, 15000, R.drawable.silver4),
        Oper(R.drawable.hibana, "Hibana", 27, 17500, R.drawable.silver3),
        Oper(R.drawable.valkyre, "Valkyre", 30, 19000, R.drawable.silver3),
        Oper(R.drawable.inga, "Ying", 32, 22500, R.drawable.silver2),
        Oper(R.drawable.echo, "Echo", 33, 25000, R.drawable.silver1),
        Oper(R.drawable.lion, "Lion", 35, 27500, R.drawable.golden3),
        Oper(R.drawable.alibi, "Alibi", 37, 30000, R.drawable.golden2),
        Oper(R.drawable.nomad, "Nomad", 40, 33500, R.drawable.golden1),
        Oper(R.drawable.caveira, "Caveira", 42, 35000, R.drawable.dimond3),
        Oper(R.drawable.kali, "Kali", 45, 37500, R.drawable.dimond2),
        Oper(R.drawable.azami, "Azami", 47, 39000, R.drawable.dimond1),
        Oper(R.drawable.mozzie, "Mozzie", 49, 40000, R.drawable.emerald),
        Oper(R.drawable.zero, "Zero",  50, 50000, R.drawable.rubin1)
    )

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentShopBinding.inflate(layoutInflater)

         first = MAIN.pref?.getBoolean("bool", false)!!

         if (!first) {
             set(allOper)
             first = MAIN.saveDataBoo("bool", true)!!
         }

         adapter = OperAdapter { img, name, oneClick, price, rang, index ->
             if (MAIN.pref?.getInt("score", 0)!! >= price) {
                 var setGun = read()
                 score = MAIN.pref?.getInt("score", 0)!!
                 score -= price
                 dataModel.message.value = score
                 MAIN.saveDataInt("score", score)
                 setGun.removeAt(index)
                 if (MAIN.pref?.getInt("oneClick", 1)!! < oneClick) {
                     MAIN.saveDataInt("oneClick", oneClick)
                     MAIN.saveDataStr("name", name)
                     MAIN.saveDataInt("img", img)
                     MAIN.saveDataInt("rang", rang)
                     binding.imgRang.setImageResource(rang)
                 }
                 set(setGun)
                 adapter.setAdapter(setGun)
             } else {
                 Toast.makeText(context, "Вы не можете себе это позволить", Toast.LENGTH_SHORT).show()
             }
         }

         binding.buttonGuns.setOnClickListener {
             MAIN.navController.navigate(R.id.action_shop_to_gunFragment)
         }

         rcvCreate()

         binding.scoreText.text = "Score: " + MAIN.pref?.getInt("score", 0)

         dataModel.message.observe(activity as LifecycleOwner, {
             binding.scoreText.text = "Score: " + it.toString()
         })

         return binding.root
    }

    ///создание файла
    fun set(jsList: MutableList<Oper>) {
        objectMapper.writeValue(File(context?.filesDir!!, "jsonInfo.json"), jsList)
    }
    // чтение json файла
    fun read(): MutableList<Oper> {
        return objectMapper.readValue(File(context?.filesDir!!, "jsonInfo.json"), Array<Oper>::class.java).toMutableList()
    }

    private fun rcvCreate() {
        binding.apply {
            rcv.layoutManager = LinearLayoutManager(context)
            rcv.adapter = adapter
            read().forEach {
            adapter.addOper(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgRang.setImageResource(MAIN.pref?.getInt("rang", R.drawable.cooper5)!!)
    }
}