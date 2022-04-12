package com.example.quizapp.fragment

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.Question.MathQuestion
import com.example.quizapp.R
import com.example.quizapp.adapter.AdaperRank
import com.example.quizapp.adapter.Rank
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class RankFragment : Fragment() {





    //Menu
    private lateinit var menu:BottomNavigationView


    private lateinit var ListMath:ListView
    private lateinit var ListScience:ListView
    private lateinit var ListGeneral:ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_rank, container, false)

       menu = view.findViewById(R.id.menu)
       menu.setOnNavigationItemSelectedListener{
           when(it.itemId){
               R.id.TabMath->{
                   Log.d("MAth","")
                   OnSelectMath()
               }
               R.id.TabScience->{
                   Log.d("Science","")
                   OnSelectScience()
               }
               R.id.TabGeneral->{
                   Log.d("General","")
                   OnSelectGeneral()
               }
           }
           true}


        ListMath = view.findViewById(R.id.ListMath)
        ListGeneral = view.findViewById(R.id.ListGeneral)
        ListScience =view.findViewById(R.id.ListScience)
        OnSelectMath()

        return view
    }

    private fun OnSelectMath(){
        ListScience.isVisible = false
        ListGeneral.isVisible=false
        ListMath.isVisible = true
        val l = ArrayList<Rank>()
        val database = FirebaseDatabase.getInstance().getReference("Math")
        database.orderByChild("Point").get().addOnSuccessListener {
            if (it.exists()){
                for (ds in it.children){
                    val ID = ds.child("ID").getValue().toString()
                    val Name = ds.child("Name").getValue().toString()
                    val Path = ds.child("Path").getValue().toString()
                    val Point = ds.child("Point").getValue().toString()
                    val Time = ds.child("Time").getValue().toString().toDouble()/1000

                    if (Time !=0.0 && Point.toString().toLong()!= 0L){
                        l.add(
                            Rank(
                                ID,
                                Name,
                                Path,
                                Point,
                                Time.toString()
                            )
                        )
                    }

                }
                l.reverse()
                val adapter = AdaperRank(requireContext(),R.layout.adapter_rank,l)
                ListMath.adapter =adapter
            }
        }

    }

    private fun OnSelectScience(){
        ListScience.isVisible = true
        ListGeneral.isVisible=false
        ListMath.isVisible = false
    }

    private fun OnSelectGeneral(){
        ListScience.isVisible = false
        ListGeneral.isVisible=true
        ListMath.isVisible = false
    }







}