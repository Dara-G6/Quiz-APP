package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {

    //button
    private lateinit var BtnClose:Button
    private lateinit var BtnSetSetting:Button

    //Radio group
    private lateinit var GetLanguage:RadioGroup
    private lateinit var BtnEnglish:RadioButton
    private lateinit var BtnKhmer:RadioButton

    //
    private  var Language :String=""
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().getReference("Users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //Radio button
        GetLanguage = findViewById(R.id.Language)

        BtnEnglish = findViewById(R.id.English)
        BtnEnglish.setOnClickListener {
            Language = BtnEnglish.text.toString()
        }

        BtnKhmer = findViewById(R.id.Khmer)
        BtnKhmer.setOnClickListener {
            Language = BtnKhmer.text.toString()
        }





        //Button
        BtnClose = findViewById(R.id.BtnClose)
        BtnClose.setOnClickListener {
            finish()
        }

        BtnSetSetting = findViewById(R.id.BtnSetting)
        BtnSetSetting.setOnClickListener {
          SetSetting()
        }

        SetCheckLanguage()


    }

    private fun SetCheckLanguage(){
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                Language = it.child("Language").getValue().toString()
                if (Language.equals("English")){
                    GetLanguage.check(R.id.English)
                }else{
                    GetLanguage.check(R.id.Khmer)
                }
            }
        }
    }

    private fun SetSetting(){
        val map = HashMap<String,String>()
        map.put("Language",Language)
        database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>)
        SetCheckLanguage()
    }
}