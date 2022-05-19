package english.lessons.inlesson.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import english.lessons.inlesson.R
import english.lessons.inlesson.databinding.GameItemBinding
import english.lessons.inlesson.ui.models.GamesList
import english.lessons.inlesson.ui.models.WhoGame

class GameAdapter(var choose: GamesList, var who: WhoGame) : RecyclerView.Adapter<GameAdapter.GameVH>() {
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
        if (position == 0)
            binding.gameNameTxt.text = "Выбери одно"
        else if (position == 1)
            binding.gameNameTxt.text = "Угадай кто"
    }

    override fun getItemCount(): Int = 2


    class GameVH(view: View): RecyclerView.ViewHolder(view)
}