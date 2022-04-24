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
import com.example.quizapp.toast.ShowMessage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.properties.Delegates
import kotlin.random.Random as Random

class PlayGameActivity : AppCompatActivity() {
    //Text view
    private lateinit var TextType:TextView
    private lateinit var TextCountQuesion:TextView
    private lateinit var TextCountPoint:TextView
    private lateinit var TextQuestion:TextView
    private lateinit var TextOptionA:TextView
    private lateinit var TextOptionB:TextView
    private lateinit var TextOptionC:TextView

    //Time
    private lateinit var TimeStart:Chronometer
    private  var TimePause:Long=0
    private var NewTime:Long=0;

    //Button
    private lateinit var BtnClose:Button
    private lateinit var BtnNext:Button

    //View
    private lateinit var CardA:View
    private lateinit var CardB:View
    private lateinit var CardC:View



    //dialog
    private lateinit var dialog:Dialog

    private var count:Long=0
    private var Newpoint:Long=0
    private lateinit var r :Random
    private var index by Delegates.notNull<Int>()
    private var Answer :String= ""
    private lateinit var ListQuestion:ArrayList<Question>
    private lateinit var  currentQuestion:Question

    //Push to database
    private lateinit var Name:String
    private lateinit var ID:String
    private lateinit var Path:String
    private  var OldTime:Long = 0
    private  var OldPoint:Long=0

    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)
        //Dialog
        dialog = Dialog(this)
        //Text View
        TextType = findViewById(R.id.TextType)
        TimeStart = findViewById(R.id.TextTime)
        TextCountQuesion = findViewById(R.id.TextCountQuestion)
        TextCountPoint = findViewById(R.id.TextCountPoint)
        TextQuestion =findViewById(R.id.TextQuestion)
        TextOptionA  =findViewById(R.id.TextOptionA)
        TextOptionB  = findViewById(R.id.TextOptionB)
        TextOptionC  = findViewById(R.id.TextOptionC)

        //View
        CardA = findViewById(R.id.CardA)
        CardA.setOnClickListener{
           OnClickcardA()

        }

        CardB = findViewById(R.id.CardB)
        CardB.setOnClickListener{
             OnClickcardB()

        }

        CardC = findViewById(R.id.CardC)
        CardC.setOnClickListener{
           OnClickcardC()

        }

        //Button
        BtnClose = findViewById(R.id.BtnClose)
        BtnClose.setOnClickListener{
           ShowDialogPauseGame()
        }


        r= Random

        getQuestion()
        ShowQuestion()

        BtnNext = findViewById(R.id.BtnNext)
        BtnNext.setOnClickListener {
            PlayGame()
        }

    }

    override fun onStart() {
        super.onStart()
        StartCountTime()
        TextType.setText(intent.getStringExtra("TypeGame"))
        Name = intent.getStringExtra("Name").toString()
        ID   = intent.getStringExtra("ID").toString()
        Path = intent.getStringExtra("Path").toString()

        database = FirebaseDatabase.getInstance().getReference(TextType.text.toString())
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
        }else if(intent.getStringExtra("TypeGame").toString().equals("Science")){
            var s = ScienceQuestion()
            index=r.nextInt(s.getAllQuestions().size-10)
            ListQuestion = s.getAllQuestions()
        }else{
            var g = GeneralQuestion()
            ListQuestion = g.getAllQuestions()

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
        TextCountPoint.setText("Point :$Newpoint/100")
    }

    //set question to text view
    private fun ShowQuestion(){
        var random = r.nextInt(6)

         Log.d("Random ",random.toString())
         TextCountQuesion.setText("Question : ${count+1}/10")
         TextQuestion.setText( currentQuestion.Question)
         if (random==0){
             //C B A
             TextOptionA.setText( currentQuestion.OptionC)
             TextOptionB.setText( currentQuestion.OptionB)
             TextOptionC.setText( currentQuestion.OptionA)
         }
         if (random==1){
             //A C B
             TextOptionA.setText( currentQuestion.OptionA)
             TextOptionB.setText( currentQuestion.OptionC)
             TextOptionC.setText( currentQuestion.OptionB)
        }
         if (random==2){
             //A B C
             TextOptionA.setText( currentQuestion.OptionA)
             TextOptionB.setText( currentQuestion.OptionB)
             TextOptionC.setText( currentQuestion.OptionC)
        }

        if (random ==3){
            //C A B
            TextOptionA.setText( currentQuestion.OptionC)
            TextOptionB.setText( currentQuestion.OptionA)
            TextOptionC.setText( currentQuestion.OptionB)
        }
        if (random == 4){
            // B C A
            TextOptionA.setText( currentQuestion.OptionB)
            TextOptionB.setText( currentQuestion.OptionC)
            TextOptionC.setText( currentQuestion.OptionA)
        }

        if (random == 5){
            // B A C
            TextOptionA.setText( currentQuestion.OptionB)
            TextOptionB.setText( currentQuestion.OptionA)
            TextOptionC.setText( currentQuestion.OptionC)
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
        TextScore.setText("Score : $Newpoint/100")

        var TextTime = dialog.findViewById<TextView>(R.id.TextTime)
        TextTime.setText("Time completed : ${(NewTime/1000).toDouble()}sec")

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
        TextCountPoint.setText("Point : $Newpoint/100")
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
        TimeStart.format="Time : %s"
        TimeStart.base = SystemClock.elapsedRealtime()-TimePause
        TimeStart.start()
    }

    //Pause count time
    private fun Pause(){
        TimeStart.stop()
        TimePause = SystemClock.elapsedRealtime() - TimeStart.base
        NewTime = (SystemClock.elapsedRealtime() - TimeStart.base )
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
        CardA.setBackgroundColor(getColor(R.color.primary))
        CardB.setBackgroundColor(getColor(R.color.white))
        CardC.setBackgroundColor(getColor(R.color.white))

        TextOptionA.setTextColor(getColor(R.color.white))
        TextOptionB.setTextColor(getColor(R.color.black))
        TextOptionC.setTextColor(getColor(R.color.black))

        Answer = TextOptionA.text.toString()
    }

    private fun OnClickcardB(){
        CardB.setBackgroundColor(getColor(R.color.primary))
        CardA.setBackgroundColor(getColor(R.color.white))
        CardC.setBackgroundColor(getColor(R.color.white))

        TextOptionB.setTextColor(getColor(R.color.white))
        TextOptionA.setTextColor(getColor(R.color.black))
        TextOptionC.setTextColor(getColor(R.color.black))

        Answer = TextOptionB.text.toString()
    }

    private fun OnClickcardC(){
        CardC.setBackgroundColor(getColor(R.color.primary))
        CardB.setBackgroundColor(getColor(R.color.white))
        CardA.setBackgroundColor(getColor(R.color.white))

        TextOptionC.setTextColor(getColor(R.color.white))
        TextOptionB.setTextColor(getColor(R.color.black))
        TextOptionA.setTextColor(getColor(R.color.black))

        Answer = TextOptionC.text.toString()
    }

    private fun AfterClick(){
        CardC.setBackgroundColor(getColor(R.color.white))
        CardB.setBackgroundColor(getColor(R.color.white))
        CardA.setBackgroundColor(getColor(R.color.white))

        TextOptionC.setTextColor(getColor(R.color.black))
        TextOptionB.setTextColor(getColor(R.color.black))
        TextOptionA.setTextColor(getColor(R.color.black))
        Answer = ""
    }

}