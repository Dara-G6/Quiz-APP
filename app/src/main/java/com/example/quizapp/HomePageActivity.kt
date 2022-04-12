package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.fragment.HomeFragment
import com.example.quizapp.fragment.ProfileTabFragment
import com.example.quizapp.fragment.RankFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomePageActivity : AppCompatActivity() {
    private lateinit var menu: BottomNavigationView
    private val homeFragment:HomeFragment = HomeFragment()
    private val rankFragment:RankFragment = RankFragment()
    private val profileTabFragment:ProfileTabFragment = ProfileTabFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)



        menu = findViewById(R.id.menu)
        menu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                //case select home
                R.id.home-> supportFragmentManager.beginTransaction().replace(R.id.container,homeFragment)
                    .commit()

                //case Select Rank
                R.id.rank-> supportFragmentManager.beginTransaction().replace(R.id.container,rankFragment)
                    .commit()
                //case Select Profile
                R.id.profile-> supportFragmentManager.beginTransaction().replace(R.id.container,profileTabFragment)
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