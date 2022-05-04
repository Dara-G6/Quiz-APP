package com.example.quizapp

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quizapp.databinding.ActivityBestScoreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.rpc.context.AttributeContext
import kotlin.math.absoluteValue

class BestScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBestScoreBinding

    private lateinit var  res  :Resources
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBestScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnClose.setOnClickListener {
            finish()
        }

        res = this.resources

        setScoreMath()
        setScoreScience()
        setScoreGeneral()
    }

    override fun onResume() {
        super.onResume()
        setScoreMath()
        setScoreScience()
        setScoreGeneral()
    }
    //set score math
    private fun setScoreMath(){
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance().getReference("Math")
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                    val point = it.child("Point").value.toString()
                    val time  = it.child("Time").value.toString().toDouble()/1000
                if (point!="0" && time!=0.0){
                    binding.TextScoreMath.setText("${res.getText(R.string.score)}  : $point")
                    binding.TextTimeMath.setText("${res.getText(R.string.time)}  : $time ${getString(R.string.sec)}")
                }else{
                    binding.TextScoreMath.visibility = View.GONE
                    binding.TextTimeMath.visibility = View.GONE
                    binding.TextNoRecordeMath.visibility = View.VISIBLE
                }
            }else{
                binding.TextScoreMath.visibility = View.GONE
                binding.TextTimeMath.visibility = View.GONE
                binding.TextNoRecordeMath.visibility = View.VISIBLE
            }
        }
    }


    //set score science
    private fun setScoreScience(){
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance().getReference("Science")
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val point = it.child("Point").value.toString()
                val time  = it.child("Time").value.toString().toDouble()/1000
                if (point!="0" && time!=0.0){
                    binding.TextScoreScience.setText("${res.getText(R.string.score)}  : $point")
                    binding.TextTimeScience.setText("${res.getText(R.string.time)}  : $time ${getString(R.string.sec)}")
                }else{
                    binding.TextScoreScience.visibility = View.GONE
                    binding.TextTimeScience.visibility = View.GONE
                    binding.TextNoRecordeScience.visibility = View.VISIBLE
                }
            }else{
                binding.TextScoreScience.visibility = View.GONE
                binding.TextTimeScience.visibility = View.GONE
                binding.TextNoRecordeScience.visibility = View.VISIBLE
            }
        }
    }

    //set score general
    private fun setScoreGeneral(){
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance().getReference("General Knowledge")
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val point = it.child("Point").value.toString()
                val time  = it.child("Time").value.toString().toDouble()/1000
                if (point!="0" && time!=0.0){
                    binding.TextScoreGeneral.setText("${res.getText(R.string.score)}  : $point")
                    binding.TextTimeGeneral.setText("${res.getText(R.string.time)}  : $time ${getString(R.string.sec)}")
                }else{
                    binding.TextScoreGeneral.visibility = View.GONE
                    binding.TextTimeGeneral.visibility = View.GONE
                    binding.TextNoRecordeGeneral.visibility = View.VISIBLE
                }
            }else{
                binding.TextScoreGeneral.visibility = View.GONE
                binding.TextTimeGeneral.visibility = View.GONE
                binding.TextNoRecordeGeneral.visibility = View.VISIBLE
            }
        }
    }
}