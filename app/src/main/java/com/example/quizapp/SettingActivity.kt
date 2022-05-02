package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.quizapp.databinding.ActivitySettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class SettingActivity : AppCompatActivity() {



    //
    private  var Language :String=""
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().getReference("Users")

    //binding
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)


       binding.English
           .setOnClickListener {
            Language = binding.English.text.toString()
        }


        binding.Khmer.setOnClickListener {
            Language = binding.Khmer.text.toString()
        }






        binding.BtnClose.setOnClickListener {
            finish()
        }


       binding.BtnSetting.setOnClickListener {
          SetSetting()
        }

        SetCheckLanguage()


    }

    override fun onResume() {
        super.onResume()
        SetCheckLanguage()
    }

    private fun SetCheckLanguage(){
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                Language = it.child("Language").value.toString()
                if (Language.equals("English")){
                    binding.Language.check(R.id.English)
                }else{
                    binding.Language.check(R.id.Khmer)
                }
            }
        }
    }

    private fun SetSetting(){
        val map = HashMap<String,String>()
        map.put("Language",Language)
        database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>)
        SetCheckLanguage()
        getLang()
        startActivity(Intent(this,StartAppActivity::class.java))
    }

    // Set khmer or english
    private fun setLangToView(lang:String){
        val r = resources
        val dm = r.displayMetrics
        var config = r.configuration
        config.locale = Locale(lang.toLowerCase())

        r.updateConfiguration(config,dm)

        binding.BtnSetting.setText(R.string.setting)
        binding.TextHeader.setText(R.string.setting)
        binding.English.setText(R.string.english)
        binding.Khmer.setText(R.string.khmer)

    }

    private fun getLang(){
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val lang = it.child("Language").value.toString()
                setLangToView(lang[0].toString()+lang[1].toString())
            }else{

                setLangToView("en")
            }
        }
    }
}