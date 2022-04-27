package com.example.quizapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.databinding.ActivitySetProfileBinding
import com.example.quizapp.extensions.hideKeyboard
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class SetProfileActivity : AppCompatActivity() {


    private var Path:Uri?=null
    private val PICK_IMAGE =1


    private lateinit var auth:FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference
    private lateinit var user: FirebaseUser

    //binding
    private lateinit var binding: ActivitySetProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.root.setOnClickListener {
            hideKeyboard(binding.root)
        }

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        database = FirebaseDatabase.getInstance().getReference("Users")
        storage = FirebaseStorage.getInstance().getReference("Profile")


       binding.ProfileImage.setOnClickListener {
            hideKeyboard(binding.root)
          ChooseImage()
        }


       binding.BtnSet.setOnClickListener {
            CheckInput()
        }


       binding.TextSkip.setOnClickListener {
             SkipProfile()
        }


    }

    //Load Image to Profile view
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK ){
                Path = data.data
               binding.ProfileImage.setImageURI(Path)
            }
        }
    }


    private fun ChooseImage(){
        var i : Intent = Intent(Intent.ACTION_PICK);
        i.setType("image/*")
        startActivityForResult(i,PICK_IMAGE)
    }

    //Check Input User name
    private fun CheckInput(){
        if (binding.TextUserName.text.toString().isEmpty()){
            binding.TextUserName.setError("Please Enter your name")
        }
        else if (binding.TextUserName.text.toString().length<4){
            binding.TextUserName.setError("Please Enter your full name")
        }
        else if (Path==null){
              NoPhoto()
        }
        else{
            WithPhoto()
        }
    }

    // case user just set name
    private fun NoPhoto() {
        binding.Form.isVisible = false
        binding.SHOWPROGRESS.isVisible = true
        storage.child("avatar.png").downloadUrl.addOnSuccessListener {
            var Url = ""
            var Name =binding.TextUserName.text.toString()
            var Email = user.email
            var ID = auth.uid
            var Login = "Yes"
            Url = it.toString()
            var map: HashMap<String, String> = HashMap<String, String>()
            map.put("ID", ID.toString())
            map.put("Email", Email.toString())
            map.put("Name", Name)
            map.put("Profile", Url.toString())
            map.put("Login", Login)
            map.put("Language","English")
            database.child(ID!!).setValue(map).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.Form.isVisible = true
                    binding.SHOWPROGRESS.isVisible = false
                    Toast(this).ShowMessage("Setup profile Success",this,R.drawable.tick)
                    startActivity(Intent(this,HomePageActivity::class.java))
                } else {
                    binding.Form.isVisible = true
                    binding.SHOWPROGRESS.isVisible = false
                    Toast.makeText(this, "Error : {${it.exception}}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    //case user set name and photo
    private fun WithPhoto(){
        val ID = auth.uid
       binding.Form.isVisible = false
        binding.SHOWPROGRESS.isVisible = true
        storage.child(ID!!).putFile(Path!!).addOnCompleteListener {
            if (it.isSuccessful){
                storage.child(ID!!).downloadUrl.addOnSuccessListener {
                    var Url = ""
                    var Name = binding.TextUserName.text.toString()
                    var Email = user.email
                    var Login = "Yes"
                    Url = it.toString()
                    var map: HashMap<String, String> = HashMap<String, String>()
                    map.put("ID", ID.toString())
                    map.put("Email", Email.toString())
                    map.put("Name", Name)
                    map.put("Profile", Url.toString())
                    map.put("Login", Login)
                    map.put("Language","English")
                    database.child(ID!!).setValue(map).addOnCompleteListener {
                        if (it.isSuccessful) {
                            binding.Form.isVisible = true
                            binding.SHOWPROGRESS.isVisible = false
                            Toast(this).ShowMessage("Setup profile Success",this,R.drawable.tick)
                            startActivity(Intent(this,HomePageActivity::class.java))
                        } else {
                            binding.Form.isVisible = true
                            binding.SHOWPROGRESS.isVisible = false
                            Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                        }
                    }
                }
            }else{
                binding.Form.isVisible = true
                binding.SHOWPROGRESS.isVisible = false
                Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)

            }
        }

    }

    //Case User skip set up profile
    private fun SkipProfile(){
        binding.Form.isVisible = false
        binding.SHOWPROGRESS.isVisible = true
        storage.child("avatar.png").downloadUrl.addOnSuccessListener {
            var r : Random = Random()
            var Url=""
            var Name="player_"+r.nextInt(1000)
            var Email =user.email
            var ID = auth.uid
            var Login = "Yes"
            Url = it.toString()
            var map:HashMap<String,String> = HashMap<String,String>()
            map.put("ID",ID.toString())
            map.put("Email",Email.toString())
            map.put("Name",Name)
            map.put("Profile",Url.toString())
            map.put("Login",Login)
            map.put("Language","English")
            database.child(ID!!).setValue(map).addOnCompleteListener {
                if (it.isSuccessful){
                    binding.Form.isVisible = true
                    binding.SHOWPROGRESS.isVisible =false
                    Toast(this).ShowMessage("Setup profile Success",this,R.drawable.tick)
                    startActivity(Intent(this,HomePageActivity::class.java))
                }else{
                    binding.Form.isVisible = true
                    binding.SHOWPROGRESS.isVisible =false
                    Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)

                }
            }
        }
    }



}