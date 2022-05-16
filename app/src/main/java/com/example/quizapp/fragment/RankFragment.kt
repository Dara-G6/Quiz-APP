package com.example.quizapp.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.isVisible
import com.example.quizapp.Question.MathQuestion
import com.example.quizapp.R
import com.example.quizapp.adapter.AdaperRank
import com.example.quizapp.databinding.FragmentRankBinding
import com.example.quizapp.dataclass.Rank
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class RankFragment : Fragment() {




    //
    private lateinit var binding: FragmentRankBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankBinding.inflate(inflater,container,false)

        binding.menu.selectedItemId=R.id.TabMath
        OnSelectMath()
        binding.menu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.TabMath -> {
                    OnSelectMath()
                }
                R.id.TabScience -> {
                    OnSelectScience()
                }
                R.id.TabGeneral -> {
                    OnSelectGeneral()
                }
            }
            true
        }


            // OnSelectMath()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.menu.selectedItemId=R.id.TabMath
        OnSelectMath()
    }
    private fun OnSelectMath() {
        binding.ListScience.isVisible = false
        binding.ListGeneral.isVisible = false
        binding.ListMath.isVisible = true
        binding.TypeRank.setImageResource(R.drawable.math_rank)
        binding.TextRank.setText(R.string.math)
        val l = ArrayList<Rank>()
        Handler().postDelayed(
            {
                val database = FirebaseDatabase.getInstance().getReference("Math")
                database.orderByChild("Point").get().addOnSuccessListener {
                    if (it.exists()) {
                        for (ds in it.children) {
                            val ID = ds.child("ID").getValue().toString()
                            val Name = ds.child("Name").getValue().toString()
                            val Path = ds.child("Path").getValue().toString()
                            val Point = ds.child("Point").getValue().toString()
                            val Time = ds.child("Time").getValue().toString().toDouble() / 1000

                            if (Time !=0.0 && Point.toString().toLong()!= 0L){
                                l.add(
                                    Rank(
                                        ID,
                                        Name,
                                        Path,
                                        Point.toLong(),
                                        Time
                                    )
                                )
                            }

                        }

                        l.sortBy {
                            it.Time
                        }
                        l.sortByDescending {
                            it.Point.toLong()
                        }


                        val adapter = AdaperRank(requireContext(), R.layout.adapter_rank, l)
                        binding.ListMath.adapter = adapter
                    }
                }}
            ,0
        )


    }

    private fun OnSelectScience() {
        binding.ListScience.isVisible = true
        binding.ListGeneral.isVisible = false
        binding.ListMath.isVisible = false
        binding.TypeRank.setImageResource(R.drawable.chemist_rank)
        binding.TextRank.setText(R.string.science)
        val l = ArrayList<Rank>()
        Handler().postDelayed(
            {
                val database = FirebaseDatabase.getInstance().getReference("Science")
                database.get().addOnSuccessListener {
                    if (it.exists()) {
                        for (ds in it.children) {
                            val ID = ds.child("ID").getValue().toString()
                            val Name = ds.child("Name").getValue().toString()
                            val Path = ds.child("Path").getValue().toString()
                            val Point = ds.child("Point").getValue().toString()
                            val Time = ds.child("Time").getValue().toString().toDouble() / 1000

                            if (Time != 0.0 && Point.toString().toLong() != 0L) {
                                l.add(
                                    Rank(
                                        ID,
                                        Name,
                                        Path,
                                        Point.toLong(),
                                        Time
                                    )
                                )
                            }

                        }

                        l.sortBy {
                            it.Time
                        }
                        l.sortByDescending {
                            it.Point.toLong()
                        }


                        val adapter = AdaperRank(requireContext(), R.layout.adapter_rank, l)
                        binding.ListScience.adapter = adapter
                    }
                }}
        ,0
        )

    }

    private fun OnSelectGeneral() {
        binding.ListScience.isVisible = false
        binding.ListGeneral.isVisible = true
        binding.ListMath.isVisible = false
        binding.TypeRank.setImageResource(R.drawable.general_rank)
        binding.TextRank.setText(R.string.general_knowledge)
        val l = ArrayList<Rank>()
        Handler().postDelayed(
            {
                val database = FirebaseDatabase.getInstance().getReference("General Knowledge")
                database.get().addOnSuccessListener {
                    if (it.exists()) {
                        for (ds in it.children) {
                            val ID = ds.child("ID").getValue().toString()
                            val Name = ds.child("Name").getValue().toString()
                            val Path = ds.child("Path").getValue().toString()
                            val Point = ds.child("Point").getValue().toString()
                            val Time = ds.child("Time").getValue().toString().toDouble() / 1000

                            if (Time != 0.0 && Point.toString().toLong() != 0L) {
                                l.add(
                                    Rank(
                                        ID,
                                        Name,
                                        Path,
                                        Point.toLong(),
                                        Time
                                    )
                                )
                            }

                        }

                        l.sortBy {
                            it.Time
                        }

                        l.sortByDescending {
                            it.Point.toLong()
                        }


                        val adapter = AdaperRank(requireContext(), R.layout.adapter_rank, l)
                        binding.ListGeneral.adapter = adapter
                    }


                }}
        ,0
        )

    }
}