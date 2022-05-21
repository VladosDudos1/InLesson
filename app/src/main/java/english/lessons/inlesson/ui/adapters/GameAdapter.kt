package english.lessons.inlesson.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import english.lessons.inlesson.R
import english.lessons.inlesson.databinding.GameItemBinding
import english.lessons.inlesson.ui.Case
import english.lessons.inlesson.ui.activities.GameActivity

class GameAdapter(val context: Context) : RecyclerView.Adapter<GameAdapter.GameVH>() {
    private lateinit var binding: GameItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameVH {
        binding = GameItemBinding.bind(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.game_item,
                    parent,
                    false
                )
        )
        return GameVH(binding.root)
    }

    override fun onBindViewHolder(holder: GameVH, position: Int) {
        if (position == 0) {
            Glide.with(binding.imgGame)
                .load("https://firebasestorage.googleapis.com/v0/b/inlesson-d6a94.appspot.com/o/game.png?alt=media&token=75386a36-633a-4418-86b1-ae4471579448")
                .into(binding.imgGame)
            binding.gameNameTxt.text = "Выбери одно"
        }
        else if (position == 1) {
            Glide.with(binding.imgGame)
                .load("https://firebasestorage.googleapis.com/v0/b/inlesson-d6a94.appspot.com/o/123.jpg?alt=media&token=4087994c-a3df-4b71-88a6-9a5e8c96a3fd")
                .into(binding.imgGame)
            binding.gameNameTxt.text = "Угадай кто"
        }

        binding.gameLayout.setOnClickListener {
            Case.gameType = position
            context.startActivity(Intent(context, GameActivity::class.java))
        }
    }

    override fun getItemCount(): Int = 2


    class GameVH(view: View): RecyclerView.ViewHolder(view)
}