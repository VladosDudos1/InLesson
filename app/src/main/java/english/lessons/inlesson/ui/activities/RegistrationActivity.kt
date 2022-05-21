package english.lessons.inlesson.ui.activities

import android.content.ContentValues.TAG
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import english.lessons.inlesson.databinding.ActivityRegistrationBinding
import english.lessons.inlesson.ui.models.User

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var store = FirebaseDatabase.getInstance().reference

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
        binding.buttonRegistration.setOnClickListener {
            registration()
        }
    }
    private fun registration(){
        val nick = binding.editNick.text.toString()
        val mail = binding.editLogin.text.toString()
        val password = binding.editPassword.text.toString()

        val userN = User(mail, "", nick)

        if (checkInput()){
            auth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)

                        store.child("users").child(store.push().key.toString()).setValue(userN)
                            .addOnCompleteListener(this) { res ->
                                if (res.isSuccessful){
                                    onBackPressed()
                                }
                                else makeToast(res.exception!!.message.toString())
                            }
                    }
                    else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        updateUI(null)
                    }
                }
        }
    }

    private fun checkInput(): Boolean {
        when {
            !Patterns.EMAIL_ADDRESS.matcher(binding.editLogin.text)
                .matches() -> makeToast("Введите свою почту")
            binding.editLogin.text.isNullOrEmpty() -> makeToast("Введите свою почту")
            binding.editNick.text.isNullOrEmpty() -> makeToast("Введите свой ник")
            binding.editPassword.text.isNullOrEmpty() -> makeToast("Введите свой пароль")
            binding.editPassword.text.toString().length < 6 -> makeToast("Пароль должен содержать хотя бы 6 символов")
            binding.repeatPassword.text.toString() != binding.editPassword.text.toString() -> makeToast("Пароли не совпадают")
            else -> return true
        }
        return false
    }

    private fun makeToast(m: String) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(account: FirebaseUser?) {
        if (account != null)
            makeToast("Регистрация прошла удачно")
        else makeToast("Что-то пошло не так")
    }
}