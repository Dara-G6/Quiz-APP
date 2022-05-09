package com.example.quizapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.quizapp.PlayGameActivity
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.*


class HomeFragment : Fragment() {





    //Firebase
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var storage:StorageReference

    //Cast Intent to  next screen
    private lateinit var Name:String
    private lateinit var ID:String
    private lateinit var Path:String
    private lateinit var intent:Intent

    //binding
    private lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentHomeBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        storage = FirebaseStorage.getInstance().getReference("Profile")



        binding.BtnMath.setOnClickListener {
             intent = Intent(activity,PlayGameActivity::class.java)
             intent.putExtra("TypeGame","Math")
             intent.putExtra("Name",Name)
             intent.putExtra("ID",ID)
             intent.putExtra("Path",Path)
            startActivity(intent)
        }


       binding.BtnScience.setOnClickListener {
            intent = Intent(activity,PlayGameActivity::class.java)
            intent.putExtra("TypeGame","Science")
            intent.putExtra("Name",Name)
            intent.putExtra("ID",ID)
            intent.putExtra("Path",Path)
            startActivity(intent)
        }


       binding.BtnGeneral.setOnClickListener {
            intent = Intent(activity,PlayGameActivity::class.java)
            intent.putExtra("TypeGame","General Knowledge")
            intent.putExtra("Name",Name)
            intent.putExtra("ID",ID)
            intent.putExtra("Path",Path)
            startActivity(intent)
        }



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setProfile()
    }


    private fun setProfile(){
        if (auth.uid!=null){
            database.child(auth.uid.toString()).get().addOnSuccessListener {
                if (it.exists()){
                   var name = it.child("Name").value.toString()
                   var Path = it.child("Profile").value.toString()
                    Name = name
                    ID = auth.uid.toString()
                    this.Path = Path
                   binding.TextDisplayName.setText(name)
                    Picasso.get().load(Path).into(binding.ProfileImage)
                }
            }
        }
    }




}