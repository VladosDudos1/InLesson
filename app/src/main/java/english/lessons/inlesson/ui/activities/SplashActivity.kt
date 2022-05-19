package english.lessons.inlesson.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import english.lessons.inlesson.app.App
import english.lessons.inlesson.databinding.ActivitySplashBinding
import english.lessons.inlesson.ui.Case
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dispChoose = App.dm.api
            .getChooseGame()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Case.chooseList = it
            }, {
                Toast.makeText(this, "first game isn't available", Toast.LENGTH_SHORT).show()
            })
        var dispWho = App.dm.api
            .getWhoGame()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Case.whoList = it
            }, {
                Toast.makeText(this, "second game isn't available", Toast.LENGTH_SHORT).show()
            })
        Handler().postDelayed({ startActivity(Intent(this, MainActivity::class.java)) }, 700)
    }
}