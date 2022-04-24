package com.example.quizapp

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.extensions.hideKeyboard
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.*

class ChangePasswordActivity : AppCompatActivity() {
    //Button
    private lateinit var BtnClose:Button
    private lateinit var BtnSetNewPassword:Button

    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var user:FirebaseUser

    //Edit Text
    private lateinit var TextOldPassword:EditText
    private lateinit var TextNewPassword:EditText
    private lateinit var TextNewConfirmPassword:EditText

    //View
    private lateinit var Form:View
    private lateinit var ShowProgress:View
    private lateinit var dialog:Dialog
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)


        //Button
        BtnClose = findViewById(R.id.BtnClose)
        BtnClose.setOnClickListener {
            finish()
        }

        BtnSetNewPassword = findViewById(R.id.BtnSetNewPassword)
        BtnSetNewPassword.setOnClickListener {
            CheckInput()
        }

        //View
        Form = findViewById(R.id.Form)
        ShowProgress = findViewById(R.id.SHOW_PROGRESS)
        view = findViewById(R.id.Layout_ChangePassword)
        view.setOnClickListener {
            hideKeyboard(view)
        }

        auth = FirebaseAuth.getInstance()

        //Edit text
        TextOldPassword = findViewById(R.id.TextOldPassword)
        TextNewPassword = findViewById(R.id.TextNewPassword)
        TextNewConfirmPassword = findViewById(R.id.TextNewConfirmPassword)

        dialog = Dialog(this)

    }

    private fun CheckInput() {
        if (TextOldPassword.text.isEmpty() || TextNewPassword.text.isEmpty()||TextNewConfirmPassword.text.isEmpty()){
            Toast(this).ShowMessage("Please Enter all the filed",this, R.drawable.close_red)
        }else if (!TextNewConfirmPassword.text.toString().equals(TextNewPassword.text.toString())){
            TextNewConfirmPassword.setError("Confirm Password not match New Password")
        }else {
            ShowDialog()
        }
    }

    //Ask user first
    private fun ShowDialog(){
        dialog.setContentView(R.layout.dialog_ask)

        val ip = WindowManager.LayoutParams()
        ip.copyFrom(dialog.window!!.attributes)
        ip.width = WindowManager.LayoutParams.MATCH_PARENT
        ip.height = WindowManager.LayoutParams.WRAP_CONTENT
        ip.gravity = Gravity.CENTER


        dialog.window!!.attributes=ip

        val TextHeader = dialog.findViewById<TextView>(R.id.TextHeader)
        val Icon =dialog.findViewById<ImageView>(R.id.IconDialog)
        val BtnNo = dialog.findViewById<Button>(R.id.BtnNo)
        val BtnYes = dialog.findViewById<Button>(R.id.BtnYes)

        TextHeader.setText("Do you want to change your password?")
        Icon.setImageResource(R.drawable.warning)

        BtnNo.setOnClickListener {
            dialog.dismiss()
        }

        BtnYes.setOnClickListener {
           ChangePassword()
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun ChangePassword(){
        Form.isVisible = false
        ShowProgress.isVisible = true
        user = auth.currentUser!!
        var credential: AuthCredential =
            EmailAuthProvider.getCredential(
                auth.currentUser!!.email
                    .toString(),TextOldPassword.text.toString())

        user.reauthenticate(credential).addOnCompleteListener {
            if (it.isSuccessful){
                Form.isVisible = true
                ShowProgress.isVisible = false
                user.updatePassword(TextNewConfirmPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast(this).ShowMessage("Change Password success",this, R.drawable.tick)

                        Form.isVisible = true
                        ShowProgress.isVisible = false
                    }else{
                        Toast(this).ShowMessage("Error :${it.exception}",this, R.drawable.close_red)
                        Form.isVisible = true
                        ShowProgress.isVisible = false
                    }
                }
            }else{
                Form.isVisible = true
                ShowProgress.isVisible = false
                Toast(this).ShowMessage("Error :${it.exception}",this, R.drawable.close_red)
            }

        }
    }


}