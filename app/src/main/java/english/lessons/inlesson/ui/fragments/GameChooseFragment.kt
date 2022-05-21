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
import english.lessons.inlesson.R
import english.lessons.inlesson.databinding.GameChooseFragmentBinding
import english.lessons.inlesson.ui.Case
import english.lessons.inlesson.ui.Case.chooseList
import english.lessons.inlesson.ui.activities.MainActivity
import english.lessons.inlesson.ui.models.Task
import kotlin.random.Random.Default.nextInt

class GameChooseFragment : Fragment() {

    private lateinit var binding: GameChooseFragmentBinding
    private var index = 0
    private lateinit var list: List<Task>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameChooseFragmentBinding.bind(
            inflater.inflate(
                R.layout.game_choose_fragment,container, false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = chooseList!![setRandom()].task

        onClick()
        setQuestion(list[index])
    }

    private fun setDefault() {
        setButtonsColor()
    }

        private fun setQuestion(ques: Task) {
        if (index <= 3) {
            binding.questionTxt.text = ques.problem
            binding.answer1Btn.text = ques.words[0]
            binding.answer2Btn.text = ques.words[1]
            binding.answer3Btn.text = ques.words[2]
            binding.answer4Btn.text = ques.words[3]

            setButtonsColor()
            index++
        }
    }

    private fun setButtonsColor() {
        binding.answer1Btn.backgroundTintList =
            ContextCompat.getColorStateList(requireActivity(), R.color.purple_500);
        binding.answer2Btn.backgroundTintList =
            ContextCompat.getColorStateList(requireActivity(), R.color.purple_500);
        binding.answer3Btn.backgroundTintList =
            ContextCompat.getColorStateList(requireActivity(), R.color.purple_500);
        binding.answer4Btn.backgroundTintList =
            ContextCompat.getColorStateList(requireActivity(), R.color.purple_500);
    }

    override fun onStop() {
        super.onStop()
        setDefault()
    }

    private fun onClick() {
        binding.answer1Btn.setOnClickListener {
            binding.answer1Btn.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_color)
            makeAnswer(0)
        }
        binding.answer2Btn.setOnClickListener {
            binding.answer2Btn.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_color)
            makeAnswer(1)
        }
        binding.answer3Btn.setOnClickListener {
            binding.answer3Btn.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_color)
            makeAnswer(2)
        }
        binding.answer4Btn.setOnClickListener {
            binding.answer4Btn.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_color)
            makeAnswer(3)
        }
    }

    private fun makeAnswer(num: Int) {
        val ques = list[index-1]

        when (ques.answer.`$numberInt`.toInt()){
            0 -> {
                binding.answer1Btn.backgroundTintList =
                    ContextCompat.getColorStateList(requireActivity(), R.color.green_color)
            }
            1 -> {
                binding.answer2Btn.backgroundTintList =
                    ContextCompat.getColorStateList(requireActivity(), R.color.green_color)
            }
            2 -> {
                binding.answer3Btn.backgroundTintList =
                    ContextCompat.getColorStateList(requireActivity(), R.color.green_color)
            }
            3 -> {
                binding.answer4Btn.backgroundTintList =
                    ContextCompat.getColorStateList(requireActivity(), R.color.green_color)
            }
        }

        changeButtonsMode(false)

        if (num == ques.answer.`$numberInt`.toInt()) {
            Case.correctAnswers++
        }
        if (index != 4) {
            Handler().postDelayed({
                setQuestion(list[index])
                changeButtonsMode(true)
            }, 850)
        }
        else {
            list = chooseList!![setRandom()].task
            val dialog = MaterialDialog(requireActivity())
                .title(text = "Ваш результат: ${Case.correctAnswers}")
                .positiveButton (text = "play again"){
                    Case.correctAnswers = 0
                    index = 0
                    it.cancel()
                    setQuestion(list[index])
                    changeButtonsMode(true)
                }
                .negativeButton (text = "main menu") {
                    Case.correctAnswers = 0
                    it.cancel()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                }
                .cancelOnTouchOutside(false)

            dialog.show {  }
        }
    }
    private fun changeButtonsMode(bool: Boolean){
        binding.answer1Btn.isEnabled = bool
        binding.answer2Btn.isEnabled = bool
        binding.answer3Btn.isEnabled = bool
        binding.answer4Btn.isEnabled = bool
    }

    private fun setRandom() : Int{
        return nextInt(0, 3)
    }
}