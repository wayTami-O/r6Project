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
import com.example.r6project.adapter.GunAdapter
import com.example.r6project.data.DataModel
import com.example.r6project.data.Gun
import com.example.r6project.data.Oper
import com.example.r6project.databinding.FragmentGunBinding
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

class GunFragment : Fragment() {

    private val objectMapper = ObjectMapper()
    lateinit var binding: FragmentGunBinding
    private val dataModel: DataModel by activityViewModels()
    private lateinit var adapter: GunAdapter
    var first = false
    var score = 0
    var allGun = mutableListOf(Gun("P12", R.drawable.pistol_kali, 2500, 1, 250),
        Gun("M45 MEUSOC", R.drawable.m45, 2500, 2, 500),
        Gun("LFP586", R.drawable.lfp, 2500, 3, 750),
        Gun("D-50", R.drawable.d_50, 2500, 4, 1000),
        Gun(".44 Mag Semi-Auto", R.drawable.mag44, 2500, 5, 1500),
        Gun("SMG-11", R.drawable.smg11, 2500, 7, 2250),
        Gun("SMG-12", R.drawable.smg12, 2500, 9, 2500),
        Gun("BOSG.12.2", R.drawable.boss12, 2500, 10, 2750),
        Gun("M590A1", R.drawable.m59, 2500, 12, 3000),
        Gun("SG-CQB", R.drawable.sgcqb, 2500, 14, 3500),
        Gun("SuperNova", R.drawable.supernova, 2500, 15, 4000),
        Gun("L85A2", R.drawable.l85a, 2500, 20, 5000),
        Gun("G36C", R.drawable.g36c, 2500, 22, 5500),
        Gun("AK-12", R.drawable.aka12, 2500, 24, 6000),
        Gun("M4", R.drawable.m4, 2500, 25, 6500),
        Gun("MP5K", R.drawable.mp5k, 2500, 27, 7250),
        Gun("MP7", R.drawable.mp7, 2500, 29, 7750),
        Gun("AK-74M", R.drawable.aka74, 2500, 30, 8000),
        Gun("Mk 14 EBR", R.drawable.mk14, 2500, 32, 8500),
        Gun("OTs-03", R.drawable.glaz_gun, 2500, 35, 10000),
        Gun("CSRX 300", R.drawable.vintovka_kali, 2500, 40, 12000)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGunBinding.inflate(layoutInflater)

        binding.imgRang.setImageResource(MAIN.pref?.getInt("rang", R.drawable.cooper5)!!)

        first = MAIN.pref?.getBoolean("boolGun", false)!!

        score = MAIN.pref?.getInt("score", 0)!!

        if (!first) {
            set(allGun)
            first = MAIN.saveDataBoo("boolGun", true)!!
        }

        adapter = GunAdapter { name, img, click, sell, sec, index ->
            if (MAIN.pref?.getInt("score", 0)!! >= sell) {
                var setGun = read()
                setGun.removeAt(index)
                adapter.setAdapter(setGun)
                set(setGun)
                score -= sell
                println(score)
                MAIN.saveDataInt("score", score)
                MAIN.saveDataBoo("bool2", true)
                if (MAIN.pref?.getInt("click",0)!! < click) {
                    MAIN.saveDataInt("sec", sec)
                    MAIN.saveDataInt("click", click)
                    MAIN.saveDataInt("imgGun", img)
                    MAIN.saveDataStr("gunName", name)
                }
                dataModel.message.value = score
                if (!MAIN.first) {
                    MAIN.first = true
                    MAIN.autoCkick()
                }
                MAIN.saveDataInt("score", score)
            } else {
                Toast.makeText(context, "Недостаточно средств", Toast.LENGTH_SHORT).show()
            }
        }

        binding.scoreText.text = "Score: " + MAIN.pref?.getInt("score", 0)

        dataModel.rang.observe(activity as LifecycleOwner, {
            binding.imgRang.setImageResource(it)
        })

        rcvCreate()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOpres.setOnClickListener {
            MAIN.navController.navigate(R.id.action_gunFragment_to_shop)
        }

        dataModel.message.observe(activity as LifecycleOwner, {
            binding.scoreText.text = "Score: " + it.toString()
        })
    }
    ///создание файла
    private fun set(jsList: MutableList<Gun>) {
        objectMapper.writeValue(File(context?.filesDir!!, "jsonGun.json"), jsList)
    }
    // чтение json файла
    private fun read(): MutableList<Gun> {
        return objectMapper.readValue(File(context?.filesDir!!, "jsonGun.json"), Array<Gun>::class.java).toMutableList()
    }

    private fun rcvCreate() {
        binding.apply {
            rcvGun.layoutManager = LinearLayoutManager(context)
            rcvGun.adapter = adapter
            read().forEach {
                adapter.addOper(it)
            }
        }
    }
}