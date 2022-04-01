package com.example.quizapp.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.ChangePassword
import com.example.quizapp.EditProfile
import com.example.quizapp.R
import com.example.quizapp.SignIN
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
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
    private lateinit var dialog:Dialog
    private lateinit var TextLoading:TextView

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
        TextLoading  = view.findViewById(R.id.TextLoading)
        dialog = Dialog(requireContext())

        //Button
        BtnEdit = view.findViewById(R.id.BtnEdit)
        BtnEdit.setOnClickListener {
           startActivity(Intent(activity,EditProfile::class.java))

        }

        BtnLogout = view.findViewById(R.id.BtnLogout)
        BtnLogout.setOnClickListener {
                 Logout()
        }

        BtnChangePassword = view.findViewById(R.id.BtnChangePassword)
        BtnChangePassword.setOnClickListener {
            startActivity(Intent(activity,ChangePassword::class.java))
        }


        BtnDeleteAccount = view.findViewById(R.id.BtnDeleteAccount)
        BtnDeleteAccount.setOnClickListener {
          ShowDialog()
        }

        //Imageview
        ProfileImage = view.findViewById(R.id.ProfileImage)
        ProfileImage.setOnClickListener {

            startActivity(Intent(activity,EditProfile::class.java))
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
            ,1500
        )

    }


    //Show dialog delete account

    private fun ShowDialog(){
        dialog.setContentView(R.layout.dialog_delete_account)

        val ip = WindowManager.LayoutParams()
        ip.copyFrom(dialog.window!!.attributes)
        ip.width = WindowManager.LayoutParams.MATCH_PARENT
        ip.height = WindowManager.LayoutParams.WRAP_CONTENT
        ip.gravity = Gravity.CENTER


        dialog.window!!.attributes=ip


        val BtnNo = dialog.findViewById<Button>(R.id.BtnNo)
        val BtnYes = dialog.findViewById<Button>(R.id.BtnYes)
        val TextEmail = dialog.findViewById<EditText>(R.id.TextEmail)
        val TextPassword = dialog.findViewById<EditText>(R.id.TextPassword)

        BtnNo.setOnClickListener {
            dialog.dismiss()
        }

        BtnYes.setOnClickListener {

            if (TextEmail.text.isEmpty()||TextPassword.text.isEmpty()){
                Toast(activity).ShowMessage("Please Enter all the the filed", requireActivity()!!,R.drawable.close_red)
            }else{
                dialog.dismiss()
                DeleteUser(TextEmail.text.toString(),TextPassword.text.toString())
            }
        }

        dialog.show()

    }

    private fun DeleteUser(email:String,password:String){
        val user:FirebaseUser= auth.currentUser!!
        val credential:AuthCredential = EmailAuthProvider.
        getCredential(email,password)
        Form.isVisible = false
        ShowProgress.isVisible = true
        TextLoading.setText("Delete account..")
        user.reauthenticate(credential).addOnCompleteListener {
            if (it.isSuccessful){
                database.child(auth.uid.toString()).removeValue()
                storage.child(auth.uid.toString()).delete()
                auth.currentUser!!.delete()
                Form.isVisible = true
                ShowProgress.isVisible = false
                Toast(activity).ShowMessage("Delete account success", requireActivity()!!,
                    R.drawable.tick
                )
                Handler().postDelayed({
                    startActivity(Intent(activity,SignIN::class.java))
                },2000)
            }else{
                Form.isVisible = true
                ShowProgress.isVisible = false
                Toast(activity).ShowMessage("Error : ${it.exception}", requireActivity()!!,R.drawable.close_red)
            }
        }

    }





}