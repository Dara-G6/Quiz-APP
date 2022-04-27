package com.example.quizapp

import android.app.Activity
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
import com.example.quizapp.databinding.ActivityEditProfileBinding
import com.example.quizapp.extensions.hideKeyboard
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity() {

    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage:StorageReference




    private var Path:Uri?=null
    private val PICK_IMAGE=1


    //Dialog
    private lateinit var dialog:Dialog

    private lateinit var binding:ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        storage =FirebaseStorage.getInstance().getReference("Profile")



       binding.BtnSet.setOnClickListener {
            ShowDialog()
            hideKeyboard(binding.root)
        }


       binding.BtnClose.setOnClickListener {
            finish()
        }





       binding.ProfileImage.setOnClickListener {
            ChooseImage()
        }



        dialog = Dialog(this)

        SetProfile()
    }




    private fun CheckInput(){
        when {
            binding.TextUserName.text.toString().isEmpty() -> {
                binding.TextUserName.setError("Please enter name")
            }
            binding.TextUserName.text.toString().length<4 -> {
                binding.TextUserName.setError("Please enter full name at least have 4 alphabets")
            }
            Path==null -> {
                binding.Form.isVisible = false
                binding.SHOWPROGRESS.isVisible =true
                NoPhoto()
            }
            else -> {
                binding.Form.isVisible = false
                binding.SHOWPROGRESS.isVisible=true
                WithPhoto()
            }
        }
    }


    //Set profile
    private fun SetProfile(){
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val name = it.child("Name").value.toString()
                val path = it.child("Profile").value.toString()
                binding.TextUserName.setText(name)
                Picasso.get().load(path).into(binding.ProfileImage)
            }
        }
    }

    //No update photo
    private fun NoPhoto(){
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                binding.Form.isVisible = false
                binding.SHOWPROGRESS.isVisible =true
                val name = binding.TextUserName.text.toString()
                val path = it.child("Profile").value.toString()
                var map:HashMap<String,String> = HashMap<String,String>()
                map.put("Name",name)
                map.put("Profile",path.toString())
                database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>).addOnCompleteListener {
                    if (it.isSuccessful){
                        UpdatePointMath(name,path)
                        UpdatePointScience(name,path)
                        UpdatePointGeneral(name,path)
                        binding.Form.isVisible = true
                        binding.SHOWPROGRESS.isVisible =false
                        Toast(this).ShowMessage("Setup new profile success",this, R.drawable.tick)

                    }else{
                        Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                        binding.Form.isVisible = true
                        binding.SHOWPROGRESS.isVisible =false
                    }
                }
            }
        }
    }
    //Update Photo
    private fun WithPhoto(){
        storage.child(auth.uid.toString()).putFile(Path!!).addOnCompleteListener {
            binding.Form.isVisible = false
            binding.SHOWPROGRESS.isVisible =true
            if (it.isSuccessful){
                binding.Form.isVisible = true
                binding.SHOWPROGRESS.isVisible =false
                storage.child(auth.uid.toString()).downloadUrl.addOnSuccessListener {
                    val name =binding.TextUserName.text.toString()
                    val path = it.toString()
                    var map:HashMap<String,String> = HashMap<String,String>()
                    map.put("Name",name)
                    map.put("Profile",path.toString())
                    database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>).addOnCompleteListener {
                        if (it.isSuccessful){
                            binding.Form.isVisible = true
                            binding.SHOWPROGRESS.isVisible =false
                            UpdatePointMath(name,path)
                            UpdatePointScience(name,path)
                            UpdatePointGeneral(name,path)
                            Toast(this).ShowMessage("Setup new profile success",this, R.drawable.tick)
                        }else{
                            Toast.makeText(this,"Error : ${it.exception}",Toast.LENGTH_LONG).show()
                            binding.Form.isVisible = true
                            binding.SHOWPROGRESS.isVisible =false
                        }
                    }
                }
            }else{
                Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                    binding.Form.isVisible = true
                    binding.SHOWPROGRESS.isVisible =false
            }
        }
    }

    //Update Username and picture in rank math
    private fun UpdatePointMath(Name:String, Path:String){
        val map = HashMap<String,String>()
        map.put("Name",Name)
        map.put("Path",Path)
        map.put("ID",auth.uid.toString())

        var Point =0
        var Time = 0

        val database = FirebaseDatabase.getInstance().getReference("Math")

        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                Point = it.child("Point").getValue().toString().toInt()
                Time = it.child("Time").getValue().toString().toInt()
                map.put("Point",Point.toString())
                map.put("Time",Time.toString())
                database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>)
            }else{
                Point = 0
                Time = 0
                map.put("Point",Point.toString())
                map.put("Time",Time.toString())
                database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>)
            }

        }




    }

    //Update Username and picture in rank science
    private fun UpdatePointScience(Name:String, Path:String){
        val map = HashMap<String,String>()
        map.put("Name",Name)
        map.put("Path",Path)
        map.put("ID",auth.uid.toString())

        var Point =0
        var Time = 0

        val database = FirebaseDatabase.getInstance().getReference("Science")

        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                Point = it.child("Point").getValue().toString().toInt()
                Time = it.child("Time").getValue().toString().toInt()
                map.put("Point",Point.toString())
                map.put("Time",Time.toString())
                database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>)
            }else{
                Point = 0
                Time = 0
                map.put("Point",Point.toString())
                map.put("Time",Time.toString())
                database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>)
            }

        }


    }

    //Update Username and picture in rank general
    private fun UpdatePointGeneral(Name:String, Path:String){
        val map = HashMap<String,String>()
        map.put("Name",Name)
        map.put("Path",Path)
        map.put("ID",auth.uid.toString())

        var Point =0
        var Time = 0

        val database = FirebaseDatabase.getInstance().getReference("General Knowledge")

        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                Point = it.child("Point").getValue().toString().toInt()
                Time = it.child("Time").getValue().toString().toInt()
                map.put("Point",Point.toString())
                map.put("Time",Time.toString())
                database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>)
            }else{
                Point = 0
                Time = 0
                map.put("Point",Point.toString())
                map.put("Time",Time.toString())
                database.child(auth.uid.toString()).updateChildren(map as Map<String, Any>)
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
                binding.ProfileImage.setImageURI(Path)
            }
        }
    }
}