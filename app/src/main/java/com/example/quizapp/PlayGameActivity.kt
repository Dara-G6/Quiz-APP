package com.example.quizapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.Question.GeneralQuestion
import com.example.quizapp.Question.MathQuestion
import com.example.quizapp.Question.Question
import com.example.quizapp.Question.ScienceQuestion
import com.example.quizapp.databinding.ActivityPlayGameBinding
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.properties.Delegates
import kotlin.random.Random as Random

class PlayGameActivity : AppCompatActivity() {



    //dialog
    private lateinit var dialog:Dialog
    private var count:Long=0
    private var Newpoint:Long=0
    private var NewTime:Long=0
    private lateinit var r :Random
    private var index by Delegates.notNull<Int>()
    private var Answer :String= ""
    private var TimePause=0
    private lateinit var ListQuestion:ArrayList<Question>
    private lateinit var  currentQuestion:Question

    //Push to database
    private lateinit var Name:String
    private lateinit var ID:String
    private lateinit var Path:String
    private  var OldTime:Long = 0
    private  var OldPoint:Long=0
    private lateinit var database:DatabaseReference

    //binding
    private lateinit var binding: ActivityPlayGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Dialog
        dialog = Dialog(this)


        //card View
       binding.CardA.setOnClickListener{
           OnClickcardA()

        }

       binding.CardB.setOnClickListener{
             OnClickcardB()

        }


       binding.CardC.setOnClickListener{
           OnClickcardC()

        }


       binding.BtnClose.setOnClickListener{
           ShowDialogPauseGame()
        }


        r= Random

        getQuestion()
        ShowQuestion()


       binding.BtnNext.setOnClickListener {
            PlayGame()
        }

    }

    override fun onStart() {
        super.onStart()
        StartCountTime()
        val type = intent.getStringExtra("TypeGame")
        Name = intent.getStringExtra("Name").toString()
        ID   = intent.getStringExtra("ID").toString()
        Path = intent.getStringExtra("Path").toString()
        Log.d("Type ",type.toString())
        database = FirebaseDatabase.getInstance().getReference(type.toString())
        database.child(ID).get().addOnSuccessListener {
            if (it.exists()){
                OldPoint = it.child("Point").getValue().toString().toLong()
                OldTime  = it.child("Time").getValue().toString().toLong()

            }else{
                OldPoint =0
                OldTime  =0
            }
        }
    }


    // get question
    private fun getQuestion(){
        ListQuestion = ArrayList<Question>()
        if (intent.getStringExtra("TypeGame").toString().equals("Math")){
            var m = MathQuestion()
            index =r.nextInt(m.getAllQuestions().size-10)
            ListQuestion = m.getAllQuestions()
            binding.TextQuestion.textSize= 22F
            binding.TextType.setText(R.string.math)
        }else if(intent.getStringExtra("TypeGame").toString().equals("Science")){
            var s = ScienceQuestion()
            index=r.nextInt(s.getAllQuestions().size-10)
            ListQuestion = s.getAllQuestions()
            binding.TextQuestion.textSize= 22F
            binding.TextType.setText(R.string.science)
        }else{
            var g = GeneralQuestion()
            ListQuestion = g.getAllQuestions()
            binding.TextType.setText(R.string.general_knowledge)
            binding.TextQuestion.textSize= 22F
        }
        currentQuestion = ListQuestion.get(index)
    }


    //play game
    private fun PlayGame(){
        if (Answer.equals("")){
            Toast(this).ShowMessage("សូមជ្រើសរើសចម្លើយ",this, R.drawable.close_red)
        }else{
            if (currentQuestion.Answer.equals(Answer)){
                Newpoint=Newpoint+10
            }else{
                 ShowIncorrect()
            }

            if (count<10){
                currentQuestion = ListQuestion.get(index)
                ShowQuestion()
            }else{
                Pause()
                UpdatePoint(Newpoint,NewTime,OldPoint,OldTime)
                ShowResult()
            }
            AfterClick()
        }
        binding.TextCountPoint.setText("ពិន្ទុ :$Newpoint/100")
    }

    //set question to text view
    private fun ShowQuestion(){
        var random = r.nextInt(6)

         Log.d("Random ",random.toString())
         binding.TextCountQuestion.setText("សំណួរ : ${count+1}/10")
         binding.TextQuestion.setText( currentQuestion.Question)
         if (random==0){
             //C B A
             binding.TextOptionA.setText( currentQuestion.OptionC)
             binding.TextOptionB.setText( currentQuestion.OptionB)
             binding.TextOptionC.setText( currentQuestion.OptionA)
         }
         if (random==1){
             //A C B
             binding.TextOptionA.setText( currentQuestion.OptionA)
             binding.TextOptionB.setText( currentQuestion.OptionC)
             binding.TextOptionC.setText( currentQuestion.OptionB)
        }
         if (random==2){
             //A B C
             binding.TextOptionA.setText( currentQuestion.OptionA)
             binding.TextOptionB.setText( currentQuestion.OptionB)
             binding.TextOptionC.setText( currentQuestion.OptionC)
        }

        if (random ==3){
            //C A B
            binding.TextOptionA.setText( currentQuestion.OptionC)
            binding.TextOptionB.setText( currentQuestion.OptionA)
            binding.TextOptionC.setText( currentQuestion.OptionB)
        }
        if (random == 4){
            // B C A
            binding.TextOptionA.setText( currentQuestion.OptionB)
            binding.TextOptionB.setText( currentQuestion.OptionC)
            binding.TextOptionC.setText( currentQuestion.OptionA)
        }

        if (random == 5){
            // B A C
            binding.TextOptionA.setText( currentQuestion.OptionB)
            binding.TextOptionB.setText( currentQuestion.OptionA)
            binding.TextOptionC.setText( currentQuestion.OptionC)
        }
        count++
        index++
    }


    //show result game
    private fun ShowResult(){
        Pause()
        dialog.setContentView(R.layout.dialog_result)
        val ip = WindowManager.LayoutParams()
        ip.copyFrom(dialog.window!!.attributes)
        ip.width = WindowManager.LayoutParams.MATCH_PARENT
        ip.height = WindowManager.LayoutParams.WRAP_CONTENT
        ip.gravity = Gravity.CENTER
        dialog.window!!.attributes=ip

        var BtnRestart = dialog.findViewById<Button>(R.id.BtnRestart)
        BtnRestart.setOnClickListener {
            dialog.dismiss()
            RestartGame()
        }

        var BtnHome = dialog.findViewById<Button>(R.id.BtnHome)
        BtnHome.setOnClickListener {
            finish()
        }

        var TextScore = dialog.findViewById<TextView>(R.id.TextScore)
        TextScore.setText("ពិន្ទុ : $Newpoint/100")

        var TextTime = dialog.findViewById<TextView>(R.id.TextTime)
        TextTime.setText("រយះពេលបញ្ចប់ : ${(NewTime/1000).toDouble()}sec")

        dialog.show()

    }



    //dialog pause game
    private fun ShowDialogPauseGame(){
        dialog.setContentView(R.layout.dialog_pause)

        val ip = WindowManager.LayoutParams()
        ip.copyFrom(dialog.window!!.attributes)
        ip.width = WindowManager.LayoutParams.MATCH_PARENT
        ip.height = WindowManager.LayoutParams.WRAP_CONTENT
        ip.gravity = Gravity.CENTER
        dialog.window!!.attributes=ip

        val BtnNo = dialog.findViewById<Button>(R.id.BtnNo)
        val BtnYes = dialog.findViewById<Button>(R.id.BtnYes)
        val BtnRestart = dialog.findViewById<Button>(R.id.BtnRestart)

        Pause()
        BtnNo.setOnClickListener{
            dialog.dismiss()
            StartCountTime()

        }

        BtnYes.setOnClickListener {
            finish()
        }

        BtnRestart.setOnClickListener {
            dialog.dismiss()
            RestartGame()
        }

        dialog.show()
    }


    //Restart Game
    private fun RestartGame(){
        count=0
        TimePause=0
        NewTime=0
        Newpoint=0
        binding.TextCountPoint.setText("ពិន្ទុ : $Newpoint/100")
        getQuestion()
        currentQuestion = ListQuestion.get(index)
        ShowQuestion()
        StartCountTime()
        AfterClick()
    }

    //Show correct answer
    private fun ShowIncorrect(){
        Pause()
        dialog.setContentView(R.layout.dialog_incorrect)

        val ip = WindowManager.LayoutParams()
        ip.copyFrom(dialog.window!!.attributes)
        ip.width = WindowManager.LayoutParams.MATCH_PARENT
        ip.height = WindowManager.LayoutParams.WRAP_CONTENT
        ip.gravity = Gravity.CENTER
        dialog.window!!.attributes=ip

        val BtnOk = dialog.findViewById<Button>(R.id.BtnOk)
        val TextAnwser = dialog.findViewById<TextView>(R.id.TextAnswer)
        TextAnwser.setText(currentQuestion.Answer)
        BtnOk.setOnClickListener {
            StartCountTime()
            dialog.dismiss()
        }

        dialog.show()
    }



    //Start Count Time
    private fun StartCountTime(){
        binding.TextTime.format="រយះពេល : %s"
        binding.TextTime.base = SystemClock.elapsedRealtime()-TimePause
        binding.TextTime.start()
    }

    //Pause count time
    private fun Pause(){
        binding.TextTime.stop()
        TimePause = (SystemClock.elapsedRealtime() - binding.TextTime.base.toLong()).toInt()
        NewTime = (SystemClock.elapsedRealtime() - binding.TextTime.base )
    }


    //Update point
    private fun UpdatePoint(newPoint:Long,newTime:Long , oldPoint:Long,oldTime: Long){
        val map :HashMap<String,String> = HashMap()
        map.put("Name",Name)
        map.put("ID",ID)
        map.put("Path",Path)

        if (newPoint == oldPoint){
            if(newTime<oldTime){
                map.put("Point",newPoint.toString())
                map.put("Time",newTime.toString())
                database.child(ID).updateChildren(map as Map<String, Any>)
            }
        }
       else if (newPoint>oldPoint){
            map.put("Point",newPoint.toString())
            map.put("Time",newTime.toString())
            database.child(ID).updateChildren(map as Map<String, Any>)
        }
    }

    private fun OnClickcardA(){
        binding.CardA.setBackgroundColor(getColor(R.color.primary))
        binding.CardB.setBackgroundColor(getColor(R.color.white))
        binding.CardC.setBackgroundColor(getColor(R.color.white))

        binding.TextOptionA.setTextColor(getColor(R.color.white))
        binding.TextOptionB.setTextColor(getColor(R.color.black))
        binding.TextOptionC.setTextColor(getColor(R.color.black))

        Answer = binding.TextOptionA.text.toString()
    }

    private fun OnClickcardB(){
        binding.CardB.setBackgroundColor(getColor(R.color.primary))
        binding.CardA.setBackgroundColor(getColor(R.color.white))
        binding.CardC.setBackgroundColor(getColor(R.color.white))

        binding.TextOptionB.setTextColor(getColor(R.color.white))
        binding.TextOptionA.setTextColor(getColor(R.color.black))
        binding.TextOptionC.setTextColor(getColor(R.color.black))

        Answer = binding.TextOptionB.text.toString()
    }

    private fun OnClickcardC(){
        binding.CardC.setBackgroundColor(getColor(R.color.primary))
        binding.CardB.setBackgroundColor(getColor(R.color.white))
        binding.CardA.setBackgroundColor(getColor(R.color.white))

        binding.TextOptionC.setTextColor(getColor(R.color.white))
        binding.TextOptionB.setTextColor(getColor(R.color.black))
        binding.TextOptionA.setTextColor(getColor(R.color.black))

        Answer = binding.TextOptionC.text.toString()
    }

    private fun AfterClick(){
        binding.CardC.setBackgroundColor(getColor(R.color.white))
        binding.CardB.setBackgroundColor(getColor(R.color.white))
        binding.CardA.setBackgroundColor(getColor(R.color.white))

        binding.TextOptionC.setTextColor(getColor(R.color.black))
        binding.TextOptionB.setTextColor(getColor(R.color.black))
        binding.TextOptionA.setTextColor(getColor(R.color.black))
        Answer = ""
    }

}