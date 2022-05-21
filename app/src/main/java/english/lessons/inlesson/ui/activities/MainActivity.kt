package english.lessons.inlesson.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.dialog.MaterialDialogs
import english.lessons.inlesson.app.App
import english.lessons.inlesson.databinding.ActivityMainBinding
import english.lessons.inlesson.ui.Case
import english.lessons.inlesson.ui.adapters.GameAdapter
import english.lessons.inlesson.ui.models.GamesList
import english.lessons.inlesson.ui.models.WhoGame
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    private lateinit var chooseList: GamesList
    private lateinit var whoList: WhoGame
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requests()
        setUser()

        onClick()
    }

    private fun setLinearAdapter(){
        try {
            binding.rvGames.adapter = GameAdapter(chooseList, whoList)
            binding.rvGames.layoutManager = LinearLayoutManager(this)
        } catch (ex: Exception){
            Log.d("error", ex.message.toString())
        }
    }
    private fun onClick(){
        binding.imgLogout.setOnClickListener {
            MaterialDialog(this)
                .title(text = "Вы уверены, что хотите выйти?")
                .positiveButton (text = "Да") {
                    App.dm.logout()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                .negativeButton { it.cancel() }
                .show()
        }
        binding.profileCardImg.setOnClickListener {

        }
    }

    private fun requests(){
        val dispChoose = App.dm.api
            .getChooseGame()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                chooseList = it
            }, {
                println(it.message)
                //Log.d("requestFalse", it.message.toString())
            })

        var dispWho = App.dm.api
            .getWhoGame()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                whoList = it
            }, {
                Log.d("requestFalse", it.message.toString())
            }, {
                setLinearAdapter()
            })
    }

    private fun setUser(){

    }
    override fun onBackPressed(){}
}