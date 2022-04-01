package com.example.quizapp

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class EditProfile : AppCompatActivity() {

    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage:StorageReference

    //View
    private lateinit var Form:View
    private lateinit var ShowProgress:View

    //Button
    private lateinit var BtnSet:Button
    private lateinit var BtnClose:Button

    //Image view
    private lateinit var ProfileImage:ImageView

    //Edit Text
    private lateinit var TextUserName:EditText


    private var Path:Uri?=null
    private val PICK_IMAGE=1


    //Dialog
    private lateinit var dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        //Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        storage =FirebaseStorage.getInstance().getReference("Profile")

        //Button
        BtnSet = findViewById(R.id.BtnSet)
        BtnSet.setOnClickListener {
            ShowDialog()
        }

        BtnClose = findViewById(R.id.BtnClose)
        BtnClose.setOnClickListener {
            finish()
        }

        //View
        Form = findViewById(R.id.Form)
        ShowProgress = findViewById(R.id.SHOW_PROGRESS)

        //Image view
        ProfileImage = findViewById(R.id.ProfileImage)
        ProfileImage.setOnClickListener {
            ChooseImage()
        }

        //Edit Text
        TextUserName = findViewById(R.id.TextUserName)

        dialog = Dialog(this)

        SetProfile()
    }




    private fun CheckInput(){
        if (TextUserName.text.isEmpty()){
            TextUserName.setError("Please enter name")
        }else if (TextUserName.text.length<4){
            TextUserName.setError("Please enter full name at least have 4 alphabets")
        }else if (Path==null){
            Form.isVisible = false
            ShowProgress.isVisible =true
            NoPhoto()
        }else{
            Form.isVisible = false
            ShowProgress.isVisible =true
            WithPhoto()
        }
    }


    //Set profile
    private fun SetProfile(){
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val name = it.child("Name").value.toString()
                val path = it.child("Profile").value.toString()
                TextUserName.setText(name)
                Picasso.get().load(path).into(ProfileImage)
            }
        }
    }

    //No update photo
    private fun NoPhoto(){
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                Form.isVisible = false
                ShowProgress.isVisible =true
                val name = TextUserName.text.toString()
                val path = it.child("Profile").value.toString()
                var map:HashMap<String,String> = HashMap<String,String>()
                map.put("Name",name)
                map.put("Profile",path.toString())
                database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>).addOnCompleteListener {
                    if (it.isSuccessful){
                        Form.isVisible = true
                        ShowProgress.isVisible =false
                        Toast(this).ShowMessage("Setup new profile success",this, R.drawable.tick)

                    }else{
                        Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                        Form.isVisible = true
                        ShowProgress.isVisible =false
                    }
                }
            }
        }
    }
    //Update Photo
    private fun WithPhoto(){
        storage.child(auth.uid.toString()).putFile(Path!!).addOnCompleteListener {
            Form.isVisible = false
            ShowProgress.isVisible =true
            if (it.isSuccessful){
                Form.isVisible = true
                ShowProgress.isVisible =false
                storage.child(auth.uid.toString()).downloadUrl.addOnSuccessListener {
                    val name = TextUserName.text.toString()
                    val path = it.toString()
                    var map:HashMap<String,String> = HashMap<String,String>()
                    map.put("Name",name)
                    map.put("Profile",path.toString())
                    database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>).addOnCompleteListener {
                        if (it.isSuccessful){
                            Form.isVisible = true
                            ShowProgress.isVisible =false
                            Toast(this).ShowMessage("Setup new profile success",this, R.drawable.tick)
                        }else{
                            Toast.makeText(this,"Error : ${it.exception}",Toast.LENGTH_LONG).show()
                            Form.isVisible = true
                            ShowProgress.isVisible =false
                        }
                    }
                }
            }else{
                Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                Form.isVisible = true
                ShowProgress.isVisible =false
            }
        }
    }
    //Select photo from gellery
    private fun ChooseImage(){
        var i : Intent = Intent(Intent.ACTION_PICK);
        i.setType("image/*")
        startActivityForResult(i,PICK_IMAGE)
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

        BtnNo.setOnClickListener {
            dialog.dismiss()
        }

        BtnYes.setOnClickListener {
            CheckInput()
            dialog.dismiss()
        }

        dialog.show()

    }

    //Load Image to Profile view
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK){
                Path = data.data
                ProfileImage.setImageURI(Path)
            }
        }
    }
}