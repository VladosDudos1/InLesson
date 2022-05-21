package english.lessons.inlesson.ui.models

data class WhoGameItem(
    val _id: Int,
    val images: List<String>,
    val name: String,
    val task: List<Task>
)