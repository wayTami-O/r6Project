package com.example.r6project.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.r6project.HOME
import com.example.r6project.MAIN
import com.example.r6project.R
import com.example.r6project.data.DataModel
import com.example.r6project.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val dataModel: DataModel by activityViewModels()
    var pref: SharedPreferences? = null
    var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        pref = activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)

        HOME = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var anim = AnimationUtils.loadAnimation(context ,R.anim.anim)

        score = pref?.getInt("score", 0)!!

        binding.operText.text = "Operative: " + pref?.getString("name", "Smoke")
        binding.scoreText.text = "Score: " + score.toString()
        binding.textGun.text = "Gun: " + pref?.getString("gunName", "Sensor")
        pref?.getInt("img", R.drawable.smoke)?.let { binding.operImg.setImageResource(it) }
        pref?.getInt("imgGun", R.drawable.zvuk)?. let { binding.z.setImageResource(it) }
        binding.imgRang.setImageResource(pref?.getInt("rang", R.drawable.cooper5)!!)

        dataModel.message.observe(activity as LifecycleOwner) {
            binding.scoreText.text = "Score: $it"
            MAIN.saveDataInt("score", score)
        }

        binding.operImg.setOnClickListener {
            score = pref?.getInt("score", 0)!!
            binding.operImg.startAnimation(anim)
            score += pref?.getInt("oneClick", 1)!!
            binding.scoreText.text = score.toString()
            dataModel.message.value = score
        }

        dataModel.rang.observe(activity as LifecycleOwner) {
            binding.imgRang.setImageResource(it)
        }

        binding.imgRang.setOnClickListener {
            Toast.makeText(context, "Спасибо Керик!", Toast.LENGTH_SHORT).show()
            //    MAIN.saveDataBoo("bool", false)
            //    MAIN.saveDataBoo("boolGun", false)
            //    MAIN.saveDataInt("score", 0)
            //    MAIN.saveDataBoo("bool2", false)
            //    MAIN.saveDataInt("oneClick", 1)
            //    dataModel.message.value = 0
            //    MAIN.first = false
        }
    }
}