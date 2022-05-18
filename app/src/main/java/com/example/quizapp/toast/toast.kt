package com.example.quizapp.toast

import android.app.Activity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.R
import org.w3c.dom.Text

fun Toast.showMessage(Message:String, context:Activity, icon:Int){

    val layout = context.layoutInflater.inflate(
        R.layout.toast_success,
        context.findViewById(R.id.toast)
    )

    val Text = layout.findViewById<TextView>(R.id.Text)
    Text.text = Message

    val image = layout.findViewById<ImageView>(R.id.Icon)
    image.setImageResource(icon)


    this.apply {
        setGravity(Gravity.BOTTOM,0,0)
        view = layout
        duration = Toast.LENGTH_LONG
        show()
    }

}

