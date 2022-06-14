package com.example.quizapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.quizapp.R
import com.example.quizapp.dataclass.Rank
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class AdaperRank(context: Context, resource: Int, objects:ArrayList<
        Rank>) :
    ArrayAdapter<Rank>(context, resource, objects) {

    val List:ArrayList<Rank> = objects
    val C= context
    val auth = FirebaseAuth.getInstance()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater = C.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rankView = layoutInflater.inflate(R.layout.adapter_rank,parent,false)

        //Text View
        val TextNo = rankView.findViewById<TextView>(R.id.TextNo)
        TextNo.text = "${position+1}"

        val TextName = rankView.findViewById<TextView>(R.id.TextName)
        TextName.setText(List[position].Name)

        val TextPoint = rankView.findViewById<TextView>(R.id.TextPoint)
        TextPoint.setText(List[position].Point.toString())

        val TextTime = rankView.findViewById<TextView>(R.id.TextTime)
        TextTime.setText(List[position].Time.toString())


        //Image
        val ImageMedal = rankView.findViewById<ImageView>(R.id.ImageMedal)
        when (position) {
            0 -> {
                ImageMedal.setImageResource(R.drawable.medal1)
            }
            1 -> {
                ImageMedal.setImageResource(R.drawable.medal2)
            }
            2 -> {
                ImageMedal.setImageResource(R.drawable.medal3)
            }
            else -> {
                Picasso.get().load("https").into(ImageMedal)
            }
        }

        val ProfileImage = rankView.findViewById<ImageView>(R.id.ProfileImage)
        Picasso.get().load(List[position].Path).into(ProfileImage)

        if(List[position].ID ==auth.uid.toString()){
            rankView.setBackgroundColor(context.getColor(R.color.primary))
            TextName.setTextColor(context.getColor(R.color.white))
            TextNo.setTextColor(context.getColor(R.color.white))
            TextPoint.setTextColor(context.getColor(R.color.white))
            TextTime.setTextColor(context.getColor(R.color.white))
            TextName.text = context.getString(R.string.you)
        }

        return rankView
    }
}