package com.example.quizapp.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.quizapp.Edit
import com.example.quizapp.R
import com.example.quizapp.SetProfile
import com.example.quizapp.SignIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class ProfileTab : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Fire base
    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference

    //Text view
    private lateinit var TextEmail:TextView
    private lateinit var TextDisplayName:TextView




    //Button
    private lateinit var BtnEdit :Button
    private lateinit var BtnLogout:Button
    private lateinit var BtnLanguage:Button
    private lateinit var BtnChangePassword:Button
    private lateinit var BtnRank:Button
    private lateinit var BtnDeleteAccount:Button


    //Imageview
    private lateinit var ProfileImage:ImageView

    //View
    private lateinit var Form:View
    private lateinit var ShowProgress:View




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_tab, container, false)

        //Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        storage = FirebaseStorage.getInstance().getReference("Profile")



        //Text view
        TextDisplayName = view.findViewById(R.id.TextDisplayName)
        TextEmail = view.findViewById(R.id.TextEmail)

        //View
        Form = view.findViewById(R.id.Form)
        ShowProgress = view.findViewById(R.id.SHOW_PROGRESS)


        //Button
        BtnEdit = view.findViewById(R.id.BtnEdit)
        BtnEdit.setOnClickListener {
           startActivity(Intent(activity,Edit::class.java))

        }

        BtnLogout = view.findViewById(R.id.BtnLogout)
        BtnLogout.setOnClickListener {
                 Logout()
        }



        //Imageview
        ProfileImage = view.findViewById(R.id.ProfileImage)
        ProfileImage.setOnClickListener {

            startActivity(Intent(activity,Edit::class.java))
        }




        return view
    }


    override fun onStart() {
        super.onStart()
        setProfile()
    }

    //Set Profile
    private fun setProfile() {
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val name =it.child("Name").value.toString()
                val path = it.child("Profile").value.toString()
                val email=it.child("Email").value.toString()
                Picasso.get().load(path).into(ProfileImage)
                TextEmail.setText(email)
                TextDisplayName.setText(name)
            }
        }
    }

    //Logout user
    private fun Logout(){
        database.child(auth.uid.toString()).child("Login").setValue("No")
        auth.signOut()
        Form.isVisible = false
        ShowProgress.isVisible = true
        Handler().postDelayed({
                  startActivity(Intent(activity,SignIN::class.java))
            Form.isVisible = true
            ShowProgress.isVisible = false
                }
            ,3000
        )

    }




}