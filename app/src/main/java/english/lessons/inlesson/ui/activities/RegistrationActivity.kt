package english.lessons.inlesson.ui.activities

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import english.lessons.inlesson.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onCLick()
    }
    private fun onCLick(){
        binding.buttonToAuth.setOnClickListener {
            onBackPressed()
        }
    }
}