package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.quizapp.extensions.hideKeyboard
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var TextEmail:EditText
    private lateinit var TextPassword:EditText
    private lateinit var TextConfirmPassword:EditText
    private lateinit var Form:View
    private lateinit var ShowProgress:View
    private lateinit var view:View
    private lateinit var BtnSignUp:Button
    private lateinit var TextAlready:TextView



    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        view = findViewById(R.id.Layput_SignUP)

        //Text view
        TextAlready = findViewById(R.id.TextAllready)
        TextAlready.setOnClickListener {
            startActivity(Intent(this,SignINActivity::class.java))
        }

        TextEmail = findViewById(R.id.TextEmail)
        TextPassword=findViewById(R.id.TextPassword)
        TextConfirmPassword = findViewById(R.id.TextConfirmPassword)

        BtnSignUp = findViewById(R.id.BtnSingUp)
        BtnSignUp.setOnClickListener {
              CheckInput()
                hideKeyboard(view)
        }


        Form = findViewById(R.id.Form)
        ShowProgress = findViewById(R.id.SHOW_PROGRESS)

        view.setOnClickListener {
            hideKeyboard(view)
        }


        auth = FirebaseAuth.getInstance()



    }




    private fun CheckInput(){
        if (TextConfirmPassword.text.isEmpty()
            ||TextEmail.text.isEmpty()
            ||TextPassword.text.isEmpty()
        ){
            Toast.makeText(this,"Enter all the information",Toast.LENGTH_LONG).show()
        }

      else  if (!TextConfirmPassword.text.toString().trim().equals(TextPassword.text.toString())){
          TextConfirmPassword.setError("Password not match")
        }
      else {
           CreateUser()
        }
    }


    private fun CreateUser(){
         val email = TextEmail.text.toString().trim()
         val password = TextConfirmPassword.text.toString().trim()
         Form.isVisible =false
         ShowProgress.isVisible = true
               auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                   if (it.isSuccessful){
                       Form.isVisible =true
                       ShowProgress.isVisible = false
                       Toast(this).ShowMessage("Create account success",this, R.drawable.tick)
                       var i : Intent = Intent(this,SetProfileActivity::class.java)
                       i.putExtra("Email",email)
                       startActivity(i)
                   }else{
                       Form.isVisible =true
                       ShowProgress.isVisible = false
                       Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)

                   }
               }

    }


}