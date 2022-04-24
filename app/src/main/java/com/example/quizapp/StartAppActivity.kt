package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.quizapp.Question.MathQuestion
import com.example.quizapp.Question.ScienceQuestion

class StartAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        Handler().postDelayed({
                       startActivity(Intent(this,SignINActivity::class.java))
             }
            ,1000)


        val m = MathQuestion()
        Log.d("Size",m.getAllQuestions().size.toString())
        val s = ScienceQuestion()
        Log.d("Size Science ",s.getAllQuestions().size.toString())

    }


}