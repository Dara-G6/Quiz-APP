package com.example.quizapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class AdaperRank(context: Context, resource: Int, objects:ArrayList<Rank>) :
    ArrayAdapter<Rank>(context, resource, objects) {

    val List:ArrayList<Rank> = objects
    val C= context

    val auth = FirebaseAuth.getInstance()

    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater = C.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val RankView = layoutInflater.inflate(R.layout.adapter_rank,parent,false)

        //Text View
        val TextNo = RankView.findViewById<TextView>(R.id.TextNo)
        TextNo.setText("${position+1}")

        val TextName = RankView.findViewById<TextView>(R.id.TextName)
        TextName.setText(List.get(position).Name)

        val TextPoint = RankView.findViewById<TextView>(R.id.TextPoint)
        TextPoint.setText(List.get(position).Point)

        val TextTime = RankView.findViewById<TextView>(R.id.TextTime)
        TextTime.setText(List.get(position).Time)


        //Image
        val ImageMedal = RankView.findViewById<ImageView>(R.id.ImageMedal)
        if (position==0){
            ImageMedal.setImageResource(R.drawable.medal1)
        }
        else if (position==1){
            ImageMedal.setImageResource(R.drawable.medal2)
        }else if (position==2){
            ImageMedal.setImageResource(R.drawable.medal3)
        }else{
            ImageMedal.setImageResource(R.drawable.medal)
        }

        val ProfileImage = RankView.findViewById<ImageView>(R.id.ProfileImage)
        Picasso.get().load(List.get(position).Path).into(ProfileImage)

        if(List.get(position).ID ==auth.uid.toString()){
            TextName.setText("You")
        }

        return RankView
    }
}