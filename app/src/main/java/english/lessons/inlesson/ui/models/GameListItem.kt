package english.lessons.inlesson.ui.models

data class GameListItem(
    val _id: Id,
    val name: String,
    val task: List<Task>
)