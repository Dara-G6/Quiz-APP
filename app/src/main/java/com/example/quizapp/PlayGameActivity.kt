package com.example.quizapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.question.GeneralQuestion
import com.example.quizapp.question.MathQuestion
import com.example.quizapp.question.Question
import com.example.quizapp.question.ScienceQuestion
import com.example.quizapp.databinding.ActivityPlayGameBinding
import com.example.quizapp.toast.showMessage
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
           onClickcardA()

        }

       binding.CardB.setOnClickListener{
             OnClickcardB()

        }


       binding.CardC.setOnClickListener{
           onClickcardC()

        }


       binding.BtnClose.setOnClickListener{
           showDialogPauseGame()
        }

        r= Random
        getQuestion()
        showQuestion()

       binding.BtnNext.setOnClickListener {
            playGame()
        }

    }

    override fun onStart() {
        super.onStart()
        startCountTime()
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

    override fun onBackPressed() {
        super.onBackPressed()
        showDialogPauseGame()
    }

    // get question
    private fun getQuestion(){
        ListQuestion = ArrayList<Question>()
        when {
            intent.getStringExtra("TypeGame").toString().equals("Math") -> {
                val m = MathQuestion()
                index =r.nextInt(m.getAllQuestions().size-10)
                ListQuestion = m.getAllQuestions()
                binding.TextType.setText(R.string.math)
            }
            intent.getStringExtra("TypeGame").toString().equals("Science") -> {
                val s = ScienceQuestion()
                index=r.nextInt(s.getAllQuestions().size-10)
                ListQuestion = s.getAllQuestions()
                binding.TextType.setText(R.string.science)
            }
            else -> {
                val g = GeneralQuestion()
                index=r.nextInt(g.getAllQuestions().size-10)
                ListQuestion = g.getAllQuestions()
                binding.TextType.setText(R.string.general_knowledge)
            }
        }
        currentQuestion = ListQuestion[index]
    }


    //play game
    private fun playGame(){
        if (Answer.equals("")){
            Toast(this).showMessage("???????????????????????????????????????????????????",this, R.drawable.close_red)
        }else{
            if (currentQuestion.Answer.equals(Answer)){
                Newpoint=Newpoint+10
            }else{
                 showIncorrect()
            }

            if (count<10){
                currentQuestion = ListQuestion.get(index)
                showQuestion()
            }else{
                pause()
                updatePoint(Newpoint,NewTime,OldPoint,OldTime)
                showResult()
            }
            afterClick()
        }
        binding.TextCountPoint.text = "${getString(R.string.score)}:$Newpoint/100"
    }

    //set question to text view
    private fun showQuestion(){
        val random = r.nextInt(6)

         Log.d("Random ",random.toString())
        binding.TextCountQuestion.text = "${getString(R.string.question)} : ${count+1}/10"
        binding.TextQuestion.text = currentQuestion.Question
         if (random==0){
             //C B A
             binding.TextOptionA.text = currentQuestion.OptionC
             binding.TextOptionB.text = currentQuestion.OptionB
             binding.TextOptionC.text = currentQuestion.OptionA
         }
         if (random==1){
             //A C B
             binding.TextOptionA.text = currentQuestion.OptionA
             binding.TextOptionB.text = currentQuestion.OptionC
             binding.TextOptionC.text = currentQuestion.OptionB
        }
         if (random==2){
             //A B C
             binding.TextOptionA.text = currentQuestion.OptionA
             binding.TextOptionB.text = currentQuestion.OptionB
             binding.TextOptionC.text = currentQuestion.OptionC
        }

        if (random ==3){
            //C A B
            binding.TextOptionA.text = currentQuestion.OptionC
            binding.TextOptionB.text = currentQuestion.OptionA
            binding.TextOptionC.text = currentQuestion.OptionB
        }
        if (random == 4){
            // B C A
            binding.TextOptionA.text = currentQuestion.OptionB
            binding.TextOptionB.text = currentQuestion.OptionC
            binding.TextOptionC.text = currentQuestion.OptionA
        }

        if (random == 5){
            // B A C
            binding.TextOptionA.text = currentQuestion.OptionB
            binding.TextOptionB.text = currentQuestion.OptionA
            binding.TextOptionC.text = currentQuestion.OptionC
        }
        count++
        index++
    }


    //show result game
    private fun showResult(){
        pause()
        dialog.setContentView(R.layout.dialog_result)
        val ip = WindowManager.LayoutParams()
        ip.copyFrom(dialog.window!!.attributes)
        ip.width = WindowManager.LayoutParams.MATCH_PARENT
        ip.height = WindowManager.LayoutParams.WRAP_CONTENT
        ip.gravity = Gravity.CENTER
        dialog.window!!.attributes=ip

        val BtnRestart = dialog.findViewById<Button>(R.id.BtnRestart)
        BtnRestart.setOnClickListener {
            dialog.dismiss()
            restartGame()
        }

        val BtnHome = dialog.findViewById<Button>(R.id.BtnHome)
        BtnHome.setOnClickListener {
            finish()
        }

        val TextScore = dialog.findViewById<TextView>(R.id.TextScore)
        TextScore.setText("${getString(R.string.score)} : $Newpoint/100")

        val TextTime = dialog.findViewById<TextView>(R.id.TextTime)
        TextTime.setText("${getString(R.string.time)} : ${(NewTime.toDouble()/1000)} ${getString(R.string.sec)}")

        val TextCongratulation = dialog.findViewById<TextView>(R.id.TextCongratulation)
        if (Newpoint>OldPoint){
            TextCongratulation.visibility = View.VISIBLE
        }
        else if(Newpoint ==OldPoint){
            if (NewTime<OldTime){
                TextCongratulation.visibility = View.VISIBLE
            }
        }

        val BtnShare = dialog.findViewById<Button>(R.id.BtnShare)
        BtnShare.setOnClickListener {
           val intent = Intent().apply {
               this.action = Intent.ACTION_SEND
               this.putExtra(Intent.EXTRA_TEXT, getString(R.string.message)+"\nhttps://www.youtube.com/watch?v=8CnmI0K4tz4&list=RDGMEM6CZm14o9sc-Q22TIneLI8gVM8CnmI0K4tz4&start_radio=1")
               this.type="text/plain"

           }
            startActivity(intent)
        }


        dialog.show()

    }



    //dialog pause game
    private fun showDialogPauseGame(){
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

        pause()
        BtnNo.setOnClickListener{
            dialog.dismiss()
            startCountTime()

        }

        BtnYes.setOnClickListener {
            finish()
        }

        BtnRestart.setOnClickListener {
            dialog.dismiss()
            restartGame()
        }

        dialog.show()
    }


    //Restart Game
    private fun restartGame(){
        count=0
        TimePause=0
        NewTime=0
        Newpoint=0
        binding.TextCountPoint.text = "${getString(R.string.score)} : $Newpoint/100"
        getQuestion()
        currentQuestion = ListQuestion.get(index)
        showQuestion()
        startCountTime()
        afterClick()
    }

    //Show correct answer
    private fun showIncorrect(){
        pause()
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
            startCountTime()
            dialog.dismiss()
        }

        dialog.show()
    }



    //Start Count Time
    private fun startCountTime(){
        binding.TextTime.format="${getString(R.string.time)} : %s"
        binding.TextTime.base = SystemClock.elapsedRealtime()-TimePause
        binding.TextTime.start()
    }

    //Pause count time
    private fun pause(){
        binding.TextTime.stop()
        TimePause = (SystemClock.elapsedRealtime() - binding.TextTime.base.toLong()).toInt()
        NewTime = (SystemClock.elapsedRealtime() - binding.TextTime.base )
    }


    //Update point
    private fun updatePoint(newPoint:Long, newTime:Long, oldPoint:Long, oldTime: Long){
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

    private fun onClickcardA(){
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

    private fun onClickcardC(){
        binding.CardC.setBackgroundColor(getColor(R.color.primary))
        binding.CardB.setBackgroundColor(getColor(R.color.white))
        binding.CardA.setBackgroundColor(getColor(R.color.white))

        binding.TextOptionC.setTextColor(getColor(R.color.white))
        binding.TextOptionB.setTextColor(getColor(R.color.black))
        binding.TextOptionA.setTextColor(getColor(R.color.black))

        Answer = binding.TextOptionC.text.toString()
    }

    private fun afterClick(){
        binding.CardC.setBackgroundColor(getColor(R.color.white))
        binding.CardB.setBackgroundColor(getColor(R.color.white))
        binding.CardA.setBackgroundColor(getColor(R.color.white))

        binding.TextOptionC.setTextColor(getColor(R.color.black))
        binding.TextOptionB.setTextColor(getColor(R.color.black))
        binding.TextOptionA.setTextColor(getColor(R.color.black))
        Answer = ""
    }

}