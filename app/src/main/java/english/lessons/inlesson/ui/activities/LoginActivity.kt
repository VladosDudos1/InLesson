package english.lessons.inlesson.ui.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import english.lessons.inlesson.app.App
import english.lessons.inlesson.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

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
        val mail = binding.editLogin.text.toString()
        val password = binding.editPassword.text.toString()
        if (checkInput()){
            auth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                        App.dm.endLogin()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        makeToast("Что-то пошло не так, проверьте качество соединения.")
                    }
                }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
    private fun checkInput(): Boolean {
        when {
            !Patterns.EMAIL_ADDRESS.matcher(binding.editLogin.text)
                .matches() -> makeToast("Введите правильную почту")
            binding.editLogin.text.isNullOrEmpty() -> makeToast("Введите свою почту")
            binding.editPassword.text.isNullOrEmpty() -> makeToast("Введите свой пароль")
            else -> return true
        }
        return false
    }
    private fun makeToast(m: String) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show()
    }
    private fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            makeToast("Вы успешно авторизовались")
        } else {
            makeToast("Не удалось выполнить авторизацию")
        }
    }
}