package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityHomePageBinding
import com.example.quizapp.fragment.HomeFragment
import com.example.quizapp.fragment.ProfileTabFragment
import com.example.quizapp.fragment.RankFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomePageActivity : AppCompatActivity() {

    private val homeFragment:HomeFragment = HomeFragment()
    private val rankFragment:RankFragment = RankFragment()
    private val profileTabFragment:ProfileTabFragment = ProfileTabFragment()

    private lateinit var binding:ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)




       binding.menu.setOnNavigationItemSelectedListener {
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


}