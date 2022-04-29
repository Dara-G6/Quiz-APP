package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.databinding.ActivitySignInBinding
import com.example.quizapp.extensions.hideKeyboard
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class SignINActivity : AppCompatActivity() {


    private lateinit  var auth:FirebaseAuth
    private lateinit var database:DatabaseReference

    //binding
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")


        //Button login
        binding.BtnLogin.setOnClickListener {
            hideKeyboard(binding.root)
            Login()
        }

        // Text view

       binding.TextRegister.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }


        binding.root.setOnClickListener {
            hideKeyboard(binding.root)
        }
    }


    override fun onStart() {
        super.onStart()
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()) {
                val l = it.child("Login").getValue().toString()
                if (l == "Yes"){
                    startActivity(Intent(this,HomePageActivity::class.java))
                }
            }
        }
        getLang()
    }

    //Login user
    private fun Login(){
        if (!binding.TextEmail.text.toString().contains("@gmail.com")){
            binding.TextEmail.setError("Enter real email")
        }
        if (binding.TextEmail.text.toString().isEmpty() || binding.TextPassword.text.toString().isEmpty()){
            binding.TextEmail.setError("Please Enter email")
            binding.TextPassword.setError("Please Enter Password")
        }
        else{
           binding.Form.isVisible = false
            binding.SHOWPROGRESS.isVisible = true
            auth.signInWithEmailAndPassword(binding.TextEmail.text.toString()
                ,binding.TextPassword.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    binding.Form.isVisible = true
                    binding.SHOWPROGRESS.isVisible  = false
                    database.child(auth.uid.toString()).child("Login").setValue("Yes")
                    startActivity(Intent(this,HomePageActivity::class.java))
                    Toast(this).ShowMessage("Login Success",this,R.drawable.tick)
                }else {
                    binding.Form.isVisible = true
                    binding.SHOWPROGRESS.isVisible = false
                    Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                }
            }
        }
    }


    // Set khmer or english
    private fun setLangToView(lang:String){
        if (lang[0].toLowerCase()=='k')
        binding.TextRegister.setText("ក")
    }

    private fun getLang(){
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val lang = it.child("Language").value.toString()
                setLangToView(lang)
            }else{
                Log.d("HI :","")
                setLangToView("en")
            }
        }
    }



}