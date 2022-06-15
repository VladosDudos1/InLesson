package english.lessons.inlesson.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import english.lessons.inlesson.R
import english.lessons.inlesson.databinding.GameWordFragmentBinding
import java.util.*
import kotlin.random.Random

class GameWordFragment : Fragment() {

    private lateinit var binding: GameWordFragmentBinding
    private var store = FirebaseDatabase.getInstance().reference
    private var question = ""
    private var randomTask = setRandom().toString()

    private var isCorrect = false

    private lateinit var answer1: String
    private lateinit var answer2: String
    private lateinit var answer3: String
    private lateinit var answer4: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameWordFragmentBinding.bind(
            inflater.inflate(
                R.layout.game_word_fragment, container, false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
        startGame()
    }


    private fun setQuestion() {
        store.child("Game").child("2").child("tasks").child(randomTask).child("answers").child("1")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    answer1 = it.result.value.toString()
                    binding.answer1Btn.text = answer1
                }
            }
        store.child("Game").child("2").child("tasks").child(randomTask).child("answers").child("2")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    answer2 = it.result.value.toString()
                    binding.answer2Btn.text = answer2
                }
            }
        store.child("Game").child("2").child("tasks").child(randomTask).child("answers").child("3")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    answer3 = it.result.value.toString()
                    binding.answer3Btn.text = answer3
                }
            }
        store.child("Game").child("2").child("tasks").child(randomTask).child("answers").child("4")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    answer4 = it.result.value.toString()
                    binding.answer4Btn.text = answer4
                }
            }
    }

    private fun onClick() {
        binding.helpBtn.setOnClickListener {
            store.child("Game").child("2").get()
                .addOnCompleteListener { r ->
                    MaterialDialog(requireActivity())
                        .title(text = "Prompt")
                        .cancelable(true)
                        .positiveButton(text = "Close prompt") {
                            it.cancel()
                        }
                        .message(
                            text = r.result.child("tasks").child(randomTask)
                                .child("help").value.toString()
                        )
                        .show()
                }
        }

        binding.answer1Btn.setOnClickListener {
            makeAnswer(0)
        }
        binding.answer2Btn.setOnClickListener {
            makeAnswer(1)
        }
        binding.answer3Btn.setOnClickListener {
            makeAnswer(2)
        }
        binding.answer4Btn.setOnClickListener {
            makeAnswer(3)
        }
        binding.answerBtn.setOnClickListener {
            if (!binding.etAnswer.text.toString().lowercase()
                    .contains(question.lowercase()) && binding.etAnswer.text.toString()
                    .isNotEmpty()
            ) {
                store.child("room2").child("resultFirst").setValue(binding.etAnswer.text.toString())
                val dialog = MaterialDialog(requireActivity())
                    .cancelable(false)
                    .title(text = "Thank you, waiting to the next player")
                    .negativeButton(text = "Leave") {
                        requireActivity().finish()
                    }
                dialog.show { }

                val timer = Timer()
                timer.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        store.child("room2").child("resultSecond").get()
                            .addOnCompleteListener { f ->
                                store.child("room2").child("resultSecond")
                                    .get()
                                    .addOnCompleteListener { r ->
                                        isCorrect =
                                            f.result.value.toString() == question
                                        if (r.result.value.toString().isNotEmpty()) {
                                            if (isCorrect) {
                                                timer.cancel()
                                                dialog.cancel()

                                                MaterialDialog(requireActivity())
                                                    .cancelable(false)
                                                    .title(text = "The player got you!")
                                                    .negativeButton(text = "Leave") {
                                                        requireActivity().finish()
                                                        resultOfGame()
                                                    }
                                                    .show()
                                            } else {
                                                timer.cancel()
                                                MaterialDialog(requireActivity())
                                                    .cancelable(false)
                                                    .title(text = "The player did not understand you")
                                                    .negativeButton(text = "Leave") {
                                                        requireActivity().finish()
                                                        resultOfGame()
                                                    }
                                                    .show()
                                            }
                                        }
                                    }
                            }
                    }
                }, 2000, 3000)
            } else Toast.makeText(
                requireActivity(),
                "Answer can`t contain a question word or be blank",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun makeAnswer(num: Int) {
        when (num) {
            0 -> {
                store.child("room2").child("resultSecond").setValue(answer1)
                if (answer1 == question) {
                    MaterialDialog(requireActivity())
                        .title(text = "Success, you`re right")
                        .cancelable(false)
                        .positiveButton(text = "Close the game") {
                            requireActivity().finish()
                        }
                        .show { }
                } else {
                    MaterialDialog(requireActivity())
                        .title(text = "Loose, you`re incorrect")
                        .cancelable(false)
                        .negativeButton(text = "Close the game") {
                            requireActivity().finish()
                        }
                        .show { }
                }
                Handler().postDelayed({ resultOfGame() }, 2800)
            }
            1 -> {
                store.child("room2").child("resultSecond").setValue(answer2)
                if (answer2 == question) {
                    MaterialDialog(requireActivity())
                        .title(text = "Success, you`re right")
                        .cancelable(false)
                        .positiveButton(text = "Close the game") {
                            requireActivity().finish()
                        }
                        .show { }
                } else {
                    MaterialDialog(requireActivity())
                        .title(text = "Loose, you`re incorrect")
                        .cancelable(false)
                        .negativeButton(text = "Close the game") {
                            requireActivity().finish()
                        }
                        .show { }
                }
                Handler().postDelayed({ resultOfGame() }, 2800)
            }
            2 -> {
                store.child("room2").child("resultSecond").setValue(answer3)
                if (answer3 == question) {
                    MaterialDialog(requireActivity())
                        .title(text = "Success, you`re right")
                        .cancelable(false)
                        .positiveButton(text = "Close the game") {
                            requireActivity().finish()
                        }
                        .show { }
                } else {
                    MaterialDialog(requireActivity())
                        .title(text = "Loose, you`re incorrect")
                        .cancelable(false)
                        .negativeButton(text = "Close the game") {
                            requireActivity().finish()
                        }
                        .show { }
                }
                Handler().postDelayed({ resultOfGame() }, 2800)
            }
            3 -> {
                store.child("room2").child("resultSecond").setValue(answer4)
                if (answer4 == question) {
                    MaterialDialog(requireActivity())
                        .title(text = "Success, you`re right")
                        .cancelable(false)
                        .positiveButton(text = "Close the game") {
                            requireActivity().finish()
                        }
                        .show { }
                } else {
                    MaterialDialog(requireActivity())
                        .title(text = "Loose, you`re incorrect")
                        .cancelable(false)
                        .negativeButton(text = "Close the game") {
                            requireActivity().finish()
                        }
                        .show { }
                }

                Handler().postDelayed({ resultOfGame() }, 2800)
            }
        }
    }

    private fun setRandom(): Int {
        return Random.nextInt(1, 7)
    }

    private fun startGame() {
        store.child("Game").child("2").get()
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    store.child("room2").get()
                        .addOnCompleteListener(requireActivity()) { r ->
                            if (r.isSuccessful) {
                                if (r.result.child("isEmpty").value.toString().toBoolean()) {
                                    vision1()
                                    question = it.result.child("tasks").child(randomTask)
                                        .child("question").value.toString()
                                    store.child("room2").child("isEmpty").setValue(false)
                                    store.child("room2").child("idGame").setValue(randomTask)
                                    binding.questionTxt.text = it.result.child("tasks").child(randomTask)
                                        .child("question").value.toString()
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
                                                store.child("room2").get()
                                                    .addOnCompleteListener { t ->
                                                        resF =
                                                            t.result.child("resultFirst").value.toString()
                                                    }
                                            } else {
                                                requireActivity().runOnUiThread {
                                                    binding.questionTxt.text = resF
                                                }
                                                timer.cancel()
                                            }
                                        }
                                    }, 500, 2000)
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
        store.child("room2").child("isEmpty").setValue(true)
        store.child("room2").child("resultFirst").setValue("")
        store.child("room2").child("resultSecond").setValue("")
        store.child("room2").child("idGame").setValue("")
    }

    private fun vision1() {
        binding.etAnswer.visibility = View.VISIBLE
        binding.answerBtn.visibility = View.VISIBLE
        binding.answer1Btn.visibility = View.GONE
        binding.answer2Btn.visibility = View.GONE
        binding.answer3Btn.visibility = View.GONE
        binding.answer4Btn.visibility = View.GONE
        binding.helpBtn.visibility = View.VISIBLE
    }

    private fun vision2() {
        binding.etAnswer.visibility = View.INVISIBLE
        binding.answerBtn.visibility = View.INVISIBLE

        binding.answer1Btn.visibility = View.VISIBLE
        binding.answer2Btn.visibility = View.VISIBLE
        binding.answer3Btn.visibility = View.VISIBLE
        binding.answer4Btn.visibility = View.VISIBLE

        binding.helpBtn.visibility = View.GONE
    }
}