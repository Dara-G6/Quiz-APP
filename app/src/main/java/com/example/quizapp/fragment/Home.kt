package com.example.quizapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class Home : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    private lateinit var ProfileImage:ImageView
    private lateinit var TextDisplayName:TextView


    private lateinit var auth:FirebaseAuth
    private lateinit var user:FirebaseUser
    private lateinit var database:DatabaseReference
    private lateinit var storage:StorageReference


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

        setProfile()

        return view
    }


    private fun setProfile(){
        var Path =""
        if (auth.uid!=null){
            database.child(auth.uid.toString()).get().addOnSuccessListener {
                if (it.exists()){
                   var name = it.child("Name").value.toString()
                   var Post =it.child("Post").value.toString()
                   Path = it.child("Profile").value.toString()
                    TextDisplayName.setText(name)
                    Picasso.get().load(Path).into(ProfileImage)
                }
            }
        }
    }


}