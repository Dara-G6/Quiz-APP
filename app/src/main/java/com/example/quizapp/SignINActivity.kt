package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.extensions.hideKeyboard
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignINActivity : AppCompatActivity() {
    private lateinit var TextRegister :TextView


    private lateinit var BtnLogin :Button
    private lateinit var TextEmail : EditText
    private lateinit var TextPassword:EditText

    private lateinit  var auth:FirebaseAuth
    private lateinit var database:DatabaseReference

    private lateinit var Form:View
    private lateinit var ShowProgress:View
    private lateinit var view:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        view = findViewById(R.id.Layout_SignIN)
        // Edit Text
        TextEmail = findViewById(R.id.TextEmail)
        TextPassword = findViewById(R.id.TextPassword)

        //Button
        BtnLogin = findViewById(R.id.BtnLogin)
        BtnLogin.setOnClickListener {
            hideKeyboard(view)
            Login()
        }

        // Text view
        TextRegister = findViewById(R.id.TextRegister)
        TextRegister.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        Form = findViewById(R.id.Form)
        ShowProgress = findViewById(R.id.SHOW_PROGRESS)
        view.setOnClickListener {
            hideKeyboard(view)
        }
    }


    override fun onStart() {
        super.onStart()
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()) {
                var l = it.child("Login").getValue().toString()
                if (l.equals("Yes")){
                    startActivity(Intent(this,HomePageActivity::class.java))
                }
            }
        }
    }

    //Login user
    private fun Login(){
        if (!TextEmail.text.toString().contains("@gmail.com")){
            TextEmail.setError("Enter real email")
        }
        if (TextEmail.text.toString().isEmpty() || TextPassword.text.toString().isEmpty()){
            TextEmail.setError("Please Enter email")
            TextPassword.setError("Please Enter Password")
        }
        else{
            Form.isVisible = false
            ShowProgress.isVisible = true
            auth.signInWithEmailAndPassword(TextEmail.text.toString(),TextPassword.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    Form.isVisible = true
                    ShowProgress.isVisible = false
                    database.child(auth.uid.toString()).child("Login").setValue("Yes")
                    startActivity(Intent(this,HomePageActivity::class.java))
                    Toast(this).ShowMessage("Login Success",this,R.drawable.tick)
                }else {
                    Form.isVisible = true
                    ShowProgress.isVisible = false
                    Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                }
            }
        }
    }



}