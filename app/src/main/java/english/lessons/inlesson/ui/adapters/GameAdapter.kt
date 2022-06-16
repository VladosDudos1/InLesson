package english.lessons.inlesson.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import english.lessons.inlesson.R
import english.lessons.inlesson.databinding.GameItemBinding
import english.lessons.inlesson.ui.Case
import english.lessons.inlesson.ui.activities.GameActivity

class GameAdapter(val context: Context) : RecyclerView.Adapter<GameAdapter.GameVH>() {
    private lateinit var binding: GameItemBinding
    private lateinit var description: String

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
        when (position) {
            0 -> {
                Glide.with(binding.imgGame)
                    .load("https://firebasestorage.googleapis.com/v0/b/inlesson-d6a94.appspot.com/o/123.jpg?alt=media&token=4087994c-a3df-4b71-88a6-9a5e8c96a3fd")
                    .into(binding.imgGame)
                binding.gameNameTxt.text = "Explain the picture"
            }
            1 -> {
                Glide.with(binding.imgGame)
                    .load("https://firebasestorage.googleapis.com/v0/b/inlesson-d6a94.appspot.com/o/1.jpg?alt=media&token=41675ade-3041-421a-9cb0-dcf2b6ae4f3c")
                    .into(binding.imgGame)
                binding.gameNameTxt.text = "Explain the word"
            }
            2 -> {
                Glide.with(binding.imgGame)
                    .load("https://firebasestorage.googleapis.com/v0/b/inlesson-d6a94.appspot.com/o/game.png?alt=media&token=75386a36-633a-4418-86b1-ae4471579448")
                    .into(binding.imgGame)
                binding.gameNameTxt.text = "Who/what is it"
            }
        }

        binding.gameLayout.setOnClickListener {
            Case.gameType = position
            context.startActivity(Intent(context, GameActivity::class.java))
        }
        binding.imgInfo.setOnClickListener {
            when (position){
                0 -> description = "In this game you have to explain what you see in the picture. After that, the second player will choose an image from the presented list based on the description provided"
                1 -> description = "In this game you have to explain the presented word. After that, the second player will choose a word from the presented words based on the explanation provided"
                2 -> description = "In this game, you need to correctly translate all the tasks and select the appropriate pictures"
            }
            MaterialDialog(context)
                .title(text = "Game description")
                .message(text = description)
                .show()
        }
    }

    override fun getItemCount(): Int = 3


    class GameVH(view: View): RecyclerView.ViewHolder(view)
}