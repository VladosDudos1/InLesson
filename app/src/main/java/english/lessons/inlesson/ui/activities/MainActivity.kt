package english.lessons.inlesson.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.afollestad.materialdialogs.MaterialDialog
import english.lessons.inlesson.R
import english.lessons.inlesson.app.App
import english.lessons.inlesson.databinding.ActivityMainBinding
import english.lessons.inlesson.ui.Case.backPressType
import english.lessons.inlesson.ui.fragments.GameFragment
import english.lessons.inlesson.ui.fragments.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (App.dm.isLogin())
            fragmentTransaction(GameFragment())
        else fragmentTransaction(LoginFragment())
    }
    private fun fragmentTransaction(fmt: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fmt).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        when (backPressType) {
            0 -> super.onBackPressed()
            1 -> finishAffinity()
            else -> {
                MaterialDialog(this)
                    .title(text = "You can't leave until you give an answer.")
                    .show()
            }
        }
    }
}