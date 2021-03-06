package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class StartAppActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)




        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        startApp()



    }

    override fun onResume() {
        super.onResume()
        startApp()
    }


    //Start App
    private fun startApp(){
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val l = it.child("Login").value.toString()
                if (l =="Yes"){
                    startActivity(Intent(this,HomePageActivity::class.java))
                }else{
                    startActivity(Intent(this,SignINActivity::class.java))
                }
            }else{
                startActivity(Intent(this,SignINActivity::class.java))
            }
        }
        getLang()
    }

    // Set khmer or english
    private fun setLangToView(lang:String){
        val r = resources
        val dm = r.displayMetrics
        val config = r.configuration
        config.locale = Locale(lang.toLowerCase())
        r.updateConfiguration(config,dm)

        val textStart = findViewById<TextView>(R.id.TextStart)
        textStart.text = getString(R.string.for_everyone)

    }

    private fun getLang(){
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