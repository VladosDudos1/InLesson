package english.lessons.inlesson.ui.models

data class GamesListItem(
    val _id: Id,
    val name: String,
    val task: List<Task>
)