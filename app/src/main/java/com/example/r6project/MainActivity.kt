package com.example.r6project

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.r6project.data.DataModel
import com.example.r6project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val dataModel: DataModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    var pref: SharedPreferences? = null
    var score = 0
    var first = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        binding.bottomNavView.setupWithNavController(navController)
        MAIN = this

        pref = getSharedPreferences("myPref", Context.MODE_PRIVATE)

        println("shiki")

        dataModel.message.observe(this, {
            saveDataInt("score", it)
            score = it
        })

        first = pref?.getBoolean("bool2", false)!!

        if (first) {
            autoCkick()
        }
    }

    fun saveDataInt(key: String, res: Int) {
        val editor = pref?.edit()
        editor?.putInt(key, res)
        editor?.apply()
    }

    fun saveDataStr(key: String, res: String) {
        val editor = pref?.edit()
        editor?.putString(key, res)
        editor?.apply()
    }

    fun saveDataBoo(key: String, res: Boolean): Boolean? {
        val editor = pref?.edit()
        editor?.putBoolean(key, res)
        editor?.apply()
        return pref?.getBoolean(key, false)
    }

    fun autoCkick() {
        val thread = Thread(Runnable {
            while (first) {
                Thread.sleep(pref?.getInt("sec", 0)!!.toLong())
                score = pref?.getInt("score", 0)!!
                score += pref?.getInt("click", 0)!!
                runOnUiThread {
                    dataModel.message.value = score
                    HOME.binding.scoreText.text = "Score: $score"
                    MAIN.saveDataInt("score", score)
                    MAIN.saveDataBoo("autoClick", false)
                }
            }
        })
        thread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveDataBoo("autoFirst", true)
    }
}