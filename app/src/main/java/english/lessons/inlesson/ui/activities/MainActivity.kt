package english.lessons.inlesson.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import english.lessons.inlesson.databinding.ActivityMainBinding
import english.lessons.inlesson.ui.Case.chooseList
import english.lessons.inlesson.ui.Case.whoList
import english.lessons.inlesson.ui.adapters.GameAdapter
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLinearAdapter()
    }

    private fun setLinearAdapter(){
        try {
            binding.rvGames.adapter = GameAdapter(chooseList, whoList)
            binding.rvGames.layoutManager = LinearLayoutManager(this)
        } catch (ex: Exception){
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }
    }
}