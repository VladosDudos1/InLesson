package english.lessons.inlesson.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import english.lessons.inlesson.R
import english.lessons.inlesson.databinding.GamePictureFragmentBinding
import english.lessons.inlesson.ui.Case.backPressType
import english.lessons.inlesson.ui.activities.MainActivity
import kotlin.random.Random.Default.nextInt
import java.util.*


class GamePictureFragment : Fragment() {

    private lateinit var binding: GamePictureFragmentBinding
    private var store = FirebaseDatabase.getInstance().reference
    private var question = ""
    private var randomTask = setRandom().toString()

    private var isCorrect = false

    private lateinit var image1: String
    private lateinit var image2: String
    private lateinit var image3: String
    private lateinit var image4: String

    private lateinit var activityForDialogs: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GamePictureFragmentBinding.bind(
            inflater.inflate(
                R.layout.game_picture_fragment, container, false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityForDialogs = requireActivity()

        onClick()
        startGame()
        backPressType = 2

        binding.answer1Img.isEnabled = false
        binding.answer2Img.isEnabled = false
        binding.answer3Img.isEnabled = false
        binding.answer4Img.isEnabled = false
    }

    private fun setQuestion() {
        store.child("Game").child("1").child("tasks").child(randomTask).child("answers").child("1")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    image1 = it.result.value.toString()
                    Glide.with(binding.answer1Img)
                        .load(image1)
                        .into(binding.answer1Img)
                }
            }
        store.child("Game").child("1").child("tasks").child(randomTask).child("answers").child("2")
            .get()
            .addOnCompleteListener {
                image2 = it.result.value.toString()
                if (it.isSuccessful) {
                    Glide.with(binding.answer2Img)
                        .load(image2)
                        .into(binding.answer2Img)
                }
            }
        store.child("Game").child("1").child("tasks").child(randomTask).child("answers").child("3")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    image3 = it.result.value.toString()
                    Glide.with(binding.answer3Img)
                        .load(image3)
                        .into(binding.answer3Img)
                }
            }
        store.child("Game").child("1").child("tasks").child(randomTask).child("answers").child("4")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    image4 = it.result.value.toString()
                    Glide.with(binding.answer4Img)
                        .load(image4)
                        .into(binding.answer4Img)
                }
            }
    }

    private fun onClick() {
        binding.helpBtn.setOnClickListener {
            store.child("Game").child("1").get()
                .addOnCompleteListener { r ->
                    MaterialDialog(activityForDialogs)
                        .title(text = "Prompt")
                        .cancelable(true)
                        .positiveButton(text = "Close prompt") {
                            it.cancel()
                        }
                        .message(text = r.result.child("tasks").child(randomTask).child("help").value.toString())
                        .show()
                }
        }

        binding.answer1Img.setOnClickListener {
            makeAnswer(0)
        }
        binding.answer2Img.setOnClickListener {
            makeAnswer(1)
        }
        binding.answer3Img.setOnClickListener {
            makeAnswer(2)
        }
        binding.answer4Img.setOnClickListener {
            makeAnswer(3)
        }
        binding.answerBtn.setOnClickListener {
            if (!binding.etAnswer.text.toString().contains(question) && binding.etAnswer.text.toString()
                    .isNotEmpty()) {
                store.child("room").child("resultFirst").setValue(binding.etAnswer.text.toString())
                val dialog = MaterialDialog(activityForDialogs)
                    .cancelable(false)
                    .title(text = "Thank you, waiting to the next player")
                    .negativeButton(text = "Leave") {
                        it.cancel()
                        activityForDialogs.supportFragmentManager.popBackStack()
                    }
                dialog.show { }

                val timer = Timer()
                timer.scheduleAtFixedRate(object : TimerTask() {
                    var title = ""
                    override fun run() {
                        store.child("room").child("resultSecond").get()
                            .addOnCompleteListener { f ->
                                store.child("room").child("resultSecond")
                                    .get()
                                    .addOnCompleteListener { r ->
                                        isCorrect =
                                            f.result.value.toString() == question
                                        if (r.result.value.toString().isNotEmpty()){
                                            dialog.cancel()
                                            title = if (isCorrect) {
                                                "The player got you!"
                                            } else {
                                                "The player did not understand you"
                                            }
                                            timer.cancel()
                                            MaterialDialog(activityForDialogs)
                                                .cancelable(false)
                                                .title(text = title)
                                                .negativeButton(text = "Leave") {
                                                    it.cancel()
                                                    activityForDialogs.supportFragmentManager.popBackStack()
                                                    resultOfGame()
                                                }
                                                .show()
                                        }
                                    }
                            }
                    }
                }, 1500, 2000)
            } else Toast.makeText(
                activityForDialogs,
                "Answer can`t contain a question word or be blank",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun makeAnswer(num: Int) {
        when (num) {
            0 -> answerRes(image1)
            1 -> answerRes(image2)
            2 -> answerRes(image3)
            3 -> answerRes(image4)
        }
    }

    private fun answerRes(img: String){
        store.child("room").child("resultSecond").setValue(img)
        val title = if (img == question) "Success, you`re right" else "Loose, you`re incorrect"

            MaterialDialog(activityForDialogs)
                .title(text = title)
                .cancelable(false)
                .positiveButton (text = "Close the game") {
                    activityForDialogs.supportFragmentManager.popBackStack()
                    it.cancel()
                }
                .show {  }
        Handler().postDelayed({resultOfGame()}, 2000)
    }

    private fun setRandom(): Int {
        return nextInt(1, 21)
    }

    private fun startGame() {
        store.child("Game").child("1").get()
            .addOnCompleteListener(activityForDialogs) {
                if (it.isSuccessful) {
                    store.child("room").get()
                        .addOnCompleteListener(activityForDialogs) { r ->
                            if (r.isSuccessful) {
                                if (r.result.child("isEmpty").value.toString().toBoolean()) {
                                    vision1()
                                    question = it.result.child("tasks").child(randomTask)
                                        .child("question").value.toString()
                                    store.child("room").child("isEmpty").setValue(false)
                                    store.child("room").child("idGame").setValue(randomTask)
                                    Glide.with(binding.questionImg)
                                        .load(
                                            it.result.child("tasks").child(randomTask)
                                                .child("question").value.toString()
                                        )
                                        .into(binding.questionImg)
                                } else {
                                    vision2()
                                    randomTask = r.result.child("idGame").value.toString()
                                    binding.questionTxt.text = "Waiting the first player"
                                    var resF = r.result.child("resultFirst").value.toString()
                                    setQuestion()
                                    question = it.result.child("tasks").child(randomTask)
                                        .child("question").value.toString()
                                    val timer = Timer()
                                    timer.scheduleAtFixedRate(object : TimerTask() {
                                        override fun run() {
                                            if (resF.isEmpty()) {
                                                store.child("room").get()
                                                    .addOnCompleteListener {t ->
                                                        resF =
                                                            t.result.child("resultFirst").value.toString()
                                                    }
                                            } else {
                                                activityForDialogs.runOnUiThread {
                                                    binding.questionTxt.text = resF
                                                    binding.answer1Img.isEnabled = true
                                                    binding.answer2Img.isEnabled = true
                                                    binding.answer3Img.isEnabled = true
                                                    binding.answer4Img.isEnabled = true
                                                }
                                                timer.cancel()
                                            }
                                        }
                                    }, 200, 1800)
                                }
                            }
                        }
                }
            }
    }

    override fun onStop() {
        super.onStop()
        resultOfGame()
    }

    private fun resultOfGame() {
        store.child("room").child("isEmpty").setValue(true)
        store.child("room").child("resultFirst").setValue("")
        store.child("room").child("resultSecond").setValue("")
        store.child("room").child("idGame").setValue("")
    }

    private fun vision1() {
        binding.questionImg.visibility = View.VISIBLE
        binding.questionTxt.visibility = View.INVISIBLE
        binding.etAnswer.visibility = View.VISIBLE
        binding.answerBtn.visibility = View.VISIBLE
        binding.answer1Img.visibility = View.GONE
        binding.answer2Img.visibility = View.GONE
        binding.answer3Img.visibility = View.GONE
        binding.answer4Img.visibility = View.GONE
        binding.helpBtn.visibility = View.VISIBLE
    }

    private fun vision2() {
        binding.questionImg.visibility = View.INVISIBLE
        binding.questionTxt.visibility = View.VISIBLE
        binding.etAnswer.visibility = View.INVISIBLE
        binding.answerBtn.visibility = View.INVISIBLE

        binding.answer1Img.visibility = View.VISIBLE
        binding.answer2Img.visibility = View.VISIBLE
        binding.answer3Img.visibility = View.VISIBLE
        binding.answer4Img.visibility = View.VISIBLE

        binding.helpBtn.visibility = View.GONE
    }
}