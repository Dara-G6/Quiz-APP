package com.example.quizapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.quizapp.R
import com.example.quizapp.adapter.AdaperRank
import com.example.quizapp.databinding.FragmentRankBinding
import com.example.quizapp.dataclass.Rank
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList


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

        context?.let { MobileAds.initialize(it) }
        getLang()
        return binding.root
    }

//    override fun onResume() {
//        super.onResume()
//        binding.menu.selectedItemId=R.id.TabMath
//        OnSelectMath()
//    }
    private fun OnSelectMath() {

        val request = AdRequest.Builder().build()
        binding.AdView.loadAd(request)
        binding.ListScience.isVisible = false
        binding.ListGeneral.isVisible = false
        binding.ListMath.isVisible = true
        binding.TypeRank.setImageResource(R.drawable.math_rank)
        binding.TextRank.setText(R.string.math)
        val l = ArrayList<Rank>()
                val database = FirebaseDatabase.getInstance().getReference("Math")
                database.orderByChild("Point").get().addOnSuccessListener {
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



                        val adapter =AdaperRank(requireContext(),R.layout.adapter_rank, l)
                        binding.ListMath.adapter = adapter
                    }
                }

        //getLang()


    }

    private fun OnSelectScience() {
        val request = AdRequest.Builder().build()
        binding.AdView.loadAd(request)
        binding.ListScience.isVisible = true
        binding.ListGeneral.isVisible = false
        binding.ListMath.isVisible = false
        binding.TypeRank.setImageResource(R.drawable.chemist_rank)
        binding.TextRank.setText(R.string.science)
        val l = ArrayList<Rank>()

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


                        val adapter =AdaperRank(requireContext(),R.layout.adapter_rank, l)
                        binding.ListScience.adapter = adapter
                    }
                }
        //getLang()

    }

    private fun OnSelectGeneral() {

        val request = AdRequest.Builder().build()
        binding.AdView.loadAd(request)

        binding.ListScience.isVisible = false
        binding.ListGeneral.isVisible = true
        binding.ListMath.isVisible = false
        binding.TypeRank.setImageResource(R.drawable.general_rank)
        binding.TextRank.setText(R.string.general_knowledge)
        val l = ArrayList<Rank>()

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



                        val adapter =AdaperRank(requireContext(),R.layout.adapter_rank, l)
                        binding.ListGeneral.adapter = adapter
                    }


                }
        //getLang()
    }

    // Set khmer or english
    private fun setLangToView(lang:String){
        val r = resources
        val dm = r.displayMetrics
        val config = r.configuration
        config.locale = Locale(lang.toLowerCase())
        r.updateConfiguration(config,dm)
        //binding.TextRank.text = getText(R.string.math)

    }

    private fun getLang(){
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val lang = it.child("Language").value.toString()
                if (lang=="Khmer"){
                    setLangToView("kh")
                }else{
                    setLangToView("en")
                }
            }else{
                setLangToView("en")
            }
        }
    }
}