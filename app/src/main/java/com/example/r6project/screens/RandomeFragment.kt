package com.example.r6project.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.r6project.MAIN
import com.example.r6project.R
import com.example.r6project.data.DataModel
import com.example.r6project.databinding.FragmentRandomeBinding

class RandomeFragment : Fragment() {

    lateinit var binding: FragmentRandomeBinding
    private val dataModel: DataModel by activityViewModels()
    var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomeBinding.inflate(layoutInflater)
        binding.scoreText.text = "Score: " + MAIN.pref?.getInt("score", 0)
        binding.imgRang.setImageResource(MAIN.pref?.getInt("rang", R.drawable.cooper5)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRandom.setOnClickListener {
            score = MAIN.pref?.getInt("score", 0)!!
            if (score >= 750){
                score -= 750
                MAIN.saveDataInt("score", score)
                MAIN.supportFragmentManager.beginTransaction().replace(R.id.holder_win, WinFragment()).commit()
            }
        }
        dataModel.message.observe(activity as LifecycleOwner, {
            binding.scoreText.text = "Score: " + it.toString()
        })
        dataModel.rang.observe(activity as LifecycleOwner, {
            binding.imgRang.setImageResource(it)
        })
    }
}