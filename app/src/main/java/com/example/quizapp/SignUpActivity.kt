package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.quizapp.databinding.ActivitySignUpBinding
import com.example.quizapp.extensions.hideKeyboard
import com.example.quizapp.toast.showMessage
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {





    //binding
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



       binding.TextAllready.setOnClickListener {
            startActivity(Intent(this,SignINActivity::class.java))
        }




        //sing up
        binding.BtnSingUp.setOnClickListener {
              checkInput()
                hideKeyboard(binding.root)
        }


        binding.root.setOnClickListener {
            hideKeyboard(binding.root)
        }


        auth = FirebaseAuth.getInstance()



    }




    private fun checkInput(){
        if (binding.TextConfirmPassword.text.toString().isEmpty()
            ||binding.TextEmail.text.toString().isEmpty()
            ||binding.TextPassword.text.toString().isEmpty()
        ){
            Toast.makeText(this,"Enter all the information",Toast.LENGTH_LONG).show()
        }

      else  if (!binding.TextConfirmPassword.text.toString().trim().equals(binding.TextPassword.text.toString())){
          binding.TextConfirmPassword.setError("Password not match")
        }
      else {
           createUser()
        }
    }


    private fun createUser(){
         val email = binding.TextEmail.text.toString().trim()
         val password = binding.TextConfirmPassword.text.toString().trim()
         binding.Form.isVisible =false
         binding.SHOWPROGRESS.isVisible = true
               auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                   if (it.isSuccessful){
                      binding.Form.isVisible =true
                       binding.SHOWPROGRESS.isVisible = false
                       Toast(this).showMessage("Create account success",this, R.drawable.tick)
                       var i : Intent = Intent(this,SetProfileActivity::class.java)
                       i.putExtra("Email",email)
                       startActivity(i)
                   }else{
                       binding.Form.isVisible =true
                       binding.SHOWPROGRESS.isVisible = false
                       Toast(this).showMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                   }
               }

    }


}