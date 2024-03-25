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
import com.example.r6project.databinding.FragmentWinBinding

class WinFragment : Fragment() {

    lateinit var binding: FragmentWinBinding
    private val dataModel: DataModel by activityViewModels()
    var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWinBinding.inflate(layoutInflater)
        score = MAIN.pref?.getInt("score", 0)!!
        var win = randomWin()
        var result = score + win
        binding.textView6.text = win.toString()
        dataModel.message.value = result
        MAIN.saveDataInt("score", result)

        return binding.root
    }

    private fun randomWin(): Int {
        return (500..1100).random()
    }
}