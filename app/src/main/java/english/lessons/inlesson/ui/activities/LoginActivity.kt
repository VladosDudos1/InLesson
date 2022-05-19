package english.lessons.inlesson.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import english.lessons.inlesson.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick(){
        binding.buttonToReg.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        binding.buttonAuthorize.setOnClickListener {
            authorise()
        }
    }

    private fun authorise(){

    }
}