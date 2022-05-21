package english.lessons.inlesson.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import english.lessons.inlesson.R
import english.lessons.inlesson.databinding.GameWhoFragmentBinding
import english.lessons.inlesson.ui.Case
import english.lessons.inlesson.ui.Case.whoList
import english.lessons.inlesson.ui.activities.MainActivity
import english.lessons.inlesson.ui.models.Task
import kotlin.random.Random

class GameWhoFragment : Fragment() {

    private lateinit var binding: GameWhoFragmentBinding
    private var index = 0
    private var bigIndex = 0
    private lateinit var list: List<Task>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameWhoFragmentBinding.bind(
            inflater.inflate(
                R.layout.game_who_fragment, container, false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = whoList!![setRandom()].task

        setQuestion(list[index])
        onClick()
    }

    private fun setQuestion(ques: Task) {
        if (index <= 3) {
            binding.questionTxt.text = ques.question
            index++
        }

        Glide.with(binding.answer1Btn)
            .load(whoList!![bigIndex].images[0])
            .into(binding.answer1Btn)
        Glide.with(binding.answer2Btn)
            .load(whoList!![bigIndex].images[1])
            .into(binding.answer2Btn)
        Glide.with(binding.answer3Btn)
            .load(whoList!![bigIndex].images[2])
            .into(binding.answer3Btn)
        Glide.with(binding.answer4Btn)
            .load(whoList!![bigIndex].images[3])
            .into(binding.answer4Btn)
    }

    private fun onClick() {
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
    }

    private fun makeAnswer(num: Int) {
        val ques = list[index - 1]

        if (num == ques.answer.`$numberInt`.toInt()) {
            Case.correctAnswers++
        }
        if (index != 4) {
            setQuestion(list[index])
        } else {
            list = Case.whoList!![setRandom()].task
            val dialog = MaterialDialog(requireActivity())
                .title(text = "Ваш результат: ${Case.correctAnswers}")
                .positiveButton(text = "play again") {
                    Case.correctAnswers = 0
                    index = 0
                    it.cancel()
                    setQuestion(list[index])
                }
                .negativeButton(text = "main menu") {
                    Case.correctAnswers = 0
                    it.cancel()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                }
                .cancelOnTouchOutside(false)

            dialog.show { }
        }
    }

    private fun setRandom(): Int {
        bigIndex = Random.nextInt(0, 3)
        return bigIndex
    }
}