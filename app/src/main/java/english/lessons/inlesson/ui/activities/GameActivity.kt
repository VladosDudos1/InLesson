package english.lessons.inlesson.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import english.lessons.inlesson.R
import english.lessons.inlesson.databinding.ActivityGameBinding
import english.lessons.inlesson.ui.Case.correctAnswers
import english.lessons.inlesson.ui.Case.gameType
import english.lessons.inlesson.ui.fragments.GameChooseFragment
import english.lessons.inlesson.ui.fragments.GameWhoFragment

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (gameType == 0) {
            fragmentTransaction(GameChooseFragment())
        }
        else if (gameType == 1){
            fragmentTransaction(GameWhoFragment())
        }
    }
    private fun fragmentTransaction(fmt: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fmt).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        correctAnswers = 0
        finish()
    }
}