package com.example.quizapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.quizapp.PlayGameActivity
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class HomeFragment : Fragment() {


    private lateinit var ProfileImage:ImageView
    private lateinit var TextDisplayName:TextView


    private lateinit var auth:FirebaseAuth
    private lateinit var user:FirebaseUser
    private lateinit var database:DatabaseReference
    private lateinit var storage:StorageReference

    private lateinit var BtnMath:Button
    private lateinit var BtnScience:Button
    private lateinit var BtnGeneral:Button



    //Intent
    private lateinit var intent:Intent

    //Cast to  next screen
    private lateinit var Name:String
    private lateinit var ID:String
    private lateinit var Path:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_home, container, false)
        ProfileImage = view.findViewById(R.id.ProfileImage)
        TextDisplayName = view.findViewById(R.id.TextDisplayName)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        storage = FirebaseStorage.getInstance().getReference("Profile")


        BtnMath = view.findViewById(R.id.BtnMath)
        BtnMath.setOnClickListener {
             intent = Intent(activity,PlayGameActivity::class.java)
             intent.putExtra("TypeGame","Math")
             intent.putExtra("Name",Name)
             intent.putExtra("ID",ID)
             intent.putExtra("Path",Path)
            startActivity(intent)
        }

        BtnScience = view.findViewById(R.id.BtnScience)
        BtnScience.setOnClickListener {
            intent = Intent(activity,PlayGameActivity::class.java)
            intent.putExtra("TypeGame","Science")
            intent.putExtra("Name",Name)
            intent.putExtra("ID",ID)
            intent.putExtra("Path",Path)
            startActivity(intent)
        }

        BtnGeneral = view.findViewById(R.id.BtnGeneral)
        BtnGeneral.setOnClickListener {
            intent = Intent(activity,PlayGameActivity::class.java)
            intent.putExtra("TypeGame","General Knowledge")
            intent.putExtra("Name",Name)
            intent.putExtra("ID",ID)
            intent.putExtra("Path",Path)
            startActivity(intent)
        }


        return view
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
                    TextDisplayName.setText(name)
                    Picasso.get().load(Path).into(ProfileImage)
                }
            }
        }
    }


}