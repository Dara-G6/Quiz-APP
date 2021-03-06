package com.example.quizapp.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.view.isVisible
import com.example.quizapp.*
import com.example.quizapp.databinding.FragmentProfileTabBinding
import com.example.quizapp.toast.showMessage
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.*


class ProfileTabFragment : Fragment() {



    // Fire base
    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference



    //binding
    private lateinit var dialog:Dialog
    private lateinit var binding:FragmentProfileTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentProfileTabBinding.inflate(inflater , container,false)
        //Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        storage = FirebaseStorage.getInstance().getReference("Profile")




       binding.BtnEdit.setOnClickListener {
           startActivity(Intent(activity,EditProfileActivity::class.java))

        }


       binding.BtnLogout.setOnClickListener {
                 logout()
        }


       binding.BtnChangePassword.setOnClickListener {
            startActivity(Intent(activity,ChangePasswordActivity::class.java))
        }



       binding.BtnDeleteAccount.setOnClickListener {
          showDialog()
        }


       binding.BtnSetting.setOnClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }

        //Imageview
       binding.ProfileImage.setOnClickListener {
            startActivity(Intent(activity,EditProfileActivity::class.java))
        }

        binding.BtnRank.setOnClickListener {
            startActivity(Intent(activity,BestScoreActivity::class.java))
        }
        dialog = Dialog(requireContext())

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        setProfile()

    }

    override fun onResume() {
        super.onResume()

    }



    //Set Profile
    private fun setProfile() {
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.exists()){
                val name =it.child("Name").value.toString()
                val path = it.child("Profile").value.toString()
                val email=it.child("Email").value.toString()
                Picasso.get().load(path).into(binding.ProfileImage)
                binding.TextEmail.setText(email)
                binding.TextDisplayName.setText(name)
            }
        }

    }

    //Logout user
    private fun logout(){
        database.child(auth.uid.toString()).child("Login").setValue("No")
        auth.signOut()
        binding.Form.isVisible = false
        binding.SHOWPROGRESS.isVisible = true
        Handler().postDelayed({
                  startActivity(Intent(activity,SignINActivity::class.java))
            binding.Form.isVisible = true
            binding.SHOWPROGRESS.isVisible = false
                }
            ,1500
        )

    }


    //Show dialog delete account
    private fun showDialog(){
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
                Toast(activity).showMessage("Please Enter all the the filed", requireActivity()!!,R.drawable.close_red)
            }else{
                dialog.dismiss()
                deleteUser(TextEmail.text.toString(),TextPassword.text.toString())
            }
        }

        val view = dialog.findViewById<View>(R.id.Layout_DeleteAccount)
        view.setOnClickListener {
           val input = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            input.hideSoftInputFromWindow(view.windowToken,0)
        }


        dialog.show()

    }




    private fun deleteUser(email:String, password:String){
        val user:FirebaseUser= auth.currentUser!!
        val credential:AuthCredential = EmailAuthProvider.
        getCredential(email,password)
        binding.Form.isVisible = false
        binding.SHOWPROGRESS.isVisible = true
        binding.TextLoading.text = "${getString(R.string.delete_account)}"
        user.reauthenticate(credential).addOnCompleteListener {
            if (it.isSuccessful){
                database.child(auth.uid.toString()).removeValue()
                database.database.getReference("General Knowledge").child(auth.uid.toString()).removeValue()
                database.database.getReference("Science").child(auth.uid.toString()).removeValue()
                database.database.getReference("Math").child(auth.uid.toString()).removeValue()
                storage.child(auth.uid.toString()).delete()
                auth.currentUser!!.delete()
                binding.Form.isVisible = true
                binding.SHOWPROGRESS.isVisible = false
                Toast(activity).showMessage("${getString(R.string.delete_account_succeeded)}", requireActivity()!!,
                    R.drawable.tick
                )
                Handler().postDelayed({
                    startActivity(Intent(activity,SignINActivity::class.java))
                },2000)
            }else{
                binding.Form.isVisible = true
                binding.SHOWPROGRESS.isVisible = false
                Toast(activity).showMessage("Error : ${it.exception}", requireActivity()!!,R.drawable.close_red)
            }
        }

    }






}