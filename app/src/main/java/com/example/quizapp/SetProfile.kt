package com.example.quizapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class SetProfile : AppCompatActivity() {

    private lateinit var Form:View
    private lateinit var ShowProgress:View

    private lateinit var BtnSet :Button
    private lateinit var ProfileImage:ImageView
    private lateinit var TextUserName:EditText
    private lateinit var TextSkip :TextView

    private var Path:Uri?=null
    private val PICK_IMAGE =1


    private lateinit var auth:FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_profile)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        database = FirebaseDatabase.getInstance().getReference("Users")
        storage = FirebaseStorage.getInstance().getReference("Profile")

        ProfileImage = findViewById(R.id.ProfileImage)
        ProfileImage.setOnClickListener {
          ChooseImage()
        }

        BtnSet = findViewById(R.id.BtnSet)
        BtnSet.setOnClickListener {
            CheckInput()
        }

        TextSkip = findViewById(R.id.TextSkip)
        TextSkip.setOnClickListener {
             SkipProfile()
        }

        TextUserName = findViewById(R.id.TextUserName)

        Form = findViewById(R.id.Form)
        ShowProgress = findViewById(R.id.SHOW_PROGRESS)
    }

    //Load Image to Profile view
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK ){
                Path = data.data
                ProfileImage.setImageURI(Path)
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
        if (TextUserName.text.isEmpty()){
            TextUserName.setError("Please Enter your name")
        }else if (TextUserName.text.length<4){
            TextUserName.setError("Please Enter your full name")
        }else if (Path==null){
              NoPhoto()
        }else{
            WithPhoto()
        }
    }

    // case user just set name
    private fun NoPhoto() {
        Form.isVisible = false
        ShowProgress.isVisible = true
        storage.child("avatar.png").downloadUrl.addOnSuccessListener {
            var Url = ""
            var Name = TextUserName.text.toString()
            var Email = user.email
            var ID = auth.uid
            var Login = "No"
            Url = it.toString()
            var map: HashMap<String, String> = HashMap<String, String>()
            map.put("ID", ID.toString())
            map.put("Email", Email.toString())
            map.put("Name", Name)
            map.put("Profile", Url.toString())
            map.put("Login", Login)
            database.child(ID!!).setValue(map).addOnCompleteListener {
                if (it.isSuccessful) {
                    Form.isVisible = true
                    ShowProgress.isVisible = false
                    Toast(this).ShowMessage("Setup profile Success",this,R.drawable.tick)
                    startActivity(Intent(this, SignIN::class.java))
                } else {
                    Form.isVisible = true
                    ShowProgress.isVisible = false
                    Toast.makeText(this, "Error : {${it.exception}}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    //case user set name and photo
    private fun WithPhoto(){
        var ID = auth.uid
        Form.isVisible = false
        ShowProgress.isVisible = true
        storage.child(ID!!).putFile(Path!!).addOnCompleteListener {
            if (it.isSuccessful){
                storage.child(ID!!).downloadUrl.addOnSuccessListener {
                    var Url = ""
                    var Name = TextUserName.text.toString()
                    var Email = user.email
                    var Login = "No"
                    Url = it.toString()
                    var map: HashMap<String, String> = HashMap<String, String>()
                    map.put("ID", ID.toString())
                    map.put("Email", Email.toString())
                    map.put("Name", Name)
                    map.put("Profile", Url.toString())
                    map.put("Login", Login)
                    database.child(ID!!).setValue(map).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Form.isVisible = true
                            ShowProgress.isVisible = false
                            Toast(this).ShowMessage("Setup profile Success",this,R.drawable.tick)
                            startActivity(Intent(this, SignIN::class.java))
                        } else {
                            Form.isVisible = true
                            ShowProgress.isVisible = false
                            Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)
                        }
                    }
                }
            }else{
                Form.isVisible = true
                ShowProgress.isVisible = false
                Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)

            }
        }

    }

    //Case User skip set up profile
    private fun SkipProfile(){
        Form.isVisible = false
        ShowProgress.isVisible = true
        storage.child("avatar.png").downloadUrl.addOnSuccessListener {
            var r : Random = Random()
            var Url=""
            var Name="player_"+r.nextInt(1000000000)
            var Email =user.email
            var ID = auth.uid
            var Login = "No"
            Url = it.toString()
            var map:HashMap<String,String> = HashMap<String,String>()
            map.put("ID",ID.toString())
            map.put("Email",Email.toString())
            map.put("Name",Name)
            map.put("Profile",Url.toString())
            map.put("Login",Login)
            database.child(ID!!).setValue(map).addOnCompleteListener {
                if (it.isSuccessful){
                    Form.isVisible = true
                    ShowProgress.isVisible =false
                    Toast(this).ShowMessage("Setup profile Success",this,R.drawable.tick)
                    startActivity(Intent(this,SignIN::class.java))
                }else{
                    Form.isVisible = true
                    ShowProgress.isVisible =false
                    Toast(this).ShowMessage("Error : ${it.exception}",this,R.drawable.x_mark)

                }
            }
        }
    }



}