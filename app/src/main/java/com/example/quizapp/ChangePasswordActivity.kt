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
import com.example.quizapp.databinding.ActivityChangePasswordBinding
import com.example.quizapp.extensions.hideKeyboard
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.*

class ChangePasswordActivity : AppCompatActivity() {


    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var user:FirebaseUser



    //binding
    private lateinit var binding:ActivityChangePasswordBinding
    private lateinit var dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            hideKeyboard(binding.root)
        }

        //Button
       binding.BtnClose.setOnClickListener {
            Log.d("Hi","")
            finish()
        }

       binding.BtnSetNewPassword.setOnClickListener {
            hideKeyboard(binding.root)
            CheckInput()
        }





        auth = FirebaseAuth.getInstance()
        dialog = Dialog(this)

    }

    private fun CheckInput() {
        if (binding.TextOldPassword.text.toString().isEmpty()
            || binding.TextNewPassword.text.toString().isEmpty()
            ||binding.TextNewConfirmPassword.text.toString().isEmpty()){
            Toast(this).ShowMessage("Please Enter all the filed",this, R.drawable.close_red)
        }
        else if (!binding.TextNewConfirmPassword.text.toString().equals(binding.TextNewPassword.text.toString())){
           binding.TextNewConfirmPassword.setError("Confirm Password not match New Password")
        }
        else
        {
            ShowDialog()
        }
    }

    //Ask user first
    private fun ShowDialog(){
        dialog.setContentView(R.layout.dialog_changepassword)

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
      binding.Form.isVisible = false
      binding.SHOWPROGRESS.isVisible = true
        user = auth.currentUser!!
        var credential: AuthCredential =
            EmailAuthProvider.getCredential(
                auth.currentUser!!.email
                    .toString(),binding.TextOldPassword.text.toString())

        user.reauthenticate(credential).addOnCompleteListener {
            if (it.isSuccessful){
                binding.Form.isVisible = true
                binding.SHOWPROGRESS.isVisible = false
                user.updatePassword(binding.TextNewConfirmPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast(this).ShowMessage("Change Password success",this, R.drawable.tick)

                       binding.Form.isVisible = true
                       binding.SHOWPROGRESS.isVisible = false
                    }else{
                        Toast(this).ShowMessage("Error :${it.exception}",this, R.drawable.close_red)
                        binding.Form.isVisible = true
                        binding.SHOWPROGRESS.isVisible = false
                    }
                }
            }else{
                binding.Form.isVisible = true
                binding.SHOWPROGRESS.isVisible = false
                Toast(this).ShowMessage("Error :${it.exception}",this, R.drawable.close_red)
            }

        }
    }


}