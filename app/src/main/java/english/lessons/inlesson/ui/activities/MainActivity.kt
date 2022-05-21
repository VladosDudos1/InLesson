package english.lessons.inlesson.ui.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import english.lessons.inlesson.app.App
import english.lessons.inlesson.databinding.ActivityMainBinding
import english.lessons.inlesson.ui.Case.chooseList
import english.lessons.inlesson.ui.Case.user
import english.lessons.inlesson.ui.Case.whoList
import english.lessons.inlesson.ui.adapters.GameAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import android.content.DialogInterface
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var store = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requests()
        setUser()
        setLinearAdapter()

        onClick()
    }

    private fun setLinearAdapter() {
        try {
            binding.rvGames.adapter = GameAdapter(this)
            binding.rvGames.layoutManager = LinearLayoutManager(this)
        } catch (ex: Exception) {
            Log.d("error", ex.message.toString())
        }
    }

    private fun onClick() {
        binding.imgLogout.setOnClickListener {
            MaterialDialog(this)
                .title(text = "Вы уверены, что хотите выйти?")
                .positiveButton(text = "Да") {
                    App.dm.logout()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                .negativeButton { it.cancel() }
                .show()
        }
        binding.profileCardImg.setOnClickListener {
            val et = EditText(this)
            val dialog: AlertDialog = AlertDialog.Builder(this)
                .setTitle("Введите ссылку на изображение")
                .setView(et)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    val editTextInput: String = et.text.toString()
                    if (URLUtil.isValidUrl(editTextInput)){
                        store.child("users").child(user!!.uid).child("image").setValue(editTextInput)
                            .addOnCompleteListener {
                                if (it.isSuccessful){
                                    Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show()
                                    setUser()
                                }
                                else Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                            }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
            dialog.show()
        }
    }

    private fun requests() {
        val dispChoose = App.dm.api
            .getChooseGame()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                chooseList = it
            }, {
                println(it.message)
            })

        var dispWho = App.dm.api
            .getWhoGame()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                whoList = it
            }, {
                println(it.message)
            })
    }

    private fun setUser() {
        store.child("users").child(user!!.uid).get()
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    binding.nickName.text = it.result.child("name").value.toString()
                    Glide.with(binding.imgProfile)
                        .load(it.result.child("image").value.toString())
                        .into(binding.imgProfile)
                }
            }

    }

    override fun onBackPressed() {}
}