package com.example.quizapp

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.databinding.ActivityEditProfileBinding
import com.example.quizapp.extensions.hideKeyboard
import com.example.quizapp.toast.showMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.File
import java.nio.file.FileSystems

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
            showDialog()
            hideKeyboard(binding.root)
        }


       binding.BtnClose.setOnClickListener {
            finish()
        }





       binding.ProfileImage.setOnClickListener {
            chooseImage()
        }



        dialog = Dialog(this)

        setProfile()
    }




    private fun checkInput(){
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
                noPhoto()
            }
            else -> {
                binding.Form.isVisible = false
                binding.SHOWPROGRESS.isVisible=true
                withPhoto()
            }
        }
    }


    //Set profile
    private fun setProfile(){
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
    private fun noPhoto(){
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
                        updatePointMath(name,path)
                        updatePointScience(name,path)
                        updatePointGeneral(name,path)
                        binding.Form.isVisible = true
                        binding.SHOWPROGRESS.isVisible =false
                        Toast(this).showMessage("${getString(R.string.edit_profile_succeeded)}",this, R.drawable.tick)

                    }else{
                        Toast(this).showMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                        binding.Form.isVisible = true
                        binding.SHOWPROGRESS.isVisible =false
                    }
                }
            }
        }
    }
    //Update Photo
    private fun withPhoto(){
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
                            updatePointMath(name,path)
                            updatePointScience(name,path)
                            updatePointGeneral(name,path)
                            Toast(this).showMessage("Setup new profile success",this, R.drawable.tick)
                        }else{
                            Toast.makeText(this,"Error : ${it.exception}",Toast.LENGTH_LONG).show()
                            binding.Form.isVisible = true
                            binding.SHOWPROGRESS.isVisible =false
                        }
                    }
                }
            }else{
                Toast(this).showMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                    binding.Form.isVisible = true
                    binding.SHOWPROGRESS.isVisible =false
            }
        }
    }

    //Update Username and picture in rank math
    private fun updatePointMath(Name:String, Path:String){
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
    private fun updatePointScience(Name:String, Path:String){
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
    private fun updatePointGeneral(Name:String, Path:String){
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


    //Select photo from gallery
    private fun chooseImage(){
        var i : Intent = Intent(Intent.ACTION_PICK);
        i.type = "image/*"
        startActivityForResult(i,PICK_IMAGE)
    }



    //Ask user first
    private fun showDialog(){
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
            checkInput()
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
                val t = data.type.toString()
                Log.d("Type ",Path.toString())
                binding.ProfileImage.setImageURI(Path)
            }
        }
    }
}