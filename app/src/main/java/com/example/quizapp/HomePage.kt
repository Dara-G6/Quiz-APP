package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.fragment.Home
import com.example.quizapp.fragment.ProfileTab
import com.example.quizapp.fragment.Rank
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomePage : AppCompatActivity() {
    private lateinit var menu: BottomNavigationView
    private val home:Home = Home()
    private val rank:Rank = Rank()
    private val profileTab:ProfileTab = ProfileTab()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)



        menu = findViewById(R.id.menu)
        menu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                //case select home
                R.id.home-> supportFragmentManager.beginTransaction().replace(R.id.container,home)
                    .commit()

                //case Select Rank
                R.id.rank-> supportFragmentManager.beginTransaction().replace(R.id.container,rank)
                    .commit()
                //case Select Profile
                R.id.profile-> supportFragmentManager.beginTransaction().replace(R.id.container,profileTab)
                    .commit()
            }
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}