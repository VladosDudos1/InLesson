package english.lessons.inlesson.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import english.lessons.inlesson.R
import english.lessons.inlesson.databinding.ActivityGameBinding
import english.lessons.inlesson.ui.Case.gameType
import english.lessons.inlesson.ui.fragments.GameFragment
import english.lessons.inlesson.ui.fragments.GameImageFragment
import english.lessons.inlesson.ui.fragments.GameWordFragment

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)

        setContentView(binding.root)

        when (gameType){
            0 ->
                fragmentTransaction(GameFragment())
            1 ->
                fragmentTransaction(GameWordFragment())
            2 ->
                fragmentTransaction(GameImageFragment())
        }
    }
    private fun fragmentTransaction(fmt: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fmt).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        MaterialDialog(this)
            .title(text = "You can't leave until you give an answer.")
            .show()
    }
}